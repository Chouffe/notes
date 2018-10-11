# SSL

## SSL certificates with Certbot/Lets Encrypt and Nginx

* Request a Staging Certificate
```
sudo docker run -it --rm \
  -v /docker-volumes/etc/letsencrypt:/etc/letsencrypt \
  -v /docker-volumes/var/lib/letsencrypt:/var/lib/letsencrypt \
  -v /docker/letsencrypt-docker-nginx/src/letsencrypt/letsencrypt-site:/data/letsencrypt \
  -v "/docker-volumes/var/log/letsencrypt:/var/log/letsencrypt" \
  certbot/certbot \
  certonly --webroot \
  --register-unsafely-without-email --agree-tos \
  --webroot-path=/data/letsencrypt \
  --staging \
  -d haskellbazaar.com -d www.haskellbazaar.com
```
* Request a Production Certificate
```
sudo docker run -it --rm \
  -v /docker-volumes/etc/letsencrypt:/etc/letsencrypt \
  -v /docker-volumes/var/lib/letsencrypt:/var/lib/letsencrypt \
  -v /docker/letsencrypt-docker-nginx/src/letsencrypt/letsencrypt-site:/data/letsencrypt \
  -v "/docker-volumes/var/log/letsencrypt:/var/log/letsencrypt" \
  certbot/certbot \
  certonly --webroot \
  --email chouffe.caillau@gmail.com --agree-tos --no-eff-email \
  --webroot-path=/data/letsencrypt \
  -d haskellbazaar.com -d www.haskellbazaar.com
```
* Get additional information about your certificates
```
sudo docker run --rm -it --name certbot \
  -v /docker-volumes/etc/letsencrypt:/etc/letsencrypt \
  -v /docker-volumes/var/lib/letsencrypt:/var/lib/letsencrypt \
  -v /docker/letsencrypt-docker-nginx/src/letsencrypt/letsencrypt-site:/data/letsencrypt \
  certbot/certbot \
  --staging \
  certificates
```

* Add the following to your nginx config
```
server {
    ...
    listen 443 ssl;
    server_name your_domain.com;
    ssl_certificate /etc/nginx/ssl/nginx.crt;
    ssl_certificate_key /etc/nginx/ssl/nginx.key;
    ...
}
```
* Renew certificate
```
docker run --rm -it --name certbot \
  -v "/docker-volumes/etc/letsencrypt:/etc/letsencrypt" \
  -v "/docker-volumes/var/lib/letsencrypt:/var/lib/letsencrypt" \
  -v "/docker-volumes/data/letsencrypt:/data/letsencrypt" \
  -v "/docker-volumes/var/log/letsencrypt:/var/log/letsencrypt" \
  certbot/certbot renew \
  --webroot -w /data/letsencrypt \
  && docker kill \
  --signal=HUP haskell-bazaar-nginx
```
* Add a Cron job
```
sudo crontab -e
```
```
# Place the following at the end of the file
0 23 * * * docker run --rm -it --name certbot -v "/docker-volumes/etc/letsencrypt:/etc/letsencrypt" -v "/docker-volumes/var/lib/letsencrypt:/var/lib/letsencrypt" -v "/docker-volumes/data/letsencrypt:/data/letsencrypt" -v "/docker-volumes/var/log/letsencrypt:/var/log/letsencrypt" certbot/certbot renew --webroot -w /data/letsencrypt --quiet && docker kill --signal=HUP haskell-bazaar-nginx
```
* [Resource](https://www.humankode.com/ssl/how-to-set-up-free-ssl-certificates-from-lets-encrypt-using-docker-and-nginx)
