CREATE TABLE users
(
    users_id        SERIAL PRIMARY KEY NOT NULL,
    nickname        VARCHAR(50)        NOT NULL,
    user_avatar_key VARCHAR(255),
    username        VARCHAR(30)        NOT NULL UNIQUE,
    email           VARCHAR(50)        NOT NULL UNIQUE,
    password        VARCHAR(80)        NOT NULL
);

CREATE TABLE role
(
    role_id SERIAL primary key NOT NULL,
    name    VARCHAR(50)        NOT NULL
);

CREATE TABLE users_roles
(
    users_role_id SERIAL PRIMARY KEY                  NOT NULL,
    users_id      INTEGER REFERENCES users (users_id) NOT NULL,
    role_id       INTEGER REFERENCES role (role_id)   NOT NULL
);

CREATE TABLE sport_type
(
    sport_type_id SERIAL PRIMARY KEY NOT NULL,
    name          VARCHAR(50)        NOT NULL UNIQUE
);

CREATE TABLE sport_facility
(
    sport_facility_id SERIAL PRIMARY KEY                            NOT NULL,
    name              VARCHAR(255)                                  NOT NULL,
    address           VARCHAR(255)                                  NOT NULL,
    latitude          DECIMAL                                       NOT NULL,
    longitude         DECIMAL                                       NOT NULL,
    description       TEXT,
    sport_type_id     INTEGER REFERENCES sport_type (sport_type_id) NOT NULL,
    created_by        INTEGER REFERENCES users (users_id)           NOT NULL
);

CREATE TABLE event
(
    event_id      SERIAL PRIMARY KEY                                               NOT NULL,
    title         VARCHAR(255)                                                     NOT NULL,
    start_time    TIME                                                             NOT NULL,
    end_time      TIME                                                             NOT NULL,
    event_date    DATE                                                             NOT NULL,
    status        VARCHAR(20) CHECK (status IN ('PLANNED', 'ACTIVE', 'COMPLETED')) NOT NULL,
    sport_type_id INTEGER REFERENCES sport_type (sport_type_id)                    NOT NULL,
    creator_id    INTEGER REFERENCES users (users_id)                              NOT NULL,
    facility_id   INTEGER REFERENCES sport_facility (sport_facility_id)            NOT NULL
);
