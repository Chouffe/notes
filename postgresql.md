# PostgreSQL

## Commands

* [Cheatsheet](https://postgrescheatsheet.com/#/tables)
* Getting Help of SQL commands
```
\h
```
* Getting help on special variables
```
\?
```
* List databases
```
\l
```
* List schemas
```
\dn
```
* Set schema to another one
```
SET SEARCH_PATH TO "temp-arthur-dev"
```
* List tables
```
\dt
```
* List indexes
```
\di
```
* List extensions
```
\dx
```
* List roles
```
\dg
```
* List users
```
\du
```
* Connect to a DB
```
\c databaseName
```

## Users

* List users
```
\du
```
* Create a user
```
CREATE USER username;
```
* Create a user with password
```
CREATE USER test WITH PASSWORD 'password';
```
* Create a user with an encrypted password
```
CREATE USER readonly WITH ENCRYPTED PASSWORD 'readonly';
```
* Grant privileges
```
GRANT USAGE ON SCHEMA public to readonly;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO readonly;

-- On each database
GRANT CONNECT ON DATABASE foo to readonly;
\c foo
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO readonly; --- this grants privileges on new tables generated in new database "foo"
GRANT USAGE ON SCHEMA public to readonly;
GRANT SELECT ON ALL SEQUENCES IN SCHEMA public TO readonly;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO readonly;
```
* Drop a user
```
DROP USER username;
```

## Arrays

* They are 1-indexed
* Retrieve one element from an array
```
-- Retrieve the first element
arr[1]
```

## REGEXES

* REGEXP_MATCHES for extracting part of a string
```sql
-- g to convert to an array (global)
SELECT
  REGEXP_MATCHES(data::TEXT, '.*editor-id","~u(.{36})"', 'g') AS data_editor_id
  ...
```


## JSON

* [Cheatsheet](https://devhints.io/postgresql-json)

## Misc

* By default, postgres tries to connect to a database with the same name as your user. To prevent this default behaviour, just specify user and database:
```
psql -U Username DatabaseName
```
* Check that the database is ready
```
pg_isready --dbname=pitch --host=localhost --port=63333
```
* List top 20 table sizes
```
SELECT nspname || '.' || relname AS "relation",
    pg_size_pretty(pg_total_relation_size(C.oid)) AS "total_size"
  FROM pg_class C
  LEFT JOIN pg_namespace N ON (N.oid = C.relnamespace)
  WHERE nspname NOT IN ('pg_catalog', 'information_schema')
    AND C.relkind <> 'i'
    AND nspname !~ '^pg_toast'
  ORDER BY pg_total_relation_size(C.oid) DESC
  LIMIT 20;
```
* Sample a percentage of rows with specified random seed
```
-- sample a percentage of rows with specified random seed
TABLESAMPLE BERNOULLI ({sample_percentage}) REPEATABLE (42)
```

## Monitoring

* [PostgreSQL Monitoring](https://www.datadoghq.com/blog/postgresql-monitoring/)
