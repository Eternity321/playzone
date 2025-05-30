INSERT INTO role (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN'),
       ('ROLE_BANNED');


INSERT INTO users (nickname, user_avatar_key, username, email, password)
VALUES ('Super Admin', NULL, 'admin', 'admin@admin.com',
        '$2a$10$RDxKQuubUSlkNzpaUERyHOCeJvt4BU2rLoSZx.ezxNVVdM/sQqQR6');

INSERT INTO users_roles (users_id, role_id)
VALUES ((SELECT users_id FROM users WHERE username = 'admin'),
        (SELECT role_id FROM role WHERE name = 'ROLE_USER')),
       ((SELECT users_id FROM users WHERE username = 'admin'),
        (SELECT role_id FROM role WHERE name = 'ROLE_ADMIN'));


INSERT INTO sport_type (name)
VALUES ('Футбол'),
       ('Баскетбол'),
       ('Волейбол'),
       ('Теннис'),
       ('Настольный теннис'),
       ('Плавание'),
       ('Мотокросс'),
       ('Гольф'),
       ('Бадминтон'),
       ('Хоккей');
