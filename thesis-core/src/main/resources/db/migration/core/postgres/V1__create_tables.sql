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
  git_provider VARCHAR(255) not null,
  token VARCHAR(255),
  last_repo_update TIMESTAMPTZ,
  registration_time TIMESTAMPTZ
);

create sequence IF NOT EXISTS ${schema}.user_test_seq;
create table IF NOT EXISTS ${schema}.user_test(
  id bigint default nextval('${schema}.user_test_seq') PRIMARY KEY,
  git_provider VARCHAR(255) not null,
  git_repo_url varchar(255) not null,
  description VARCHAR(255),
  username VARCHAR(255) references ${schema}.user_,
  created_at TIMESTAMPTZ,
  unique(git_repo_url, username)
);

create sequence IF NOT EXISTS ${schema}.user_test_dep_test_vector_seq;
create table IF NOT EXISTS ${schema}.user_test_dep_test_vector(
  id bigint default nextval('${schema}.user_test_dep_test_vector_seq') PRIMARY KEY,
  user_test_id bigint not null references ${schema}.user_test,
  url VARCHAR(255),
  test_vector_id bigint references ${schema}.test_vector,
  unique(user_test_id, test_vector_id)
);

create sequence IF NOT EXISTS ${schema}.user_test_dep_gitrace_seq;
create table IF NOT EXISTS ${schema}.user_test_dep_gitrace(
  id bigint default nextval('${schema}.user_test_dep_gitrace_seq') PRIMARY KEY,
  user_test_id bigint not null references ${schema}.user_test,
  url VARCHAR(255),
  gitrace_id bigint references ${schema}.gitrace,
  unique(user_test_id, gitrace_id)
);


create sequence IF NOT EXISTS ${schema}.notification_seq;
create table IF NOT EXISTS ${schema}.notification(
  id bigint default nextval('${schema}.notification_seq') PRIMARY KEY,
  user_test_id bigint references ${schema}.user_test,
  user_test_url VARCHAR(255),
  changed_dep_type VARCHAR(255),
  changed_dep_id bigint,
  uuid VARCHAR(255),
  checked INTEGER DEFAULT 0,
  created_at TIMESTAMPTZ,
  checked_at TIMESTAMPTZ,
  username VARCHAR(255) references ${schema}.user_
);


create sequence IF NOT EXISTS ${schema}.scheduler_history_seq;
create table IF NOT EXISTS ${schema}.scheduler_history(
  id bigint default nextval('${schema}.scheduler_history_seq') PRIMARY KEY,
  type VARCHAR(255),
  timestamp TIMESTAMPTZ
);
