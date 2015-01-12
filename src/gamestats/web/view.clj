(ns gamestats.web.view
  (:use hiccup.core)
  (:use hiccup.page)
  (:use hiccup.form)
  (:require gamestats.records)
  (:import (gamestats.records
     Player-stats))
)

(declare map-tag fmtdbl fmtlng)
(def ^:const c "center")
(def ^:const r "right")
(def ^:const l "left")

(defn- view-layout [& content]
  (html
    (doctype :xhtml-strict)
    (xhtml-tag "en"
      [:head
        [:meta {:http-equiv "refresh" :content "5"}]
        [:meta {:http-equiv "Content-type" :content "text/html; charset=utf-8"}]
        [:title "gamestats"]
        [:link {:href "/gamestats.css" :rel "stylesheet" :type "text/css"}]]
      [:body content]))
)

(defn view-stats [stats]
  (view-layout   
   [:h1 "Top 10 Players"]
   [:hr]
   [:table {:width "100%"}
    [:tr (map-tag :th ["Player" "Total score" "Average" "Round Count" "Standard Deviation"] [c c c c c])]
    (for [ps stats]
       [:tr (map-tag :td [
         (:player ps) 
         (fmtlng (:total-score ps))
         (fmtdbl (:average ps)) 
         (fmtlng (:round-count ps))
         (fmtdbl (:standard-deviation ps))
         ] 
         [l r r r r])]
    )
   ])
 )

(defn- fmtdbl [d]
  (format "%.2f" d)
)

(defn- fmtlng [d]
  (format "%,d" d)
)
 
(defn- map-tag
  ([tag xs]
    (map (fn [x] [tag x]) xs)
  )
  ([tag xs cs]
  (map (fn [x y][tag {:class y} x ]) xs cs)
 )
)
  