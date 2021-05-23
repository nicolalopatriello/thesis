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
  url VARCHAR(255) not null,
  username VARCHAR(255) not null,
  password VARCHAR(255) not null,
  branch VARCHAR(255) not null,
  last_commit_sha VARCHAR(255),
  worker_id bigint,
  worker_started_at TIMESTAMPTZ,
  worker_finished_at TIMESTAMPTZ,
  recipe TEXT,
  minutes_watchers_interval bigint not null,
  owner VARCHAR(255) references ${schema}.user_
);

create sequence IF NOT EXISTS ${schema}.runner_seq;
create table IF NOT EXISTS ${schema}.runner(
  id bigint default nextval('${schema}.runner_seq') PRIMARY KEY,
  secret VARCHAR(255),
  registered_at TIMESTAMPTZ
);
