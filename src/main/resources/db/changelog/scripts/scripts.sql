-- liquibase formatted sql

-- changeset YuriPetukhov:1

CREATE TABLE clients (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(80) NOT NULL CHECK (name !~ '^\s*$')
);

CREATE TABLE contacts (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(20),
    type_value VARCHAR(80),
    client_id BIGSERIAL NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    CONSTRAINT idx_unique_contact_per_type_value UNIQUE (client_id, type_value)
);
