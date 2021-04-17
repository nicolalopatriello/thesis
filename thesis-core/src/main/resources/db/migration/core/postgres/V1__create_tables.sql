create SCHEMA IF NOT EXISTS ${schema};

create table IF NOT EXISTS ${schema}.user_(
  username VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255),
  surname VARCHAR(255),
  email VARCHAR(255) not null,
  password VARCHAR(255) not null,
  registration_time TIMESTAMPTZ
);

create sequence IF NOT EXISTS ${schema}.test_vector_seq;
create table IF NOT EXISTS ${schema}.test_vector(
  id bigint default nextval('${schema}.test_vector_seq') PRIMARY KEY,
  url VARCHAR(255),
  filename VARCHAR(255),
  hash VARCHAR(255),
  path VARCHAR(255),
  registration_time TIMESTAMPTZ,
  last_update TIMESTAMPTZ
);

create sequence IF NOT EXISTS ${schema}.gitrace_seq;
create table IF NOT EXISTS ${schema}.gitrace(
  id bigint default nextval('${schema}.gitrace_seq') PRIMARY KEY,
  git_repo_url VARCHAR(255),
  git_description VARCHAR(255),
  git_provider VARCHAR(255),
  token VARCHAR(255),
  last_repo_update TIMESTAMPTZ,
  registration_time TIMESTAMPTZ
);


create table IF NOT EXISTS ${schema}.user_test(
  url varchar(255) not null PRIMARY KEY,
  description VARCHAR(255),
  username VARCHAR(255) references ${schema}.user_
);

create table IF NOT EXISTS ${schema}.user_test_dep_test_vector(
  url VARCHAR(255) not null PRIMARY KEY references ${schema}.user_test,
  test_vector_id bigint references ${schema}.test_vector
);

create table IF NOT EXISTS ${schema}.user_test_dep_gitrace(
  url VARCHAR(255) not null PRIMARY KEY references ${schema}.user_test,
  gitrace_id bigint references ${schema}.gitrace
);


create sequence IF NOT EXISTS ${schema}.notifications_seq;
create table IF NOT EXISTS ${schema}.notifications(
  id bigint default nextval('${schema}.notifications_seq') PRIMARY KEY,
  git_repo_url VARCHAR(255) references ${schema}.user_test,
  checked INTEGER DEFAULT 0,
  created_at TIMESTAMPTZ,
  checked_at TIMESTAMPTZ
);


create sequence IF NOT EXISTS ${schema}.scheduler_history_seq;
create table IF NOT EXISTS ${schema}.scheduler_history(
  id bigint default nextval('${schema}.scheduler_history_seq') PRIMARY KEY,
  type VARCHAR(255),
  timestamp TIMESTAMPTZ
);
