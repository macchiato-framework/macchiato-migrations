(ns macchiato.test.migrations.core-test
  (:require
    [macchiato.fs.path :as path]
    [macchiato.migrations.core :as migrations]
    [cljs.test :refer-macros [async is are deftest testing use-fixtures]]))

(deftest migrations
  (async done
         (migrations/migrate
          {:host "127.0.0.1"
           :port 5432
           :username "postgres"
           :password "postgres"
           :database "homebox"
           :driver "pg"
           :migration-dir (path/resolve "test/migrations")}
          :max
          (fn []
            (done)))))
