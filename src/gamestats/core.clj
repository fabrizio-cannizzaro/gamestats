(ns gamestats.core
  (:gen-class)
  (:use
    gamestats.config
    gamestats.stats
    gamestats.round.writer
    gamestats.round.reader
    gamestats.web.controller
    ring.adapter.jetty
  )
  (:require gamestats.records)
  (:import (gamestats.records
   Player-stats)
  )
)

(declare start-writing start-refreshing)

(defn -main []
  (println config)
  (create-tree)
  (top-ten (load-all))
  (start-writing)
  (start-refreshing)
  (run-jetty app {:port (:port config)})
)

(defn- start-writing []
  (let [thread (Thread. #(incr-tree 1 1 1))]
  (.start thread))
)

(defn- start-refreshing []
  (let [thread (Thread. refresh)]
  (.start thread))
)