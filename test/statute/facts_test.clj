(ns statute.facts-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [statute.facts :as facts]))

(deftest bih-has-spec-basis
  (let [sb (facts/spec-basis "BIH")]
    (is (= 5 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))
    (is (every? :statute/level sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["BIH" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= 2 (count (facts/by-topic "BIH" :labor))))
  (is (empty? (facts/by-topic "BIH" :environment)))
  (is (empty? (facts/by-topic "ATL" :labor))))

(deftest spec-basis-at-level-splits-by-constitutional-level
  (testing "state/fbih/rs each return only their own entries"
    (is (= 1 (count (facts/spec-basis-at-level "BIH" :state))))
    (is (= 2 (count (facts/spec-basis-at-level "BIH" :fbih))))
    (is (= 2 (count (facts/spec-basis-at-level "BIH" :rs))))
    (is (empty? (facts/spec-basis-at-level "BIH" :brcko)))
    (is (empty? (facts/spec-basis-at-level "ATL" :state)))))
