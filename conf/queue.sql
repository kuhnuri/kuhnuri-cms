CREATE USER "play"
  NOSUPERUSER
  INHERIT
  CREATEROLE;

CREATE DATABASE queue WITH OWNER = "play" ENCODING 'UTF8';

GRANT ALL PRIVILEGES ON DATABASE queue TO "play";

\connect queue play

CREATE TYPE STATUS AS ENUM ('queue', 'process', 'done', 'error');

CREATE TABLE queue
(
  uuid       VARCHAR(256)                     NOT NULL,
  transtype  VARCHAR(256)                     NOT NULL,
  created    TIMESTAMP WITH TIME ZONE         NOT NULL,
  input      VARCHAR(256)                     NOT NULL,
  output     VARCHAR(256)                     NOT NULL,
  status     STATUS DEFAULT 'queue' :: STATUS NOT NULL,
  id         SERIAL                           NOT NULL,
  processing TIMESTAMP WITH TIME ZONE,
  finished   TIMESTAMP WITH TIME ZONE
);

CREATE UNIQUE INDEX table_name_id_uindex
  ON queue (uuid);

CREATE UNIQUE INDEX queue_id_uindex
  ON queue (id);
