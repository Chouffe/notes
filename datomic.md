# Datomic

## Vocabulary

* `Entity`: An entity is a set of datoms that are all about the same E
* `Point-In-Time Entity`: A point-in-time (as-of) view of an entity considers only datoms that whose Op is true as of a certain Tx

## Schema

* [Schema Reference](https://docs.datomic.com/cloud/schema/schema-reference.html)
* Example
```
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

## Datalog


* Datalog Query
```
[:find ?e
 :where [?e :user/email]]
```

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

## Resources

* [Day of Datomic](https://github.com/Datomic/day-of-datomic)
* [Learn Datalog Today](http://www.learndatalogtoday.org/)
* [Datomic Tutorial](https://github.com/ftravers/datomic-tutorial)
* [Pull Syntax Documentation](https://docs.datomic.com/cloud/query/query-pull.html)
* [Datomic Documentation](https://docs.datomic.com/cloud/index.html)
* [Datomic Transaction Data Reference](https://docs.datomic.com/cloud/transactions/transaction-data-reference.html#lookup-ref)
* [Datomic Data Model](https://docs.datomic.com/cloud/whatis/data-model.html)
* [Datomic Web App a practical guide](https://vvvvalvalval.github.io/posts/2016-07-24-datomic-web-app-a-practical-guide.html)
