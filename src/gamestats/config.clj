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
