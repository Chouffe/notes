# Pedestal

## Repl Development

* [Developing at the REPL](http://pedestal.io/guides/developing-at-the-repl)
* Start a server in dev-mode - Re-require/reload the `service.clj` file will make all the necessary changes.
```
(dev serv (run-dev))

; If you ever need to stop the server
(server/stop serv)
```

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
