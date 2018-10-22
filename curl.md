# Curl

* Inspect response headers
```
curl -I http://google.com
```
* Post `JSON` encoded data to `localhost:3000/api/login`
```
curl --header "Content-Type: application/json" --request POST --data '{"username":"xyz","password":"xyz"}' http://localhost:3000/api/login

# Shorter version
curl -H "Content-Type: application/json" -X POST -d '{"username":"xyz","password":"xyz"}' http://localhost:3000/api/login
```
* Post `EDN` encoded data to `localhost:3000/api/login`
```
curl -H "Content-Type: application/edn" -X POST -d '{:username "xyz" :password "xyz"}' http://localhost:3000/api/login
```
