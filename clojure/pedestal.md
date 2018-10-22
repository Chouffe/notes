# Pedestal

## Interceptors

* [Parameters Reference](http://pedestal.io/reference/parameters)
* Body Params:
  * `json-params`
  * `edn-params`
  * `transit-params`
  * `form-params`
```
(require '[io.pedestal.http.body-params :as body-params])

(def routes
  (route/expand-routes
    #{["/greet-json" :post [(body-params/body-params) echo] :route-name :greet-json]}))
```

## Resources

* [Your First API](http://pedestal.io/guides/your-first-api)
* [Pedestal Documentation](http://pedestal.io/api/pedestal.service/io.pedestal.http.body-params.html#var-body-params)
* [Github Repository](https://github.com/pedestal/pedestal)
