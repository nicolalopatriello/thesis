INSERT INTO ${schema}.user_ (username,name,surname,email,password,registration_time) VALUES
('nlopatriello', 'Nicola', 'Lopatriello', 'nic.lopatriello@gmail.com', '$2a$12$5HJMvSfdjbevzLrvA4a8jeLY3zoHjXL.gi34d43PGbVKKVQfEzpYe', NULL);

INSERT INTO ${schema}.user_ (username,name,surname,email,password,registration_time) VALUES
('admin', 'Admin', 'Admin', 'admin@thesis.test', '$2a$12$5HJMvSfdjbevzLrvA4a8jeLY3zoHjXL.gi34d43PGbVKKVQfEzpYe',NULL);

INSERT INTO ${schema}.worker (id, secret, registered_at) VALUES
(0, 'worker-1-secret',NULL);