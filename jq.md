# jq

## Commands

* input an HTML as a JSON value
```
echo '{"foo": "XXX"}' | jq --arg foo "$(cat index.html)" '.foo = $foo'

configuration="$(jq -n \
                 --arg pitch_stage "$PITCH_STAGE" \
                 --arg db_security_group "$DB_SECURITY_GROUP" \
                 '{Parameters: {
                    Stage: $pitch_stage,
                    DbSecurityGroup: $db_security_group,
                    }}' )"
```
