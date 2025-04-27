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
       ('Мотокросс');

INSERT INTO sport_facility (name, address, latitude, longitude, description, sport_type_id, created_by)
VALUES ('Стадион "Юность"', 'ул. Спортивная, 12', 55.7558, 37.6176, 'Современный стадион с натуральным газоном',
        (SELECT sport_type_id FROM sport_type WHERE name = 'Футбол'),
        (SELECT users_id FROM users WHERE username = 'admin')),

       ('Баскет-холл', 'ул. Олимпийская, 5', 55.7512, 37.6189, 'Закрытая площадка для баскетбола с трибунами',
        (SELECT sport_type_id FROM sport_type WHERE name = 'Баскетбол'),
        (SELECT users_id FROM users WHERE username = 'admin')),

       ('Теннис-клуб "ACE"', 'пр. Спортивный, 7', 55.7520, 37.6160, 'Открытые и крытые теннисные корты',
        (SELECT sport_type_id FROM sport_type WHERE name = 'Теннис'),
        (SELECT users_id FROM users WHERE username = 'admin')),

       ('Трасса для мотокросса', 'ул. Здоровья, 3', 55.7599, 37.6201, 'Поле с трассой для мотокросса',
        (SELECT sport_type_id FROM sport_type WHERE name = 'Мотокросс'),
        (SELECT users_id FROM users WHERE username = 'admin'));