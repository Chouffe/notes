# GPG

* Listing public keys - metadata only
```
gpg --list-public-keys
```
* Listing private keys - metadata only
```
gpg --list-secret-keys
```
* Encrypt a file in binary
```
# Creates a message.txt.gpg file that is encrypted
gpg --encrypt --recipient 'YOUREMAILGPGKEY' message.txt
```
* Encrypt a file in ascii armor
```
# Creates a message.txt.asc that is encrypted
gpg --encrypt --armor --recipient 'foo@bar.quux' message.txt
```
* Decrypt a binary/ascii armor file
```
gpg --decrypt message.txt.gpg
gpg --decrypt message.txt.asc
```
* Signing a file
```
# WARNING: THIS DOES NOT ENCRYPT THE FILE
gpg --sign <file>
```
* Verify a file
```
gpg --verify file
```

* How to export a gpg secret key from one device to another
```bash
# Generate a random password to encrypt key (use it in the next step)
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
* Create a new master key with GPG
```
gpg --gen-key
```

## Yubikey setup

* https://www.preining.info/blog/2016/04/gnupg-subkeys-yubikey/
* https://www.preining.info/blog/2016/05/yubikey-neo/
* https://www.preining.info/blog/2018/03/replacing-a-lost-yubikey/
* https://ocramius.github.io/blog/yubikey-for-ssh-gpg-git-and-local-login/
* https://support.yubico.com/support/solutions/articles/15000006419

## Resources

* https://riseup.net/en/security/message-security/openpgp/best-practices
* https://www.openkeychain.org/faq/
* https://medium.com/@acparas/gpg-quickstart-guide-d01f005ca99
* https://oguya.ch/posts/2016-04-01-gpg-subkeys/
* https://help.github.com/en/articles/associating-an-email-with-your-gpg-key
* https://help.github.com/en/articles/generating-a-new-gpg-key
