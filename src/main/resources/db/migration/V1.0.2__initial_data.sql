DO $$DECLARE
  system_id         UUID = '00000000-0000-0000-0000-000000000000';
BEGIN

  INSERT INTO oauth_clients (client_id, client_secret, client_resources) VALUES
    ('web', 'web', 'authresource,webresource'),
    ('mobile', 'mobile', 'authresource,mobileresource');

  --   -- Password: 12345
  INSERT INTO users (id, email, password, user_name, user_surname, user_status) VALUES
    (system_id,
     'admin@kramar.com',
     '$2a$10$oJD.FohR7Jhn.CHVh83KGOpsp.JNKuBjryHioPuR33F0U0yQ9Kqt2',
     'Admin user', 'Admin user', 'ACTIVE');

  INSERT INTO user2roles (user_id, role) VALUES (system_id, 'SUPER_ADMIN');

END$$;

