#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$DB_USER" --dbname "$DB_DB" <<-EOSQL
    CREATE USER docker;
    CREATE DATABASE thesis_core;
    GRANT ALL PRIVILEGES ON DATABASE thesis_core TO docker;
EOSQL




