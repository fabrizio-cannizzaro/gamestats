(ns gamestats.records)

(defrecord Player-round [server game round player score])
(defrecord Player-stats [player total-score round-count average standard-deviation])