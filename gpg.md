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
gpg --full-generate-key
```
* Generate a revokation certificate in case it gets lost or compromised
```
gpg --gen-revoke $KEYID > $KEYID-revoke.txt
```
* Save a copy of the private key
```
gpg --armor --export-secret-keys $KEYID > $KEYID-master.key
```
* Edit key to add subkeys
```
gpg --expert --edit-key KEYID
```
* Delete a secret-key: WARNING!! Make sure you have a backup
```
gpg --delete-secret-keys KEYID
```
* Encrypt a message to your own key (useful for storing passwords and credentials)
```
echo "test message string" | gpg --encrypt --armor --recipient $KEYID -o encrypted.txt
```
* Decrypt message
```
gpg --decrypt --armor encrypted.txt
```
* Sign a message
```
echo "test message string" | gpg --armor --clearsign > signed.txt
```
* Verify the signature
```
gpg --verify signed.txt
```

## Yubikey

I routinely swap between two YubiKeys, the Nano in my docking station and the Neo on my keychain.

I have the same encryption and authentication keys on both YubiKeys and distinct signing keys on each.

In order to swap between which YubiKey I want to use, I do the following:

```
killall gpg-agent
rm -r ~/.gnupg/private-keys-v1.d/
# Plug in the new YubiKey
gpg --card-edit
```

(Make sure the card is visible, also notifies gpg which keys are available for current card)

Now the alternate card should be usable. If it's not, unplug the YubiKey and repeat steps 1-4 again, it should work the second time.

I've found the command `gpg-connect-agent updatestartuptty /bye` can also be helpful.

This process should help you when you are trying to create the YubiKeys as well.

## Yubikey

### Require Touch

* Signing
```
ykman openpgp set-touch sig on
```
* Encryption
```
ykman openpgp set-touch enc on
```
* Authentication
```
ykman openpgp set-touch aut on
```
* Turn it off: `on -> off`
```
ykman openpgp set-touch sig off
ykman openpgp set-touch enc off
ykman openpgp set-touch aut off
```

### Setup

* https://www.preining.info/blog/2016/04/gnupg-subkeys-yubikey/
* https://www.preining.info/blog/2016/05/yubikey-neo/
* https://www.preining.info/blog/2018/03/replacing-a-lost-yubikey/
* https://ocramius.github.io/blog/yubikey-for-ssh-gpg-git-and-local-login/
* https://support.yubico.com/support/solutions/articles/15000006419
* https://www.linode.com/docs/security/authentication/gpg-key-for-ssh-authentication/
* https://mlohr.com/gpg-agent-ssh-gnome/
* https://github.com/drduh/YubiKey-Guide

## Resources

* https://riseup.net/en/security/message-security/openpgp/best-practices
* https://www.openkeychain.org/faq/
* https://medium.com/@acparas/gpg-quickstart-guide-d01f005ca99
* https://oguya.ch/posts/2016-04-01-gpg-subkeys/
* https://help.github.com/en/articles/associating-an-email-with-your-gpg-key
* https://help.github.com/en/articles/generating-a-new-gpg-key
* https://developers.yubico.com/PGP/Card_edit.html
