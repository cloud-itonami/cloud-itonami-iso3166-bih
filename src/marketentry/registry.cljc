(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `tax-social-security-arrears-violation?` is the SAME discipline
  applied to a genuinely Bosnia-and-Herzegovina-specific statutory
  ground, but with a DIFFERENT SHAPE than sibling actors' numeric
  threshold recomputes: ZJN (Zakon o javnim nabavkama, Law on Public
  Procurement) Čl. 45(1)(c)-(d) makes ANY unpaid pension/health-
  insurance contribution or direct/indirect tax obligation a MANDATORY
  exclusion ground -- WebFetch/curl-verified 2026-07-22 against the
  Parliamentary Assembly of BiH's own document repository
  (parlament.ba), read in full via `pdftotext -layout` -- and there is
  NO de-minimis/percentage-of-turnover carve-out the way a sibling
  jurisdiction's own procurement-exclusion statute has (a 1%-of-
  turnover cap). The ONLY accepted alternative proof (Čl. 45(3)) is a
  CONFIRMED reprogrammed/deferred-payment arrangement with the tax
  authority. So this check is a BINARY ground-truth recompute (is there
  an undisputed positive arrears amount AND no confirmed reprogram
  agreement on file?), not a numeric statutory-threshold recompute --
  the ABSENCE of a de-minimis carve-out is itself the honestly-modeled
  difference from that other check shape, not an oversight.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement portal. It builds the RECORD an
  operator would keep, not the act of submitting a portal registration
  itself (that is `marketentry.operation`'s `:filing/submit`, always
  human-gated -- see README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

(defn tax-social-security-arrears-violation?
  "ZJN Čl. 45(1)(c)-(d): does `engagement` have an undisputed positive
  `:tax-arrears-amount` with NO confirmed reprogrammed/deferred-payment
  arrangement (`:tax-arrears-reprogrammed-confirmed?`) on file? There is
  NO de-minimis carve-out in this jurisdiction's own statute -- any
  undisputed unpaid amount, absent a Čl. 45(3) confirmed reprogram
  agreement, is a mandatory exclusion ground. Missing/zero arrears never
  violate."
  [{:keys [tax-arrears-amount tax-arrears-reprogrammed-confirmed?]}]
  (and (> (double (or tax-arrears-amount 0)) 0.0)
       (not (true? tax-arrears-reprogrammed-confirmed?))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  portal."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
