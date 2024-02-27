-- liquibase formatted sql

-- changeset YuriPetukhov:1

CREATE TABLE clients (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(80)
);

CREATE TABLE contacts (
  id BIGSERIAL PRIMARY KEY,
  type VARCHAR(20),
  value VARCHAR(80),
  client_id BIGSERIAL NOT NULL,
  FOREIGN KEY (client_id) REFERENCES clients(id)
);