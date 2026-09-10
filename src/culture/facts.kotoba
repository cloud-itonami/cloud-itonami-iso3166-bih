(ns culture.facts
  "Country-level regional-culture catalog for Bosnia and Herzegovina (BIH)
  -- national dishes, protected products, beverages, crafts, festivals and
  heritage sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"BIH"
   [{:culture/id "bih.dish.cevapi"
     :culture/name "Ćevapi"
     :culture/name-local "ćevapi / ћевапи"
     :culture/country "BIH"
     :culture/kind :dish
     :culture/summary "Grilled minced-meat dish popular in Southeast Europe, considered a national dish of Serbia and Bosnia and Herzegovina; usually served in groups of five to ten pieces in a flatbread."
     :culture/url "https://en.wikipedia.org/wiki/%C4%86evapi"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bih.dish.burek"
     :culture/name "Bosnian burek"
     :culture/country "BIH"
     :culture/kind :dish
     :culture/summary "Meat-filled pastry of the börek family, traditionally rolled in a spiral and cut into sections; in 2012 Lonely Planet included the Bosnian burek in 'The World's Best Street Food'."
     :culture/url "https://en.wikipedia.org/wiki/B%C3%B6rek"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bih.dish.bosanski-lonac"
     :culture/name "Bosnian pot"
     :culture/name-local "Bosanski lonac"
     :culture/country "BIH"
     :culture/kind :dish
     :culture/summary "Bosnian stew of layered chunked meat and vegetables, described as a national dish of Bosnia and Herzegovina."
     :culture/url "https://en.wikipedia.org/wiki/Bosanski_lonac"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bih.dish.begova-corba"
     :culture/name "Bey's soup"
     :culture/name-local "Begova čorba"
     :culture/country "BIH"
     :culture/kind :dish
     :culture/summary "Traditional festive chicken-and-vegetable soup from Bosnia and Herzegovina, originating in the Ottoman period and commonly served as a warm appetizer at weddings, celebrations and religious holidays."
     :culture/url "https://en.wikipedia.org/wiki/Begova_%C4%8Dorba"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bih.beverage.bosnian-coffee"
     :culture/name "Bosnian coffee"
     :culture/name-local "bosanska kahva"
     :culture/country "BIH"
     :culture/kind :beverage
     :culture/summary "Regional variant of Turkish-style coffee prepared in a cezve; coffee drinking in Bosnia is a traditional daily custom and plays an important role during social gatherings."
     :culture/url "https://en.wikipedia.org/wiki/Turkish_coffee"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bih.craft.konjic-woodcarving"
     :culture/name "Konjic woodcarving"
     :culture/country "BIH"
     :culture/kind :craft
     :culture/summary "Woodcarving technique practised in Konjic Municipality, Bosnia and Herzegovina, with recognizable hand-carved motifs; included on the UNESCO Representative List of the Intangible Cultural Heritage of Humanity in 2017."
     :culture/url "https://en.wikipedia.org/wiki/Konjic_woodcarving"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bih.festival.sarajevo-film-festival"
     :culture/name "Sarajevo Film Festival"
     :culture/country "BIH"
     :culture/kind :festival
     :culture/summary "The premier and largest film festival in Southeast Europe, founded in Sarajevo in 1995 during the siege of Sarajevo and held every August."
     :culture/url "https://en.wikipedia.org/wiki/Sarajevo_Film_Festival"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "bih.heritage.stari-most"
     :culture/name "Stari Most"
     :culture/name-local "Стари мост"
     :culture/country "BIH"
     :culture/kind :heritage
     :culture/summary "Rebuilt 16th-century Ottoman bridge over the Neretva in Mostar, destroyed in 1993 and reopened in 2004; the Old Bridge Area of the Old City of Mostar was designated a UNESCO World Heritage Site in 2005."
     :culture/url "https://en.wikipedia.org/wiki/Stari_Most"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-bih culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "BIH"))
                 " BIH entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
