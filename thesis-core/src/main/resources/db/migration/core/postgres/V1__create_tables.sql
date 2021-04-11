create SCHEMA IF NOT EXISTS ${schema};

create table IF NOT EXISTS ${schema}.user_(
  username VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255),
  surname VARCHAR(255),
  email VARCHAR(255) not null,
  password VARCHAR(255) not null,
  enabled INTEGER DEFAULT 0,
  role VARCHAR(255) not null,
  refresh_token VARCHAR(1024),
  registration_time TIMESTAMPTZ,
  error_count INTEGER DEFAULT 0,
  last_error TIMESTAMPTZ,
  expiration_time_password TIMESTAMPTZ,
  expiration_time_blocked TIMESTAMPTZ,
  change_password_required INTEGER DEFAULT 0
);

CREATE INDEX user_refresh_token
ON ${schema}.user_(refresh_token);

create sequence IF NOT EXISTS ${schema}.password_seq;

create table IF NOT EXISTS ${schema}.password_(
  id bigint default nextval('${schema}.password_seq') PRIMARY KEY,
  username VARCHAR(255) not null,
  encoded_password VARCHAR(1024) not null,
  registration_time TIMESTAMPTZ
);
