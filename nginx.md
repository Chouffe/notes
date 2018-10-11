# Nginx

* default folder for an index.html page
```
/usr/share/nginx/html
```

## Configuration

* Organized in contexts and nested contexts with `{` and `}`
* The `main` context is outside any `{`: broadest environment
  * user, groups, number of workers are configured in the main context
* `events` context, contained within the `main` context
  * Set global options, how worker processes handle connections
* `http` context
  * Hold the vast majority of the configuration
  * How the web server handles http and https connections
  * Child of the `main` context
* `server` context
  * Child of the `http` context
  * Each server context defines a specific virtual server to handle client requests. As many server contexts as needed.
  * Nginx must decide which context is most appropriate based on details of the request
  * `listen`: directive ip address / port combination that this server is designed to respond to
  * `server_name`: directive that can be used to match with the Host header from the HTTP request
  * Selection is done with IP address/port combination
  * `proxy_pass` sets the new url
```
# main context

events {
    # events context
    ...
}

http {

    server {
        # first server context
    }

    server {
        # second server context
    }
    ...
}
```
* `location` context
  * Match the location definition against the client request through a selection algorithm
  * Selection based on the request URI
```
location match_modifier location_match {
    ...
}
```
```
# main context

server {
    # server context

    location /match/criteria {
        # first location context
    }

    location /other/criteria {
        # second location context

        location nested_match {
            # first nested location
        }

        location other_nested {
            # second nested location
        }
    }
}
```

### Best practices

* Declare directives in the highest context to which they are applicable and override them as necessary in lower contexts
```
http {
    root /var/www/html;
    server {
        location / {
            ...
        }

        location /another {
            ...
        }
    }
}
```
* Use multiple sibling contexts instead of if logic for processing

## Misc

* [Beware of the trailing slash in `proxy_pass` url](https://stackoverflow.com/questions/22759345/nginx-trailing-slash-in-proxy-pass-url)

## Resources

* [Config file guide](https://www.digitalocean.com/community/tutorials/understanding-the-nginx-configuration-file-structure-and-configuration-contexts)
* [Nginx configuration official documentation](http://nginx.org/en/docs/dirindex.html)