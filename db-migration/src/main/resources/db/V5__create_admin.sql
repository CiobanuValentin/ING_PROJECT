-- Inserting the admin user
INSERT INTO users (firstname, lastname, email, phone, active, created_date, modified_date, created_by, modified_by, password)
VALUES ('admin', '', 'admin@ign.com', '', true, NOW(), NOW(), NULL, NULL, 'f7db06f2c5db810d1965af2eb22ef3a0b069f8c1af41728093047a47f7a53958:3999747e0b3676143612cccf211c8c33:2');

-- Inserting the administrator role
INSERT INTO role (role_key, name)
VALUES ('admin_role_key', 'administrator');

-- Establishing the relationship between admin user and administrator role
INSERT INTO user_role (user_id, role_key)
VALUES ((SELECT user_id FROM users WHERE email = 'admin@ign.com'), 'admin_role_key');
