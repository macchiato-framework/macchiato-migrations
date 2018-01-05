(ns macchiato.migrations.core
  (:require [cljs.nodejs :as node]
            [macchiato.fs.path :as path]
            [taoensso.timbre :as timbre
             :refer-macros [info error]]
            ["postgrator" :as Postgrator]))

(defn- translate-config [{:keys [migration-dir
                                 schema-table
                                 driver
                                 host
                                 port
                                 database
                                 username
                                 password
                                 connection-string
                                 request-timeout
                                 options
                                 ssl]
                          :or   {migration-dir (str js/__dirname path/separator "migrations")
                                 schema-table  "schema_migrations"}}]
  (clj->js
    (merge
     {:migrationDirectory migration-dir
      :schemaTable        schema-table
      :driver             driver}
     (if connection-string
       {:connectionString connection-string}
       {:host     host
        :database database
        :username username
        :password password})
     (when ssl
       {:ssl ssl})
     (when request-timeout
       {:requestTimeout request-timeout})
     (when options
       {:options (clj->js options)}))))

(defn- error-handler [e]
  (error e "An error occured while running migrations!")
  (error (js->clj (.-appliedMigrations e))))

(defn- info-handler [message]
  (fn [i]
    (info message (dissoc (js->clj i) "getSql"))))

(defn migrate
  ([config] (migrate config :max nil))
  ([config version] (migrate config version nil))
  ([config version cb]
   (let [postgrator (Postgrator. (translate-config config))]
     (.on postgrator "migration-started" (info-handler "starting migration:"))
     (.on postgrator "migration-finished" (info-handler "ending migration:"))
     (-> (if (= :max version)
           (.migrate postgrator)
           (.migrate postgrator version))
         (.then (or #(cb (js->clj %)) (info-handler "applied migrations:")))
         (.catch error-handler)))))
