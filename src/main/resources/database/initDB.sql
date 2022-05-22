CREATE TABLE IF NOT EXISTS url
(
    id              serial
        constraint url_pk
            primary key,
    url             VARCHAR   NOT NULL CHECK ( length(url) > 3),
    alias           VARCHAR  ,
    creation_date   TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    counter         INTEGER   NOT NULL
);