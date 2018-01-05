### SQL migrations library for Macchiato

This library wraps the [postgrator](https://github.com/rickbergfalk/postgrator) NPM migrations module.

[![CircleCI](https://circleci.com/gh/macchiato-framework/macchiato-migrations.svg?style=svg)](https://circleci.com/gh/macchiato-framework/macchiato-migrations)

[![Clojars Project](https://img.shields.io/clojars/v/macchiato/migrations.svg)](https://clojars.org/macchiato/migrations)

### config options

Create a folder and stick some SQL scripts in there that change your database in some way. It might look like:

```
migrations/
  |- 001.do.sql
  |- 001.undo.sql
  |- 002.do.optional-description-of-script.sql
  |- 002.undo.optional-description-of-script.sql
  |- 003.do.sql
  |- 003.undo.sql
  |- ... and so on
The files must follow the convention [version].[action].[optional-description].sql.
```

Version must be a number, but you may start and increment the numbers in any way you'd like. If you choose to use a purely sequential numbering scheme instead of something based off a timestamp, you will find it helpful to start with 000s or some large number for file organization purposes.

Action must be either "do" or "undo". Do implements the version, and undo undoes it.

Optional-description can be a label or tag to help keep track of what happens inside the script. Descriptions should not contain periods.



* `:migration-dir` "migrations" ;default
* `:schema-table` "schema_migrations"
* `:driver` "pg" ;db driver
* `:host` "127.0.0.1"
* `:port` 5432 ;optional
* `:database` "databasename"
* `:username` "user"
* `:password` "password"
* `:connection-string` "tcp://username:password@hosturl/databasename"
* `:request-timeout` - timeout in milliseconds
* `:ssl` - boolean

## Usage

```
(require '[macchiato.migrations.core :as migrations])

(def config
  {:migration-dir "migrations" ;default
    :schema-table "schema_migrations"
    :driver "pg" ;db driver
    :connection-string "tcp://username:password@hosturl/databasename"})

;; migrate to the latest version
(migrations/migrate config)

;; migrate to a specific version
(migrations/migrate config "001")

;; migrate to a specific version and provide a callback to run when finished
(migrations/migrate config "001" #(println "migrations complete"))

;; migrate to the latest version and provide a callback to run when finished
(migrations/migrate config :max #(println "migrations complete"))
```
