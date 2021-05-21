create SCHEMA IF NOT EXISTS ${schema};

create table IF NOT EXISTS ${schema}.user_(
  username VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255),
  surname VARCHAR(255),
  email VARCHAR(255) not null,
  password VARCHAR(255) not null,
  registration_time TIMESTAMPTZ
);

create sequence IF NOT EXISTS ${schema}.repository_seq;
create table IF NOT EXISTS ${schema}.repository(
  id bigint default nextval('${schema}.repository_seq') PRIMARY KEY,
  url VARCHAR(255),
  username VARCHAR(255),
  password VARCHAR(255),
  branch VARCHAR(255),
  last_pull_timestamp TIMESTAMPTZ,
  last_commit_sha VARCHAR(255),
  owner VARCHAR(255) references ${schema}.user_
);
