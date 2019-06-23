# GPG

* Listing public keys - metadata only
```
gpg --list-public-keys
```
* Listing private keys - metadata only
```
gpg --list-secret-keys
```

* How to export a gpg secret key from one device to another
```bash
# Generate a random password to encrypt key
gpg --armor --gen-random 1 20

# Export the encrypted secret key
gpg --armor --export-secret-keys YOUREMAILADDRESS | gpg --armor --symmetric --output mykey.sec.asc

# Transfer it with any method you want and then remove the file
rm mykey.sec.asc
```

* Import a gpg key - Do not forget to shred it!
```
$ gpg2 --import A85EA103-private-subkey.gpg
$ shred --remove A85EA103-private-subkey.gpg
```

## Resources

* https://www.openkeychain.org/faq/
* https://medium.com/@acparas/gpg-quickstart-guide-d01f005ca99
* https://oguya.ch/posts/2016-04-01-gpg-subkeys/
