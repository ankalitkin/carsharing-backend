CREATE TABLE employee
(
    id       SERIAL PRIMARY KEY,
    name     varchar(255) NOT NULL,
    roles    varchar(255) NOT NULL,
    login    varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    salt     varchar(255) NOT NULL,
    deleted  boolean      NOT NULL
);

CREATE TABLE customer
(
    id                SERIAL PRIMARY KEY,
    name              varchar(255),
    phone_number      varchar(255) NOT NULL,
    selfie_filename   varchar(255),
    profile_confirmed bool         NOT NULL DEFAULT false,
    banned_until      date
);

CREATE TABLE car
(
    id         SERIAL PRIMARY KEY,
    model      varchar(255) NOT NULL,
    vin        varchar(255) NOT NULL,
    latitude   double precision,
    longitude  double precision,
    is_used    bool         NOT NULL DEFAULT false,
    is_visible bool         NOT NULL DEFAULT true,
    comment    varchar(255)
);

CREATE TABLE document_upload
(
    id          SERIAL PRIMARY KEY,
    customer_id int          NOT NULL REFERENCES customer,
    filename    varchar(255) NOT NULL
);

CREATE TABLE document
(
    id                SERIAL PRIMARY KEY,
    customer_id       int REFERENCES customer,
    first_name        varchar(255) NOT NULL,
    last_name         varchar(255) NOT NULL,
    birthdate         date         NOT NULL,
    issued_on         date         NOT NULL,
    valid_by          date         NOT NULL,
    driving_exp_since date         NOT NULL,
    photo_filename    varchar(255)
);

CREATE TABLE car_photos
(
    id             SERIAL PRIMARY KEY,
    car_id         int NOT NULL REFERENCES car,
    photo_filename varchar(255)
);

CREATE TABLE log
(
    id          SERIAL PRIMARY KEY,
    type        varchar(255) NOT NULL,
    customer_id int          NOT NULL REFERENCES customer,
    car_id      int          NOT NULL REFERENCES car,
    value       varchar(255)
);
