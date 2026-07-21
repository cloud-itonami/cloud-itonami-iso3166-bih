(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of Bosnian and Herzegovinian procurement law, whether a claimed
  engagement fee actually equals base + months x rate, whether a
  declared tax/social-security arrears amount is actually excused by a
  confirmed reprogrammed-payment arrangement, whether a JIB (Entity-
  level tax identification number) has been verified for a filing that
  requires it, or when a draft stops being a draft and becomes a
  real-world e-Nabavke portal submission, so this MUST be a separate
  system able to *reject* a proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints).

  This blueprint's own text (docs/business-model.md Trust Controls:
  '`:filing/submit` never automated') names exactly the checks below.

  Six checks, in priority order, ALL HARD violations: a human
  approver CANNOT override them. The confidence/actuation gate is
  SOFT: it asks a human to look (low confidence / actuation), and the
  human may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file?
    3. Tax/social-security arrears -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement has an undisputed
                                       positive `:tax-arrears-amount`
                                       with no confirmed reprogrammed-
                                       payment arrangement on file (ZJN
                                       Čl. 45(1)(c)-(d) + (3)) -- this
                                       jurisdiction's own statute has NO
                                       de-minimis carve-out, so this is
                                       a BINARY check, a genuinely
                                       different SHAPE than a sibling
                                       jurisdiction's percentage-of-
                                       turnover threshold recompute.
    4. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    5. JIB unverified               -- for `:filing/submit`, when the
                                       engagement declares
                                       `:requires-jib? true`,
                                       INDEPENDENTLY check
                                       `:jib-verified?`. CONDITIONAL on
                                       the engagement's own ground
                                       truth. Grounded in the JIB
                                       (jedinstveni identifikacioni
                                       broj) the Entity's own tax
                                       administration assigns on
                                       registration -- the base number
                                       BiH's STATE-level UIO indirect-
                                       tax identification number is
                                       mathematically derived from (see
                                       `marketentry.facts`).
    6. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real portal package and submitting a real portal
  registration are the two real-world actuation events this actor
  performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence must actually be satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(MBS/JIB/UIO識別番号/e-Nabavke登録等)が充足していない状態での提案"}]))))

(defn- tax-social-security-arrears-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own declared tax/social-security arrears violate ZJN
  Čl. 45(1)(c)-(d) -- the flagship genuinely new check for this
  jurisdiction. Unlike a sibling jurisdiction's percentage-of-turnover
  de-minimis threshold, THIS jurisdiction has NO carve-out, so this
  check is BINARY and mandatory on every `:filing/submit`, not
  conditionally gated."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (registry/tax-social-security-arrears-violation? e)
        [{:rule :tax-social-security-arrears
          :detail (str subject " の未納税金・年金/健康保険拠出金延滞額(" (:tax-arrears-amount e)
                      " KM)につき、分割納付合意の確認(ZJN第45条(3)項)が無い -- "
                      "ZJN(公共調達法)第45条(1)(c)-(d)項の義務的除外事由に該当(このjurisdictionにde minimis免除は無い) "
                      "-- 提出提案は進められない")}]))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- jib-unverified-violations
  "For `:filing/submit`, when the engagement declares `:requires-jib?
  true`, INDEPENDENTLY check `:jib-verified?` -- CONDITIONAL on the
  engagement's own ground truth. Grounded in the JIB (jedinstveni
  identifikacioni broj) the Entity's own tax administration assigns on
  business registration."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (and (true? (:requires-jib? e))
                 (not (true? (:jib-verified? e))))
        [{:rule :jib-unverified
          :detail (str subject " はJIB(jedinstveni identifikacioni broj)確認を要するが未確認 -- 提出提案は進められない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (tax-social-security-arrears-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (jib-unverified-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
