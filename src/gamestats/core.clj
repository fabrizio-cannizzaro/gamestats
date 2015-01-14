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