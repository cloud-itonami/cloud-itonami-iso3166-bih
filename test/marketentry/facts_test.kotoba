(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest bih-has-spec-basis
  (let [sb (facts/spec-basis "BIH")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/rep-spec-basis "BIH")))
    (is (some? (facts/corporate-number-spec-basis "BIH")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "BIH")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "BIH" all)))
    (is (not (facts/required-evidence-satisfied? "BIH" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["BIH" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))

(deftest registration-spec-basis-is-entity-level-not-state-level
  (testing "business registration is honestly modeled as Entity-level, NOT part of the state-level corporate-number regime"
    (let [regs (facts/registration-spec-basis "BIH")]
      (is (some? regs))
      (is (some? (:fbih regs)))
      (is (some? (:rs regs)))
      (is (nil? (:brcko regs)) "Brčko District deliberately not covered -- honest omission")
      (is (some? (facts/registration-spec-basis-for-level "BIH" :fbih)))
      (is (some? (facts/registration-spec-basis-for-level "BIH" :rs)))
      (is (nil? (facts/registration-spec-basis-for-level "BIH" :brcko)))
      (is (nil? (facts/registration-spec-basis "ATL")))))
  (testing "the state-level corporate-number regime is a SEPARATE, DERIVED number, not company registration itself"
    (is (some? (facts/corporate-number-spec-basis "BIH")))))
