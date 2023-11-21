INSERT INTO experis_db.roles (id, name) VALUES(1, 'ADMIN');
INSERT INTO experis_db.roles (id, name) VALUES(2, 'USER');

INSERT INTO experis_db.users (email, first_name, last_name, registered_at, password) VALUES('admin@email.com', 'Admin', 'Test', '2023-11-20 16:00', '{noop}admin');
INSERT INTO experis_db.users (email, first_name, last_name, registered_at, password) VALUES('user@email.com', 'User', 'Test', '2023-11-20 16:20','{noop}user');

INSERT INTO experis_db.user_role (user_id, role_id) VALUES(1, 1);
INSERT INTO experis_db.user_role (user_id, role_id) VALUES(1, 2);
INSERT INTO experis_db.user_role (user_id, role_id) VALUES(2, 2);