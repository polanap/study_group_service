CREATE TABLE admin_request
(
    id                BIGSERIAL PRIMARY KEY      NOT NULL,
    user_id           BIGINT                     NOT NULL,
    creation_time     TIMESTAMP DEFAULT NOW() NOT NULL,
    processed_user_id BIGINT,
    procession_time   TIMESTAMP,
    status            VARCHAR(50)
);
