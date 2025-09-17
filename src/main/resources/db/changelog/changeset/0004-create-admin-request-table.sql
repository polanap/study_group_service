CREATE TABLE admin_request
(
    id                SERIAL PRIMARY KEY      NOT NULL,
    user_id           INT                     NOT NULL,
    creation_time     TIMESTAMP DEFAULT NOW() NOT NULL,
    processed_user_id INT,
    procession_time   TIMESTAMP,
    status            VARCHAR(50)
);
