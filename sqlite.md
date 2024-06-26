# SQLITE

Connect to a database:

```sh
sqlite3 file.sqlite
```

List all tables:

```sqlite
.tables
```

Schema of a given table:

```sqlite
.schema albums
```

Use the PRAGMA command to display column informations:

```sqlite
.header on
.column on
pragma table_info('albums');
```

```txt
cid  name      type           notnull  dflt_value  pk
---  --------  -------------  -------  ----------  --
0    AlbumId   INTEGER        1                    1
1    Title     NVARCHAR(160)  1                    0
2    ArtistId  INTEGER        1                    0
``
