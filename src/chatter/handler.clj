(ns chatter.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [garden.core :refer [css]]
            [hiccup.page :as page]
            [hiccup.form :as form]
            [ring.middleware.params :refer [wrap-params]]))

(def styles
  (css [:p {:font-size "30px"}]
       [:h1 {:text-align :center :font-style "italic"}]))

(def chat-messages
    (atom [{:name "blue" :message "hello, world"}
          {:name "red" :message "red is my favorite color"}
          {:name "green" :message "green makes it go faster"}]))
(def new-message-form
  (form/form-to
      [:post "/"]
      "Name: " (form/text-field "name")
      "Message: " (form/text-field "msg")
       "Date:" (form/text-field "date")
      (form/submit-button "Submit")))

(defn- render-message
  [m]
  [:tr [:td (:name m)] [:td (:message m)] [:td (:date m)]])

(defn generate-message-view
  "This generates the HTML for displaying messages"
  [messages]
   (page/html5
    [:head
      [:title "chatter"]
    (page/include-css "/chatter.css")
    (page/include-css "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css")
    (page/include-js  "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js")
    ]
    [:body
     [:h1 "Our Chat App 1"]
     new-message-form
     [:p
      [:table#messages.table.table-bordered.table-hover
       (map render-message messages)]]]))

(defn update-messages!
  "This will update a message list atom"
  [messages name new-message new-date]
  (swap! messages conj {:name name :message new-message :date new-date}))

(defroutes app-routes
  (GET "/" [] (generate-message-view @chat-messages))
  (GET "/chatter.css" [] {:content-type "text/css" :body styles})
  (POST "/" {params :params}
        (let [name-param  (get params "name")
               msg-param (get params "msg")
              date-param (get params "date")
              new-messages (update-messages! chat-messages name-param msg-param date-param)]
          (generate-message-view new-messages)
          ))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app (wrap-params app-routes))
