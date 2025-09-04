USE level3_final;

CREATE TABLE coach
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    auth_id        VARCHAR(255) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    name           VARCHAR(255) NOT NULL UNIQUE,
    education_part VARCHAR(50)  NOT NULL,
    start_time     TIME,
    end_time       TIME,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE crew
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    periods        INT          NOT NULL,
    education_part VARCHAR(50)  NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE meeting
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_time  DATETIME    NOT NULL,
    content    TEXT,
    status     VARCHAR(50) NOT NULL,
    coach_id   BIGINT      NOT NULL,
    crew_id    BIGINT      NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 외래키 제약조건
    CONSTRAINT fk_meeting_coach
        FOREIGN KEY (coach_id) REFERENCES coach (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_meeting_crew
        FOREIGN KEY (crew_id) REFERENCES crew (id)
            ON DELETE CASCADE
)
