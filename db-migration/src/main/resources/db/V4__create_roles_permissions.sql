
CREATE TABLE IF NOT EXISTS permission (
    permission_key VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY,
    description VARCHAR(300),
    action VARCHAR(100) NOT NULL,
    subject VARCHAR(100) NOT NULL,
    unique(action, subject)
    );

CREATE TABLE IF NOT EXISTS role (
    role_key VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    unique(name)
    );

CREATE TABLE IF NOT EXISTS role_permission (
    role_key TEXT NOT NULL,
    permission_key TEXT NOT NULL,
    unique(role_key, permission_key),
    FOREIGN KEY (role_key) REFERENCES role(role_key),
    FOREIGN KEY (permission_key) REFERENCES permission(permission_key)
    );

CREATE TABLE IF NOT EXISTS user_permission (
    user_id INT NOT NULL,
    permission_key TEXT NOT NULL,
    unique(user_id, permission_key),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (permission_key) REFERENCES permission(permission_key)
    );

CREATE TABLE IF NOT EXISTS user_role (
    user_id INT NOT NULL,
    role_key TEXT NOT NULL,
    unique(user_id, role_key),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_key) REFERENCES role(role_key)
    );
