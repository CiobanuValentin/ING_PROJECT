-- Inserting the admin user
INSERT INTO users (user_id ,firstname, lastname, email, phone, active, validated, created_date, modified_date, created_by, modified_by, password)
VALUES (1, 'admin', '', 'admin@ing.com', '', true, true, NOW(), NOW(), NULL, NULL, 'f7db06f2c5db810d1965af2eb22ef3a0b069f8c1af41728093047a47f7a53958:3999747e0b3676143612cccf211c8c33:2');

-- Inserting the administrator role
INSERT INTO role (role_key, name)
VALUES ('admin_role_key', 'administrator');

