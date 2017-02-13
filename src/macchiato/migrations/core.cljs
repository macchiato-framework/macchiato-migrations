(ns macchiato.migrations.core
  (:require [cljs.nodejs :as node]
            [macchiato.fs.path :as path]
            [taoensso.timbre :as timbre
             :refer-macros [info error]]))

(def ^:private postgrator (node/require "postgrator"))

(defn- translate-config [{:keys [migration-dir schema-table driver host port database username password connection-string]
                          :or   {migration-dir (str (js/__dirname "+" path/separator "migrations"))
                                 schema-table  "schema_migrations"}}]
  (clj->js
    {:migrationDirectory migration-dir
     :schemaTable        schema-table
     :driver             driver
     :host               host
     :database           database
     :username           username
     :password           password
     :connectionString   connection-string}))

(defn- on-end-migrations [cb]
  (fn [err migrations]
    (if err
      (error err)
      (info migrations))
    (.endConnection postgrator (or cb #()))))

(defn migrate
  ([config] (migrate config "max" nil))
  ([config version] (migrate config version nil))
  ([config version cb]
   (.setConfig postgrator (translate-config config))
   (.migrate version (on-end-migrations cb))))


