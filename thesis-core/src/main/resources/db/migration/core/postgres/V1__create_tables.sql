create SCHEMA IF NOT EXISTS ${schema};

create table IF NOT EXISTS ${schema}.user_(
  username VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255),
  surname VARCHAR(255),
  email VARCHAR(255) not null,
  password VARCHAR(255) not null,
  registration_time TIMESTAMPTZ
);

create sequence IF NOT EXISTS ${schema}.runner_seq;
create table IF NOT EXISTS ${schema}.runner(
  id bigint default nextval('${schema}.runner_seq') PRIMARY KEY,
  secret VARCHAR(255),
  registered_at TIMESTAMPTZ,
  UNIQUE(secret)
);

create sequence IF NOT EXISTS ${schema}.repository_seq;
create table IF NOT EXISTS ${schema}.repository(
  id bigint default nextval('${schema}.repository_seq') PRIMARY KEY,
  url VARCHAR(255) not null,
  username VARCHAR(255) not null,
  password VARCHAR(255) not null,
  branch VARCHAR(255) not null,
  last_commit_sha VARCHAR(255),
  runner_id bigint references ${schema}.runner,
  runner_started_at TIMESTAMPTZ,
  runner_finished_at TIMESTAMPTZ,
  recipe TEXT,
  minutes_watchers_interval bigint not null,
  owner VARCHAR(255) references ${schema}.user_
);

create sequence IF NOT EXISTS ${schema}.dependency_seq;
create table IF NOT EXISTS ${schema}.dependency(
  id bigint default nextval('${schema}.dependency_seq') PRIMARY KEY,
  name VARCHAR(255) not null,
  version VARCHAR(255),
  programming_language VARCHAR(128),
  repository_id bigint references ${schema}.repository,
  UNIQUE(name, version, programming_language, repository_id)
);


create sequence IF NOT EXISTS ${schema}.vulnerability_seq;
create table IF NOT EXISTS ${schema}.vulnerability(
  id bigint default nextval('${schema}.vulnerability_seq') PRIMARY KEY,
  cve_id VARCHAR(20) not null,
  cve_published_at TIMESTAMPTZ,
  cve_modified_at TIMESTAMPTZ,
  cvss bigint,
  cvss_vector VARCHAR(128),
  cve_patch VARCHAR(255),
  dependency_id bigint references ${schema}.dependency
);
