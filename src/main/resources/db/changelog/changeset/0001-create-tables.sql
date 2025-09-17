CREATE TABLE t_role
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    role VARCHAR(100)       NOT NULL
);

CREATE TABLE t_user
(
    id                BIGSERIAL PRIMARY KEY      NOT NULL,
    username          VARCHAR(100)            NOT NULL,
    password          VARCHAR(100)            NOT NULL,
    email             VARCHAR(100),
    registration_date TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE t_user_roles
(
    id      BIGSERIAL PRIMARY KEY NOT NULL,
    role_id BIGINT,
    user_id BIGINT
);
