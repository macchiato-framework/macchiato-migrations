(defproject macchiato/migrations "0.0.5"
  :description "SQL migrations library"
  :url "https://github.com/yogthos/macchiato-framework/macchiato-migrations"
  :scm {:name "git"
         :url "https://github.com/macchiato-framework/macchiato-migrations.git"}
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :clojurescript? true
  :dependencies [[com.taoensso/timbre "4.10.0"]
                 [org.clojure/clojure "1.9.0" :scope "provided"]
                 [org.clojure/clojurescript "1.9.946" :scope "provided"]
                 [macchiato/fs "0.2.1"]]
  :plugins [[codox "0.6.4"]
            [lein-doo "0.1.7"]
            [lein-npm "0.6.2"]
            [lein-cljsbuild "1.1.4"]]

  :npm {:dependencies [[postgrator "3.1.0"]]}
  :profiles {:test
             {:npm {:dependencies [[pg "7.4.0"]]}
              :cljsbuild
                   {:builds
                    {:test
                     {:source-paths ["src" "test"]
                      :compiler     {:main          macchiato.test.runner
                                     :output-to     "target/test/core.js"
                                     :target        :nodejs
                                     :optimizations :none
                                     :source-map    true
                                     :pretty-print  true}}}}
              :doo {:build "test"}}}

  :aliases
  {"test"
   ["do"
    ["with-profile" "test" "npm" "install"]
    ["clean"]
    ["with-profile" "test" "doo" "node" "once"]]
   "test-watch"
   ["do"
    ["with-profile" "test" "npm" "install"]
    ["clean"]
    ["with-profile" "test" "doo" "node"]]})
