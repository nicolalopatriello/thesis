create SCHEMA IF NOT EXISTS ${schema};
ALTER TABLE ${schema}.user_ ADD COLUMN built_in INTEGER DEFAULT 0;