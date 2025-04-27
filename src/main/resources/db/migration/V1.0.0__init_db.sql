CREATE TABLE users
(
    users_id        BIGSERIAL   NOT NULL PRIMARY KEY,
    nickname        VARCHAR(50) NOT NULL,
    user_avatar_key VARCHAR(255),
    username        VARCHAR(30) NOT NULL UNIQUE,
    email           VARCHAR(50) NOT NULL UNIQUE,
    password        VARCHAR(80) NOT NULL
);

CREATE TABLE role
(
    role_id BIGSERIAL   NOT NULL PRIMARY KEY,
    name    VARCHAR(50) NOT NULL
);

CREATE TABLE users_roles
(
    users_role_id BIGSERIAL NOT NULL PRIMARY KEY,
    users_id      BIGINT    NOT NULL REFERENCES users (users_id) ON DELETE CASCADE,
    role_id       BIGINT    NOT NULL REFERENCES role (role_id) ON DELETE CASCADE
);

CREATE TABLE sport_type
(
    sport_type_id BIGSERIAL   NOT NULL PRIMARY KEY,
    name          VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE sport_facility
(
    sport_facility_id BIGSERIAL        NOT NULL PRIMARY KEY,
    name              VARCHAR(255)     NOT NULL,
    address           VARCHAR(255)     NOT NULL,
    latitude          DOUBLE PRECISION NOT NULL,
    longitude         DOUBLE PRECISION NOT NULL,
    description       TEXT,
    sport_type_id     BIGINT           NOT NULL REFERENCES sport_type (sport_type_id),
    created_by        BIGINT           NOT NULL REFERENCES users (users_id)
);

CREATE TABLE event
(
    event_id      BIGSERIAL    NOT NULL PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    start_time    TIME         NOT NULL,
    end_time      TIME         NOT NULL,
    event_date    DATE         NOT NULL,
    status        VARCHAR(20)  NOT NULL CHECK (status IN ('PLANNED', 'ACTIVE', 'COMPLETED')),
    sport_type_id BIGINT       NOT NULL REFERENCES sport_type (sport_type_id),
    creator_id    BIGINT       NOT NULL REFERENCES users (users_id),
    facility_id   BIGINT       NOT NULL REFERENCES sport_facility (sport_facility_id)
);

CREATE TABLE event_participant
(
    event_participant_id BIGSERIAL NOT NULL PRIMARY KEY,
    event_id             BIGINT    NOT NULL REFERENCES event (event_id) ON DELETE CASCADE,
    user_id              BIGINT    NOT NULL REFERENCES users (users_id) ON DELETE CASCADE,
    UNIQUE (event_id, user_id)
);

CREATE TABLE photo
(
    photo_id          BIGSERIAL    NOT NULL PRIMARY KEY,
    file_key          VARCHAR(255) NOT NULL UNIQUE,
    created_at        TIMESTAMP DEFAULT now(),
    sport_facility_id BIGINT       NOT NULL REFERENCES sport_facility (sport_facility_id) ON DELETE CASCADE
);

CREATE TABLE facility_comment
(
    facility_comment_id BIGSERIAL NOT NULL PRIMARY KEY,
    rating              INTEGER   NOT NULL CHECK (rating >= 1 AND rating <= 5),
    created_at          TIMESTAMP DEFAULT now(),
    text                TEXT,
    user_id             BIGINT    NOT NULL REFERENCES users (users_id) ON DELETE CASCADE,
    facility_id         BIGINT    NOT NULL REFERENCES sport_facility (sport_facility_id) ON DELETE CASCADE,
    UNIQUE (user_id, facility_id)
);

CREATE TABLE report
(
    report_id   BIGSERIAL   NOT NULL PRIMARY KEY,
    reporter_id BIGINT      NOT NULL REFERENCES users (users_id) ON DELETE CASCADE,
    target_type VARCHAR(20) NOT NULL CHECK (target_type IN ('USER', 'FACILITY', 'COMMENT', 'EVENT')),
    target_id   BIGINT      NOT NULL,
    reason      TEXT        NOT NULL
);

