(defproject chatter "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [hiccup "1.0.5"]
                 [garden "1.3.0-SNAPSHOT"]
                 [environ "1.0.0"]]

  :plugins [[lein-ring "0.8.13"]
            [lein-environ "1.0.0"]]
  :ring {:handler chatter.handler/app
         :init chatter.handler/init
         :destroy chatter.handler/destroy}
  :aot :all
  :main chatter.handler
  :profiles
  {:dev
   {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}}
:production
{:ring
 {:open-browser? false, :stacktraces? false, :auto-reload? false}
 :env {:production true}}
:uberjar-name "chatter-standalone.jar")
