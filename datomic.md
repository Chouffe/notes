# Datomic

## Vocabulary

* `Entity`: An entity is a set of datoms that are all about the same E
* `Point-In-Time Entity`: A point-in-time (as-of) view of an entity considers only datoms that whose Op is true as of a certain Tx

## Schema

* [Schema Reference](https://docs.datomic.com/cloud/schema/schema-reference.html)
* Example
```clojure
(def schema
  [{:db/doc "A users email."
    :db/ident :user/email
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db}])
```
* `:db/ident`: Attribute used to uniquely name an entity
* `:db/doc`: Documentation string for an entity
* `:db/valueType`: Type the entity will hold
  * `:db.type/keyword`
  * `:db.type/string`
  * `:db.type/ref`
  * `:db.type/instant`
  * `:db.type/long`
  * `:db.type/bigdec`
  * `:db.type/boolean`
  * `:db.type/float`
  * `:db.type/uuid`
  * `:db.type/double`
  * `:db.type/bigint`
  * `:db.type/uri`
* `:db/cardinality`: Cardinality of the field.
  * `:db.cardinality/one`
  * `:db.cardinality/many`
* `:db/unique`: whether the attribute is unique to each entity
* `:db.install/_attribute`

## Transactions

* `:db/id`:
* `:db/add`: Primitive assertion. All transactions reduce to a collection of primitive assertions and retractions of facts
  * `[:db/add fred :user/age 42]`
* `:db/retract`: Primitive retraction. See comment about `:db/add`
  * Eg. `[[:db/add 42 :likes "pizza"]]`
  * Eg. `[[:db/retract 42 :likes "pizza"]]`
* tx-data is either a list of forms or a map of forms
  * List form Eg
  ```
  [:db/add "-42" :order/id "X-001"]
  [:db/add "-42" :order/line-items "-43"]
  [:db/add "-43" :item/sku "SKU-7"]
  [:db/add "-43" :item/count 2]
  ```
  * Map form Eg and equivalent to the above list forms
  ```
  {:order/id "X-001"
   :order/line-items [{:item/sku "SKU-7" :item/count 2}] }
  ```

## API

* API Require
```
(require
 '[datomic.api :as d])
```
* `(create-database db-uri)`: creates database specified by URI
* `(delete-database db-uri)`: deletes database specified by URI
* `(connect db-uri)`: connects to the database specified by URI
* `(release conn)`: releases the connection async. Returns immediately.
* `(transact conn tx-data)`: submit a transaction to the DB for writing. Returns a future with the keys:
  * `:db-before`: DB value before the transaction
  * `:db-after`: DB value after the transaction
  * `:tx-data`: Collection of datoms produced by the transaction
  * `:tempids`: Argument to `resolve-tempids`
* `(q query & inputs)`: executes query against its input
* `(db conn)`: Retrieves a value of the DB for reading. Latest value of the DB
* `(entity db eid)`: Returns a map of the entity's attributes for the given eid
* `(touch entity)`: Touches all the attributes to an entity recursively
  * `(d/touch (d/entity db :db/ident))`: See all attributes of `:db/ident` entity
* Blow away and recreate DB
```
(d/delete-database db-url)
(d/create-database db-url)
(d/transact (d/connect db-url) schema)
(d/transact (d/connect db-url) test-data)
```
* `(basis-t db)`: Returns the t of the most recent transaction reachable via this db value
* `(t->tx t)`: Returns the transaction id associated with the t
* `(as-of db tx)`: Returns the value of the DB at some point t. t can be a time or transaction id
* `(history db)`: Returns a database value containing ALL assertions and retractions across time. Assertions/retractions can be distinguished with the `added?` in `[e a v tx added?]`
* Identify an Entity
  * `eid`
  * `ident` for an entity that has a `:db/ident`
  * lookup ref for entity that has a unique attribute. Eg `[:person/email "joe@example.com"]`
  * In transactions, a `tempid` can be used
```
;; the following thee queries are equivalent
(d/q '[:find [?artist-name ...]
       :in $ ?country
       :where [?artist :artist/name ?artist-name]
              [?artist :artist/country ?country]]
     db [:country/name "Belgium"])

(d/q '[:find [?artist-name ...]
       :in $ ?country
       :where [?artist :artist/name ?artist-name]
              [?artist :artist/country ?country]]
     db :country/BE)

(d/q '[:find [?artist-name ...]
       :in $ ?country
       :where [?artist :artist/name ?artist-name]
              [?artist :artist/country ?country]]
     db 17592186045516)
```

## Datalog


### Resource

* [Query Doc](https://docs.datomic.com/on-prem/query.html)

### Find Spec

```
[:find ?e
 :where [?e :user/email]]
```
* Find specification
  * `:find ?a ?b`: returns relation
  * `:find [?a ...]`: returns collection
  * `:find [?a ?b]`: returns list
  * `:find ?a .`: single scalar
* Return only one scalar with `.`
```
(d/q '[:find ?year .
       :in $ ?name
       :where [?artist :artist/name ?name]
              [?artist :artist/startYear ?year]]
     db "John Lennon")
```
* The collection find spec is useful when you are only interested in a single variable.
```
;; query
[:find [?release-name ...]
 :in $ ?artist-name
 :where [?artist :artist/name ?artist-name]
        [?release :release/artists ?artist]
        [?release :release/name ?release-name]]

;; inputs
db "John Lennon"

;; result
["Power to the People"
 "Unfinished Music No. 2: Life With the Lions"
 "Live Peace in Toronto 1969"
 "Live Jam"
 ...]
```
* The single tuple find spec is used when you are interested in multiple variables but only a single return result
```
;; query
[:find [?year ?month ?day]
 :in $ ?name
 :where [?artist :artist/name ?name]
        [?artist :artist/startDay ?day]
        [?artist :artist/startMonth ?month]
        [?artist :artist/startYear ?year]]

;; inputs
db "John Lennon"

;; result
[1940 10 9]
```

### not/not-join/or/and clauses

* not clauses allow you to express that one or more logic variables inside a query must not satisfy all of a set of predicates
```
;; query
[:find (count ?eid) .
 :where [?eid :artist/name]
        (not [?eid :artist/country :country/CA])]

;; inputs
db

;; result
4538
```
* not-join clauses
```
;; query
[:find (count ?artist) .
       :where [?artist :artist/name]
       (not-join [?artist]
         [?release :release/artists ?artist]
         [?release :release/year 1970])]
;; inputs
db

;; result
3263
;; In this next query, which returns the number of artists who didn't release an album in 1970,
;; ?release is used only inside the not clause and doesn't need to unify with the outer clause.
;; not-join is used to specify that only ?artist needs unifying.
```
* or clauses allow you to express that one or more logic variables inside a query satisfy at least one of a set of predicates
```
;; query
[:find (count ?medium) .
       :where (or [?medium :medium/format :medium.format/vinyl7]
                  [?medium :medium/format :medium.format/vinyl10]
                  [?medium :medium/format :medium.format/vinyl12]
                  [?medium :medium/format :medium.format/vinyl])]

;; inputs
db

;; result
9219
```
* and clauses can only be used in not or or clauses. By default, and clauses are used when stacking up clauses

### Expression Clauses

* Expression clauses allow arbitrary Java or Clojure functions to be used inside of Datalog queries. Any functions or methods you use in expression clauses must be pure
```
[(predicate ...)]
[(function ...) bindings]
```
* Predicate expression
```
;; query
[:find ?name ?year
 :where [?artist :artist/name ?name]
        [?artist :artist/startYear ?year]
        [(< ?year 1600)]]

;; inputs
db

;; result
#{["Choir of King's College, Cambridge" 1441]
  ["Heinrich Schütz" 1585]}
```
* Function expression: their return values are used to bind other variables
```
[:find ?track-name ?minutes
 :in $ ?artist-name
 :where [?artist :artist/name ?artist-name]
        [?track :track/artists ?artist]
        [?track :track/duration ?millis]
        [(quot ?millis 60000) ?minutes]
        [?track :track/name ?track-name]]
```
* Expression clauses do not nest!!!
```
;; this query will not work!!!
[:find ?celsius .
 :in ?fahrenheit
 :where [(/ (- ?fahrenheit 32) 1.8) ?celsius]]


;; This query will work
[:find ?celsius .
 :in ?fahrenheit
 :where [(- ?fahrenheit 32) ?f-32]
        [(/ ?f-32 1.8) ?celsius]

;; inputs
212

;; result
100.0
```

#### Built-in Expression Functions and Predicates

* `!=`, `<`, `>`, `<=`, `>=`
* `*`, `+`, `-`, `/`
* All functions from `clojure.core` namespace
* A set of functions that are aware of Datomic data structures: `get-else`, `get-some`, `ground`, `fulltext`, `missing?`, `tx-ids`, `tx-data`
* `get-else`: it can return a default attribute for a missing attribute on an entity
```
[(get-else src-var ent attr default) ?val-or-default]
```
```
;; query
[:find ?artist-name ?year
 :in $ [?artist-name ...]
 :where [?artist :artist/name ?artist-name]
        [(get-else $ ?artist :artist/startYear "N/A") ?year]]

;; inputs
db, ["Crosby, Stills & Nash" "Crosby & Nash"]

;; result
#{["Crosby, Stills & Nash" 1968]
  ["Crosby & Nash" "N/A"]}
```
* `get-some`
```
[(get-some src-var ent attr+) [?attr ?val]]
```
```
;; The query below tries to find a :country/name for an entity, and then falls back to :artist/name:
[:find [?e ?attr ?name]
 :in $ ?e
 :where [(get-some $ ?e :country/name :artist/name) [?attr ?name]]]

;; inputs
db, :country/US

;; result
[:country/US 84 "United States"]
```
* `fulltext`: returns a 4 tuple: entity value tx and score
```
[(fulltext src-var attr search) [[?ent ?val ?tx ?score]]]
```
```
;; query
[:find ?entity ?name ?tx ?score
 :in $ ?search
 :where [(fulltext $ :artist/name ?search) [[?entity ?name ?tx ?score]]]]

;; inputs
db, "Jane"

;; result
#{[17592186047274 "Jane Birkin" 2839 0.625]
  [17592186046687 "Jane" 2267 1.0]
  [17592186047500 "Mary Jane Hooper" 3073 0.5]}

```
* `missing`: returns true if the attribute is missing for the entity
```
;; The following query finds all artists whose start year is not recorded in the database.
[:find ?name
 :where [?artist :artist/name ?name]
        [(missing? $ ?artist :artist/startYear)]]

;; inputs
db

;; result
#{["Sigmund Snopek III"] ["De Labanda's"] ["Baby Whale"] ...}

```

### Calling Clojure/Java Functions


* Static Java Methods
```
;; query
[:find ?k ?v
 :where [(System/getProperties) [[?k ?v]]]]

;; no inputs

;; result
#{["java.vendor.url.bug" "http://bugreport.sun.com/bugreport/"]
  ["sun.cpu.isalist" ""]
  ["sun.jnu.encoding" "UTF-8"]
  ...}

```
* Instance Methods
```
[(.endsWith ?k "path")]
```
```
;; query
[:find ?k ?v
 :where [(System/getProperties) [[?k ?v]]]
        [(.endsWith ?k "version")]]

;; no inputs

;; result
#{["java.class.version" "52.0"]
  ["java.runtime.version" "1.8.0_20-b26"]
  ["java.version" "1.8.0_20"]
  ...}

```
* Clojure functions can be used as functions and predicates
```
;; query
'[:find [?prefix ...]
  :in [?word ...]
  :where [(subs ?word 0 5) ?prefix]]

;; inputs
["hello" "antidisestablishmentarianism"]

;; result
["hello" "antid"]
```

### Aggregate Functions

* Returning a single Value
```
(min ?xs)
(max ?xs)
(count ?xs)
(count-distinct ?xs)
(sum ?xs)
(avg ?xs)
(median ?xs)
(variance ?xs)
(stddev ?xs)
```
* Returning Collections
```
;; Where n is specified, fewer than n items may be returned if not enough items are available.
(distinct ?xs)
(min n ?xs)
(max n ?xs)
(rand n ?xs)
(sample n ?xs)
```
* One can also make custom aggregate functions

### Pull Syntax

* Instead of using `(d/entity eid)` to convert an eid to its attribute map, one can use the pull syntax directly in a Datalog Query
* `(pull eid pull-pattern)`
```
;; Without pull syntax
(->> db
     (d/q '[:find ?e
            :where
            [?e :user/email]])
     (map (fn [xs] (d/touch (d/entity db (first xs))))))

;; With pull syntax
(d/q '[:find [(pull ?e [:user/email]) ...]
       :where
       [?e :user/email]] db)
```
* Pull Pattern
```
;; Only the first one. No `...` at the end of pull pattern
(d/q '[:find [(pull ?e [:user/email])]
       :where
       [?e :user/email]]
       db)

;; Returns multiple fields and all results
(d/q '[:find [(pull ?e [:user/email :user/age]) ...]
       :where
       [?e :user/email]]
       db)

;; Parent Child Pull Syntax
(d/q '[:find [(pull ?e [:user/email {:user/upVotes [:story/url]}]) ...]
       :where
       [?e :user/email]]
       db)
```
* It reminds me of GraphQL syntax to pull data from the Database! Even more powerful!

## Ops

* Backup URI
  * Directory: file:/full/path/backup/dir
  * S3: s3://bucket/prefix
    * One needs to set appropriate IAM role for the peers doing the backups
* Restore a DB
```
# Example
bin/datomic restore-db file:mbrainz-1968-1973 datomic:free://localhost:4334/mbrainz-1968-1973

# General pattern
bin/datomic restore-db from-backup-uri to-db-uri
```
* List Bakups
```
bin/datomic list-backups backup-uri
```
* Backup a DB
```
# Example
bin/datomic backup-db datomic:free://172.17.0.2:4334/mbrainz-1968-1973 backup-uri

# General pattern
bin/datomic backup-db from-db-uri to-backup-uri
```

## Resources

* [Day of Datomic](https://github.com/Datomic/day-of-datomic)
* [Learn Datalog Today](http://www.learndatalogtoday.org/)
* [Datomic Tutorial](https://github.com/ftravers/datomic-tutorial)
* [Query Syntax Documentation](https://docs.datomic.com/on-prem/query.html)
* [Pull Syntax Documentation](https://docs.datomic.com/cloud/query/query-pull.html)
* [Datomic Documentation](https://docs.datomic.com/cloud/index.html)
* [Datomic Transaction Data Reference](https://docs.datomic.com/cloud/transactions/transaction-data-reference.html#lookup-ref)
* [Datomic Data Model](https://docs.datomic.com/cloud/whatis/data-model.html)
* [Datomic Web App a practical guide](https://vvvvalvalval.github.io/posts/2016-07-24-datomic-web-app-a-practical-guide.html)
* [Datomic Backup Doc](https://docs.datomic.com/on-prem/backup.html)
