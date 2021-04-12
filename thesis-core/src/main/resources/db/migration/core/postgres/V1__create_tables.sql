create SCHEMA IF NOT EXISTS ${schema};

create table IF NOT EXISTS ${schema}.user_(
  username VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255),
  surname VARCHAR(255),
  email VARCHAR(255) not null,
  password VARCHAR(255) not null,
  registration_time TIMESTAMPTZ
);

create sequence IF NOT EXISTS ${schema}.gitrace_seq;
create table IF NOT EXISTS ${schema}.gitrace(
  id bigint default nextval('${schema}.gitrace_seq') PRIMARY KEY,
  git_descr VARCHAR(255),
  git_provider VARCHAR(255),
  git_url VARCHAR(255),
  last_release_created_at TIMESTAMPTZ,
  last_releaseid VARCHAR(255),
  last_release_name VARCHAR(255),
  last_release_tag_name VARCHAR(255),
  time_from_last_release bigint,
  timestamp TIMESTAMPTZ,
  token VARCHAR(255)
);

create sequence IF NOT EXISTS ${schema}.notifications_seq;
create table IF NOT EXISTS ${schema}.notifications(
  id bigint default nextval('${schema}.notifications_seq') PRIMARY KEY,
  checked INTEGER DEFAULT 0,
  elementid bigint,
  git_link VARCHAR(255),
  new_release_tag_name VARCHAR(255),
  old_release_tag_name VARCHAR(255),
  timestamp TIMESTAMPTZ
);

create sequence IF NOT EXISTS ${schema}.scheduler_history_seq;
create table IF NOT EXISTS ${schema}.scheduler_history(
  id bigint default nextval('${schema}.scheduler_history_seq') PRIMARY KEY,
  timestamp TIMESTAMPTZ,
  type VARCHAR(255)
);

create table IF NOT EXISTS ${schema}.subscribers_tests(
  url varchar(255) not null PRIMARY KEY,
  description VARCHAR(255),
  username VARCHAR(255) references ${schema}.user_
);

create table IF NOT EXISTS ${schema}.subscribers_tests_crypto_tv(
  url VARCHAR(255) not null PRIMARY KEY,
  id_crypto_tv VARCHAR(255)
);

create table IF NOT EXISTS ${schema}.subscribers_tests_repo_git(
  url VARCHAR(255) not null PRIMARY KEY,
  id_repo VARCHAR(255)
);

create sequence IF NOT EXISTS ${schema}.test_vector_seq;
create table IF NOT EXISTS ${schema}.test_vector(
  id bigint default nextval('${schema}.test_vector_seq') PRIMARY KEY,
  filename VARCHAR(255),
  hash VARCHAR(255),
  last_changed TIMESTAMPTZ,
  last_modified_filets TIMESTAMPTZ,
  path VARCHAR(255),
  timestamp TIMESTAMPTZ,
  url VARCHAR(255)
);

