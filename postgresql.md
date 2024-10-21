# PostgreSQL

## Commands

* [Cheatsheet](https://postgrescheatsheet.com/#/tables)

* Getting Help of SQL commands

```sql
\h
```

* Getting help on special variables

```sql
\?
```

* List databases

```sql
\l
```

* List schemas

```sql
\dn
```

* Set schema to another one

```sql
SET SEARCH_PATH TO "temp-arthur-dev"
```

* List tables

```sql
\dt
```

* List indexes

```sql
\di
```

* List extensions

```sql
\dx
```

* List roles

```sql
\dg
```

* List users

```sql
\du
```

* Connect to a DB

```sql
\c databaseName
```

* Enable timing of commands

```sql
\timing
```

## Users

* List users

```sql
\du
```

* Create a user

```sql
CREATE USER username;
```

* Create a user with password

```sql
CREATE USER test WITH PASSWORD 'password';
```

* Create a user with an encrypted password

```sql
CREATE USER readonly WITH ENCRYPTED PASSWORD 'readonly';
```

* Grant privileges

```sql
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

```sql
DROP USER username;
```

## Logging

Enable DEBUG level logging - can be useful for troubleshooting procedures/extensions

```sql
SET client_min_messages TO DEBUG;
```

## Arrays

* They are 1-indexed
* Retrieve one element from an array

```sql
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

```sql
psql -U Username DatabaseName
```

* Check that the database is ready

```sql
pg_isready --dbname=pitch --host=localhost --port=63333
```

* List top 20 table sizes

```sql
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

```sql
-- sample a percentage of rows with specified random seed
TABLESAMPLE BERNOULLI ({sample_percentage}) REPEATABLE (42)
```

## Monitoring

* [PostgreSQL Monitoring](https://www.datadoghq.com/blog/postgresql-monitoring/)

## Extensions

### `pg_partman`

#### Resources

- [Release blog post of new partman](https://www.keithf4.com/posts/2023-05-30-new-hugo-new-partman/)
- [Breaking Up Tables with Partitioning](https://postgresql.us/events/pgconfnyc2023/sessions/session/1309/slides/114/It's%20Not%20You,%20It's%20Me%20-%20Breaking%20Up%20Tables%20with%20Partitioning.pdf)
- [PostgreSQL Locks Deep Dive](https://medium.com/@hnasr/postgres-locks-a-deep-dive-9fc158a5641c)
- [pg_partman tutorial](https://neon.tech/docs/extensions/pg_partman)
