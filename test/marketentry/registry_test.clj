(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "BIH" 0)
        s (registry/register-submit "eng-1" "BIH" 0)]
    (is (= "BIH-DFT-000000" (get d "draft_number")))
    (is (= "BIH-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "BIH" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest tax-social-security-arrears-violation-has-no-de-minimis-carve-out
  (testing "ANY undisputed positive arrears amount violates, no matter how small -- unlike a sibling jurisdiction's percentage-of-turnover threshold, ZJN Čl. 45(1)(c)-(d) has NO de-minimis carve-out"
    (is (true? (registry/tax-social-security-arrears-violation?
                {:annual-turnover 2000000 :tax-arrears-amount 1
                 :tax-arrears-reprogrammed-confirmed? false})))
    (is (true? (registry/tax-social-security-arrears-violation?
                {:annual-turnover 1000000 :tax-arrears-amount 25000
                 :tax-arrears-reprogrammed-confirmed? false}))))
  (testing "a confirmed reprogrammed/deferred-payment arrangement (ZJN Čl. 45(3)) is the ONLY accepted alternative proof"
    (is (false? (registry/tax-social-security-arrears-violation?
                {:annual-turnover 1000000 :tax-arrears-amount 25000
                 :tax-arrears-reprogrammed-confirmed? true}))))
  (testing "no arrears declared -> never violates"
    (is (false? (registry/tax-social-security-arrears-violation? {:annual-turnover 2000000})))
    (is (false? (registry/tax-social-security-arrears-violation?
                {:annual-turnover 2000000 :tax-arrears-amount 0
                 :tax-arrears-reprogrammed-confirmed? false})))))
