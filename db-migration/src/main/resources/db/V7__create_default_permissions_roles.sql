-- Add permissions for editing, viewing, creating, and deleting products
INSERT INTO permission (permission_key, description, action, subject)
VALUES
    ('edit_product', 'Edit Product', 'edit', 'product'),
    ('view_product', 'View Product', 'view', 'product'),
    ('create_product', 'Create Product', 'create', 'product'),
    ('delete_product', 'Delete Product', 'delete', 'product');


-- Inserting the administrator role
INSERT INTO role (role_key, name)
VALUES ('visitor_role_key', 'visitor');
