; Copyright (C) 2015, Fabrizio Cannizzaro - alcinoo.cannizzaro@gmail.com
; This file is part of gamestats.
;
;    gamestats is free software: you can redistribute it and/or modify
;    it under the terms of the GNU General Public License as published by
;    the Free Software Foundation, either version 3 of the License, or
;    (at your option) any later version.
;
;    gamestats is distributed in the hope that it will be useful,
;    but WITHOUT ANY WARRANTY; without even the implied warranty of
;    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;    GNU General Public License for more details.
;
;    You should have received a copy of the GNU General Public License
;    along with gamestats.  If not, see <http://www.gnu.org/licenses/>.

(ns gamestats.round.writer
  (:use
    gamestats.config
    clojure.java.io 
  )
  (:import (java.util.zip 
	  ZipOutputStream
	  ZipEntry)
  )
)

(declare create-folder write-zip-data create-round-name)

(defn create-tree []
  (dotimes [i (:n-server config)]
     (dotimes [j (:n-game config)]
       (let [server (str "server" i) game (str "game" j)]
       (create-folder server game)
       (let [ts (quot (System/currentTimeMillis) 1000)]
       (dotimes [k (:n-round config)]
         (write-zip-data (create-round-name ts k) server game)
        )))
     )
  )
)

(defn incr-tree [n-server n-games n-round]
  (while true
  (dotimes [i n-server]
     (dotimes [j n-games]
       (let [server (str "server" (rand-int(:n-server config))) game (str "game" (rand-int(:n-game config)))]
       (let [ts (quot (System/currentTimeMillis) 1000)]
       (dotimes [k n-round]
         (write-zip-data (create-round-name ts k) server game)
        )))
     )
  )
  (Thread/sleep 10000) 
  )
)

(defn- create-folder
  [server game]
  (println "create-folder" server game)
  (.mkdirs (file (format "%s/%s/%s" (:root-path config) server game)))
)

(defn- write-zip-data
  [round server game]
  (let [file-name (format "%s/%s/%s/%s.zip" (:root-path config) server game round)]
  (with-open [zos (-> file-name output-stream ZipOutputStream.)]
   (.putNextEntry zos (ZipEntry. (str round '.txt)))
   (dotimes [i (:n-player config)]
     (.write zos (.getBytes (format "player%d:%d\n" i (inc(rand-int 100)))))
   )
  )
  (println "file" file-name "created")
  )
)

(defn- create-round-name [ts k]
  (format "%d.%d_round" ts k)
 )


