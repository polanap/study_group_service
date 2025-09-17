CREATE TABLE coordinates
(
    id BIGSERIAL PRIMARY KEY,
    x  BIGINT NOT NULL,
    y  INT    NOT NULL CHECK (y <= 708)
);

CREATE TABLE location
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    x    DOUBLE PRECISION,
    y    INT
);

CREATE TABLE person
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    eye_color   VARCHAR(50)  NOT NULL,
    hair_color  VARCHAR(50)  NOT NULL,
    location_id BIGINT,
    height      DOUBLE PRECISION,
    nationality VARCHAR(50),
    FOREIGN KEY (location_id) REFERENCES location (id) ON DELETE CASCADE
);

CREATE TABLE study_group
(
    id                   BIGSERIAL PRIMARY KEY,
    name                 VARCHAR(255) NOT NULL,
    coordinates_id       BIGINT       NOT NULL,
    creation_date        DATE         NOT NULL,
    students_count       INT,
    expelled_students    INT          NOT NULL,
    transferred_students INT,
    form_of_education    VARCHAR(50),
    should_be_expelled   BIGINT,
    average_mark         FLOAT        NOT NULL,
    semester             VARCHAR(50),
    group_admin_id       BIGINT,
    FOREIGN KEY (coordinates_id) REFERENCES coordinates (id) ON DELETE CASCADE,
    FOREIGN KEY (group_admin_id) REFERENCES person (id) ON DELETE CASCADE
);

