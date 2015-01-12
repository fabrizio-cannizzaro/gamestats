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
