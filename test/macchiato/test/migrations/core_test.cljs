(ns macchiato.test.migrations.core-test
  (:require
    [macchiato.fs.path :as path]
    [macchiato.migrations.core :as migrations]
    [taoensso.timbre :as timbre :refer-macros [info]]
    [cljs.test :refer-macros [async is are deftest testing use-fixtures]]))

(deftest migrations
  (async done
         (migrations/migrate
          {:host "127.0.0.1"
           :port 5432
           :username "admin"
           :password "admin"
           :database "postgres"
           :driver "pg"
           :migration-dir (path/resolve "test/migrations")}
          :max
          (fn [result]

            (is (=
                 [{"version" 1
                   "action" "do"
                   "filename" "001.do.users.sql"
                   "name" "users"
                   "md5" "e159df3a58728ad0bbed1ca06e7c0a9a"
                   "getSql" nil}]
                 (assoc-in result [0 "getSql"] nil)))
            (done)))))
