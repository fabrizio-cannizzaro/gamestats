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

(ns gamestats.web.controller
  (:use gamestats.web.view)
  (:use compojure.core)
  (:use gamestats.stats)
  (:use ring.middleware.resource)  
  (:require 
	  [compojure.route :as route]
	  [compojure.handler :as handler])
)

(defroutes app
  (GET "/" []
    (view-stats (vals @sorted-playermap))
   )
   (route/resources "/")
   (route/not-found "Page not found")
)
