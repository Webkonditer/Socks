-- liquibase formatted sql

-- changeset alexander:1
CREATE TABLE socks
(
    id              BIGSERIAL primary key,
    color           varchar(255),
    cotton_part     INT,
    quantity        INT
);
