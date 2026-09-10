(ns statute.facts
  "General-law compliance catalog for Bosnia and Herzegovina (BIH) --
  extends this repo's existing `marketentry.facts` (public-procurement
  market-entry only, narrow scope) with a second, orthogonal catalog of
  statutes a company operating in this jurisdiction must generally track
  for compliance. Mirrors cloud-itonami-iso3166-bgr/-bhs's `statute.facts`
  (ADR-2607141700, cloud-itonami-compliance-fact-federation).

  BiH's constitutional structure is NOT unitary: a state level, TWO
  Entities (the Federation of Bosnia and Herzegovina / FBiH and Republika
  Srpska / RS), plus the Brčko District, each hold their own overlapping
  or exclusive competencies. This iteration specifically investigated,
  rather than assumed, which level each candidate statute actually
  operates at -- and the honest answer is genuinely mixed, unlike every
  prior sibling in this family so far:

  - Data protection is STATE-level (Zakon o zaštiti ličnih podataka).
  - Labour law is ENTITY-level -- FBiH and RS each have their OWN
    separate Zakon o radu; there is NO single state-level labour code.
  - Company/business REGISTRATION (the procedural act of incorporating
    a business subject into a registry) is ALSO Entity-level -- FBiH
    and RS each have their own Zakon o registraciji poslovnih subjekata,
    administered through their own registry COURTS (not a state-level
    company registrar the way e.g. BGR's Registry Agency is).
  - Brčko District has its own parallel regime for both labour law and
    business registration; this iteration did NOT independently fetch
    and verify Brčko District's own statutes within its time budget --
    that is an honest, deliberate omission, not a claim that FBiH+RS
    coverage extrapolates to the whole country. A market entrant whose
    registered seat is in Brčko District needs a citation this catalog
    does not yet provide.

  Every entry below cites an OFFICIAL government/entity-gazette-hosted
  URL or a body that directly administers the law -- never fabricated,
  and every entry was WebFetch/curl-fetched and `pdftotext -layout`-read
  in full (2026-07-22), not taken from a secondary summary:

  - Law on Personal Data Protection (Zakon o zaštiti ličnih podataka) --
    fetched directly from the Official Gazette's own publisher
    (sluzbenilist.ba, the entry recording 'Službeni glasnik Bosne i
    Hercegovine' broj 12/25 od 28.2.2025) AND independently
    cross-confirmed via the High Judicial and Prosecutorial Council of
    BiH's own legal database (csd.pravosudje.ba), which gave the
    identical citation and confirmed the law's entry-into-force (8th
    day after publication, i.e. 2025-03-08) and 210-day delayed
    application (2025-10-04). This is a BRAND NEW law -- it replaced
    the older Zakon o zaštiti ličnih podataka (Sl. glasnik BiH 49/06,
    76/11, 89/11 ispravka), which this session also fetched directly
    (justice-portal parlament.ba document repository) to confirm the
    supersession and that both versions establish the SAME supervisory
    body, Agencija za zaštitu ličnih podataka u Bosni i Hercegovini
    (AZLP), Art. 1(2) in both texts. Confidence: HIGH (two independent
    official/quasi-official-source confirmations of the current
    citation; the OLD law's own text was read in full to confirm the
    Agency's continuity across the re-enactment).
  - Zakon o radu (Federacija Bosne i Hercegovine) -- fetched directly
    (pufbih.ba, the FBiH Tax Administration's own hosted consolidated
    text) and read in full via `pdftotext -layout`; its own title page
    reads '(\"Službene novine Federacije BiH\", broj: 26/16 i 89/18) -
    prečišćena neslužbena verzija'. Later amendments (23/20, 49/21,
    103/21, 44/22, 39/24 per secondary legal-database listings) were
    NOT independently fetched/read this iteration -- the citation below
    covers the text this session actually verified (26/16 base +
    89/18 amendment), not the full current amendment chain. Confidence:
    HIGH for the base+89/18 text actually read; the existence of later
    amendments is noted but not independently verified.
  - Zakon o radu Republike Srpske -- fetched directly (fipa.gov.ba, the
    Foreign Investment Promotion Agency of BiH's own hosted copy) and
    read in full via `pdftotext -layout`; its own closing articles
    (Čl. 272) confirm it REPEALS the prior Zakon o radu (Sl. glasnik RS
    38/00, 40/00, 47/02, 38/03, 66/03, 20/07) and (Čl. 273) enters into
    force the 8th day after gazette publication, adopted/signed 29
    December 2015 (Broj: 02/1-021-1715/15). The fetched PDF's own text
    does not carry its OWN gazette number on a page this session could
    read (unlike the FBiH law's clean masthead), so the citation 'Sl.
    glasnik RS 1/16' below rests on the repeal-date/adoption-date match
    plus convergent secondary legal-database citations (paragraf.ba,
    helpdesk.unijauprs.org), not a directly-read masthead. Confidence:
    MODERATE for the exact gazette number; HIGH that this specific
    fetched full text IS the current RS Zakon o radu (confirmed by its
    own repeal clause superseding the correct predecessor).
  - Zakon o registraciji poslovnih subjekata u Federaciji Bosne i
    Hercegovine -- fetched directly (pufbih.ba) and read in full; its
    own title page reads '(\"Službene novine Federacije BiH\", broj:
    27/05, 68/05, 43/09 i 63/14) - prečišćeni neslužbeni tekst'. Governs
    registration in the SUDSKI REGISTAR (court registry) -- Čl. 1 names
    the 'nadležni registarski sud' (competent registry COURT) as the
    deciding authority, per Čl. 3(2)-(3)'s own definitions. Confidence:
    HIGH (primary consolidated text fetched and read in full, masthead
    matches).
  - Zakon o registraciji poslovnih subjekata u Republici Srpskoj --
    fetched directly (portalfo2.pravosudje.ba, the High Judicial and
    Prosecutorial Council of BiH's own document store) and read in
    full; its own title page reads '(Objavljen u \"Sl. glasniku RS\",
    br. 67 od 8. avgusta 2013)'. GENUINELY NEW INSTITUTIONAL-STRUCTURE
    FINDING for this catalog: unlike the initial secondary-source
    impression that APIF (Agencija za posredničke, informatičke i
    finansijske usluge) simply replaced the court registry in RS, the
    fetched primary text's own Čl. 2 definitions confirm registration
    STILL runs through a 'nadležni registarski sud' (competent registry
    court, designated by the Zakon o sudovima Republike Srpske) exactly
    as in FBiH -- APIF's role, per the SAME Čl. 2(g), is a
    'jednošalterski sistem' (one-stop-shop/single-counter SERVICE) that
    channels the registration application to the court AND, in the same
    transaction where possible, obtains the applicant's jedinstveni
    identifikacioni broj (JIB) from the competent tax authority (and a
    customs number from UIO where applicable) -- an intermediary
    front-end, not a replacement registrar. Later amendments (15/16,
    84/19 per secondary legal-database listings) were NOT independently
    fetched/read this iteration. Confidence: HIGH for the base 67/13
    text and the court-vs-APIF institutional correction (both directly
    read from the primary text); the later-amendment chain is
    secondary-sourced only.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url. `:statute/level` is a field this
  iteration ADDS to the shared schema (no prior sibling needed it, per
  a GitHub-org-wide check of existing catalogs before this session
  wrote its own) -- it is `:state`, `:fbih`, or `:rs`, and its presence
  in every BIH entry below is deliberate: this jurisdiction's own
  institutional-complexity note (see `marketentry.facts`) would get
  silently flattened into a single-country generalization without it.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit. `:statute/level` names the
  constitutional level this specific statute operates at."
  {"BIH"
   [{:statute/id "bih.personal-data-protection-act"
     :statute/title "Zakon o zaštiti ličnih podataka (Law on Personal Data Protection)"
     :statute/jurisdiction "BIH"
     :statute/level :state
     :statute/kind :law
     :statute/law-number "Službeni glasnik BiH, broj 12/25 (od 28.2.2025)"
     :statute/url "https://www.sluzbenilist.ba/page/akt/aCRNh0ohz4nh78h77P7BE="
     :statute/url-provenance :official-gazette
     :statute/enacted-date "2025-03-08"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:data-protection :privacy}}
    {:statute/id "bih.fbih-labour-law"
     :statute/title "Zakon o radu (Federacija Bosne i Hercegovine) -- Federation of Bosnia and Herzegovina Labour Law"
     :statute/jurisdiction "BIH"
     :statute/level :fbih
     :statute/kind :law
     :statute/law-number "Službene novine Federacije BiH, broj: 26/16 i 89/18 (prečišćena neslužbena verzija -- later amendments 23/20, 49/21, 103/21, 44/22, 39/24 not independently verified this iteration)"
     :statute/url "https://www.pufbih.ba/v1/public/upload/zakoni/f0787-zakon-o-radu-precisceni-tekst.pdf"
     :statute/url-provenance :official-fbih-tax-administration
     :statute/enacted-date "2016-04-14"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:labor :employment}}
    {:statute/id "bih.rs-labour-law"
     :statute/title "Zakon o radu Republike Srpske -- Republika Srpska Labour Law"
     :statute/jurisdiction "BIH"
     :statute/level :rs
     :statute/kind :law
     :statute/law-number "Sl. glasnik RS 1/16 (gazette number MODERATE confidence -- inferred from adoption date 2015-12-29 + repeal of Sl. glasnik RS 38/00,40/00,47/02,38/03,66/03,20/07, not a directly-read masthead; later amendments 66/18, 91/21, 119/21, 112/23, 39/24 not independently verified)"
     :statute/url "https://www.fipa.gov.ba/publikacije_materijali/zakoni/04.08.2016.Zakon%20o%20radu%20RS.pdf"
     :statute/url-provenance :official-bih-fipa
     :statute/enacted-date "2015-12-29"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:labor :employment}}
    {:statute/id "bih.fbih-business-registration-act"
     :statute/title "Zakon o registraciji poslovnih subjekata u Federaciji Bosne i Hercegovine"
     :statute/jurisdiction "BIH"
     :statute/level :fbih
     :statute/kind :law
     :statute/law-number "Službene novine Federacije BiH, broj: 27/05, 68/05, 43/09 i 63/14"
     :statute/url "https://www.pufbih.ba/v1/public/upload/zakoni/14623-zakon-o-registraciji-poslovnih-subjekata-ispravan-tekst.pdf"
     :statute/url-provenance :official-fbih-tax-administration
     :statute/enacted-date "2005-05-14"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "bih.rs-business-registration-act"
     :statute/title "Zakon o registraciji poslovnih subjekata u Republici Srpskoj"
     :statute/jurisdiction "BIH"
     :statute/level :rs
     :statute/kind :law
     :statute/law-number "Sl. glasnik RS, broj 67/13 (od 8. avgusta 2013; later amendments 15/16, 84/19 not independently verified)"
     :statute/url "https://portalfo2.pravosudje.ba/vstvfo-api/vijest/download/26479"
     :statute/url-provenance :official-vstv-pravosudje
     :statute/enacted-date "2013-08-08"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:corporate-governance :incorporation}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn spec-basis-at-level
  "The jurisdiction's statute vector narrowed to a single constitutional
  `level` (:state/:fbih/:rs) -- the accessor this family needed for a
  country whose statutes genuinely split by level, rather than treating
  `spec-basis` as if it always describes one flat national regime."
  [iso3 level]
  (filterv #(= level (:statute/level %)) (spec-basis iso3)))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-bih statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "BIH")) " BIH statutes seeded with an "
                 "official government-hosted citation, spanning STATE + FBiH + RS "
                 "levels (Brčko District not yet covered -- honest omission, see "
                 "namespace docstring). Extend `statute.facts/catalog`, never "
                 "fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :data-protection)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
