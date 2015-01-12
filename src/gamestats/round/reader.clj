(ns gamestats.round.reader
  (:use
    gamestats.config
    clojure.java.io 
    [clojure.string :only (split)]
  )
  (:require gamestats.records)
  (:import (java.util.zip 
	  ZipEntry
	  ZipInputStream)
   (java.io InputStreamReader)
   (gamestats.records Player-round)
  )  
)

(declare load-rounds load-games read-data load-result-file)

(defn load-all []
  (let [servers (.listFiles (file (:root-path config)))]
  (loop [i (dec (count servers)) prs (transient [])]
    (if (>= i 0)
      (let [server (get servers i)]
      (recur (dec i) (load-games prs server)))
      (persistent! prs)
    )
  ))
)


(defn- load-games [prs server]
  (let [games (.listFiles server)]
  (loop [i (dec (count games)) ret prs]
    (if (>= i 0)
     (let [game (get games i)]
     (recur (dec i) (load-rounds prs server game)))
     ret
    )
  ))  
)
  
(defn- load-rounds [prs server game]
  (let [rounds (.listFiles game)]
  (loop [i (dec (count rounds)) ret prs]
    (if (>= i 0)
      (let [round (get rounds i)]
      (recur (dec i) (read-data prs round server game)))
      ret
    )
  ))  
)

(defn- read-data
  [prs round-file server game]
  (let [seq-v (load-result-file round-file) n (count seq-v)]
  (loop [i 0 ret prs]
    (if (< i n)
      (recur (inc i)
        (let [v (nth seq-v i)]
        (let [mpr (Player-round. (.getName server) (.getName game) (.getName round-file) (get v 0) (Integer/parseInt (get v 1)))]
        (conj! ret mpr)))
      )
      ret
    )
  ))
)

(defn- load-result-file [round-file]
  (with-open [zis (ZipInputStream. (input-stream (.getPath round-file)))]
   (.getNextEntry zis)
   (let [lines (line-seq (reader (InputStreamReader. zis)))]
   (doall (map #(split % #":") lines)))
  )
)
