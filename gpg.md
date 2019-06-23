# GPG

* How to export a gpg secret key from one device to another
```bash
# Generate a random password to encrypt key
gpg --armor --gen-random 1 20

# Export the encrypted secret key
gpg --armor --export-secret-keys YOUREMAILADDRESS | gpg --armor --symmetric --output mykey.sec.asc

# Transfer it with any method you want and then remove the file
rm mykey.sec.asc
```

## Resources

* https://www.openkeychain.org/faq/
