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

create sequence IF NOT EXISTS ${schema}.worker_seq;
create table IF NOT EXISTS ${schema}.worker(
  id bigint default nextval('${schema}.worker_seq') PRIMARY KEY,
  secret VARCHAR(255),
  registered_at TIMESTAMPTZ
);

create sequence IF NOT EXISTS ${schema}.watcher_seq;
create table IF NOT EXISTS ${schema}.watcher(
  id bigint default nextval('${schema}.watcher_seq') PRIMARY KEY,
  name VARCHAR(255),
  description VARCHAR(255),
  type VARCHAR(255),
  enabled bool not null default true,
  repository_id bigint references ${schema}.repository,
  last_update TIMESTAMPTZ
);
