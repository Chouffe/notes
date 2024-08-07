# GPG

## Commands

* Listing public keys - metadata only

```sh
gpg --list-public-keys
gpg -k
```

`pub`: your public key info
`sub`: your public subkey info

* Listing private keys - metadata only

```sh
gpg --list-secret-keys
gpg -K
```

`sec`: the master/primary secret key. There is key size, keyid, creation date, expiration date and fingerprint information displayed.
`ssb`: secret subkeys. These can be your sub signing key, encryption key or authentication key. You can have multiple subkeys.
`uid`: this is the user information associated with the secret key. You can have multiple uids.
`sec#`: # after sec means that your secret key is missing from the machine. But it has a reference to the secret key.
`ssb>`: > after ssb means that your subkeys are not the machine. Instead they are on a smartcard.

* Encrypt a file in binary

```sh
# Creates a message.txt.gpg file that is encrypted
gpg --encrypt --recipient 'YOUREMAILGPGKEY' message.txt
```

* Encrypt a file in ascii armor

```sh
# Creates a message.txt.asc that is encrypted
gpg --encrypt --armor --recipient 'foo@bar.quux' message.txt
```

* Decrypt a binary/ascii armor file

```sh
gpg --decrypt message.txt.gpg
gpg --decrypt message.txt.asc
```

* Signing a file

```sh
# WARNING: THIS DOES NOT ENCRYPT THE FILE
gpg --sign <file>
```

* Verify a file

```sh
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

```sh
$ gpg2 --import A85EA103-private-subkey.gpg
$ shred --remove A85EA103-private-subkey.gpg
```

* Create a new master key with GPG

```sh
gpg --full-generate-key
```

* Generate a revokation certificate in case it gets lost or compromised

```sh
gpg --gen-revoke $KEYID > $KEYID-revoke.txt
```

* Save a copy of the private key

```sh
gpg --armor --export-secret-keys $KEYID > $KEYID-master.key
```

* Edit key to add subkeys

```sh
gpg --expert --edit-key KEYID
```

* Delete a secret-key: WARNING!! Make sure you have a backup

```sh
gpg --delete-secret-keys KEYID
```

* Encrypt a message to your own key (useful for storing passwords and credentials)

```sh
echo "test message string" | gpg --encrypt --armor --recipient $KEYID -o encrypted.txt
```

* Decrypt message

```sh
gpg --decrypt --armor encrypted.txt
```

* Sign a message

```sh
echo "test message string" | gpg --armor --clearsign > signed.txt
```

* Verify the signature

```sh
gpg --verify signed.txt
```

### Renew sub-keys

1. Setup the environment with the loaded master key in an airgapped system: https://github.com/drduh/YubiKey-Guide#setup-environment
2. Edit the expiry date and export the public key: https://github.com/drduh/YubiKey-Guide#renewing-sub-keys

```sh
gpg --edit-card $KEYID
key 2  # Do this for the keys that need to be extended and repeat (2, 3, 4) Sign, Encrypt, Authenticate
expire
3y
save
```

```sh
gpg --armor --export $KEYID > "$KEYID-public.asc"
gpg --armor --export-secret-keys $KEYID > "$KEYID-private-subkeys.key"
```

3. Load the public keys into the devices that are using it (phone, laptop, etc)

```sh
gpg --import "$KEYID-public.asc"
```

## Yubikey

I routinely swap between two YubiKeys, the Nano in my docking station and the
Neo on my keychain.
I have the same encryption and authentication keys on both YubiKeys and
distinct signing keys on each.
In order to swap between which YubiKey I want to use, I do the following:

```sh
killall gpg-agent
rm -r ~/.gnupg/private-keys-v1.d/
# Plug in the new YubiKey to load the keys
gpg --card-status
# gpg --card-edit
```

Note: Make sure the card is visible, also notifies gpg which keys are available for
current card
Now the alternate card should be usable. If it's not, unplug the YubiKey and
repeat steps 1-4 again, it should work the second time.
I've found the command `gpg-connect-agent updatestartuptty /bye` can also be helpful.

This process should help you when you are trying to create the YubiKeys as well.
The gpg-agent keeps track of the yubikey card id, to tell `gpg-agent` to
relearn the serial number on the smartcard

```sh
gpg-connect-agent "scd serialno" "learn --force" /bye
```

### Yubikey with OpenKeyChain and Password Store

1. Export the public key as a file

```sh
gpg --armor --export arthur@caillau.me > mykey.asc
```

1. Or: share with QR code scanning option from the OpenKeyChain Android App
2. Transfer the file to your Android phone
3. Import the key via UI - the key is now loaded into the app, it cannot be
   used yet as we need to let OpenKeyChain know that it is on the yubikey
4. import Key from yubikey - this will make the imported key available for
   other apps like Password Store

### Change Admin PIN

One can change PIN and Admin PIN using this command:

```sh
gpg --change-pin
```

### Require Touch

* Signing

```sh
ykman openpgp set-touch sig on
```

* Encryption

```sh
ykman openpgp set-touch enc on
```

* Authentication

```sh
ykman openpgp set-touch aut on
```

* Turn it off: `on -> off`

```sh
ykman openpgp set-touch sig off
ykman openpgp set-touch enc off
ykman openpgp set-touch aut off
```

### SSH setup

* To use a GPG key instead of an SSH one, one can follow the instructions [there](https://github.com/drduh/YubiKey-Guide#create-configuration)
* If the agent complains that it can't `sign_and_send_pubkey: signing
failed...`, run this and try again:

```sh
gpg-connect-agent updatestartuptty /bye
```

* [Github now supports SSH security keys](https://www.yubico.com/blog/github-now-supports-ssh-security-keys/)

* Generating an SSH key using a resident key - it will require you to touch the yubikey to confirm

```sh
ssh-keygen -t ecdsa-sk -O resident
```

The resident key can be loaded directly form the security key.

* To use the SSH key on a new computer until it is rebooted:

```sh
ssh-add -K
```

* To permanently import the key permanently

```sh
ssh-keygen -K
```

And then move the two generated files `id_ecdsa_sk_rk` and `id_ecdsa_sk_rk.pub`
to your ssh directory

* Display the ssh public key

```sh
ssh-add -L
``

### Github setup

* Go to the config file in the `.git` folder of the project and change the https:// to:

```sh
[remote "origin"]
  url = git@github.com:<repo-path>
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
* [Yubikey Guide](https://github.com/drduh/YubiKey-Guide)
* https://devhints.io/gnupg
