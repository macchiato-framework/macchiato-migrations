(ns macchiato.test.migrations.core-test
  (:require
    [macchiato.fs.path :as path]
    [macchiato.migrations.core :as migrations]
    [cljs.test :refer-macros [async is are deftest testing use-fixtures]]))

(deftest migrations
  (migrations/migrate
   {:host "127.0.0.1"
    :port 5432
    :username "admin"
    :password "admin"
    :database "postgres"
    :driver "pg"
    :migration-dir (path/resolve "test/migrations")}))
