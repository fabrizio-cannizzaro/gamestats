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

(ns gamestats.config
  (:import (java.util ResourceBundle))
)

(def rb (ResourceBundle/getBundle "gamestats"))

(defn- int-config [key] (Integer/parseInt (.getString rb key)))

(def config {
  :root-path (.getString rb "root-path")
  :n-server (int-config "n-server")
  :n-game (int-config "n-game")
  :n-round (int-config "n-round")
  :n-player (int-config "n-player")
  :port (int-config "port")
  }
)
