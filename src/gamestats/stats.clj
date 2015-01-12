(ns gamestats.stats
  (:use gamestats.round.reader)
  (:require gamestats.records)
  (:import (gamestats.records
     Player-round
     Player-stats)
  )
)

(declare aggregate-players create-stats-map calculate-stats)

(def sorted-playermap (atom {}))

(defn top-ten [prs]
  (println "size:" (count prs))
  (let [pm  (create-stats-map (seq (aggregate-players prs)))]
  (reset! sorted-playermap (take 10 (into (sorted-map-by 
      (fn [key1 key2] (compare [(:total-score (key2 pm) key2)] [(:total-score (key1 pm) key1)]))
    ) pm ))
  ))
  @sorted-playermap
)

(defn- aggregate-players [prs]
  (loop [i (dec (count prs)) playerscores (transient {})]
    (if (>= i 0)
      (let [pr (nth prs i) player (keyword (:player pr)) score (:score pr)]
      (let [ps (player playerscores)]
      (recur (dec i)
        (if (nil? ps)
          (assoc! playerscores player (transient [score]))
          (assoc! playerscores player (conj! ps score))
        )
      )))
      (persistent! playerscores)
    )
  )
)

(defn- create-stats-map [player-scores]
  (loop [i (dec (count player-scores)) playermap (transient {})]
    (if (>= i 0)
      (let [ps (nth player-scores i) player (key ps) scores (persistent! (val ps))]
      (recur (dec i) (assoc! playermap player (calculate-stats player scores))))
      (persistent! playermap)
    )
  )
)

(defn refresh []
  (while true 
    (top-ten (load-all))
    (Thread/sleep 10000)
  )
)

(defn- calculate-stats [player scores]
  (let [
    n (count scores)
    total (reduce + scores)
    average (double (/ total n))
    squared-diff (map #(Math/pow (- % average) 2) scores)
    standard-deviation (Math/sqrt (/ (reduce + squared-diff) n))]
    (Player-stats. player total n average standard-deviation)
  )
)

