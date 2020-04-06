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

## Redirect http to https

```
http {
    ...
    # Redirect http requests to https
    server {
        listen         80;
        server_name    server_name.com;
        return         301 https://$server_name$request_uri;
    }

    server {
       listen 443 ssl;
       server_name server_name.com;

       ssl_certificate /etc/letsencrypt/live/server_name.com/fullchain.pem;
       ssl_certificate_key /etc/letsencrypt/live/server_name.com/privkey.pem;
    ...
    }
...
}
```

## Proxy websocket connection

```
  location /sockjs-node {
    proxy_pass http://client;
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "Upgrade";
  }
```

## Rewrite Rule

* Rewrite /api/link to /link when passing it to http://api;
```
# Tell Nginx there is an 'upstream' server at server:3000
upstream api {
  server api:5000;
}

server {
  listen 80;

  location /api {
    rewrite /api/(.*) /$1 break;
    proxy_pass http://api;
  }
}
```

## Commands

* Test config files loaded
```
nginx -t
```
* Show nginx version and command that was used to launch it
```
nginx -V
```

## Misc

* [Beware of the trailing slash in `proxy_pass` url](https://stackoverflow.com/questions/22759345/nginx-trailing-slash-in-proxy-pass-url)

## Resources

* [Config file guide](https://www.digitalocean.com/community/tutorials/understanding-the-nginx-configuration-file-structure-and-configuration-contexts)
* [Nginx configuration official documentation](http://nginx.org/en/docs/dirindex.html)
* [Beginner's Guide](https://nginx.org/en/docs/beginners_guide.html#conf_structure)
