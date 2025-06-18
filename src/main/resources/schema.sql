DROP TABLE reservation IF EXISTS;
DROP TABLE reservation_time IF EXISTS;
DROP TABLE exercise_course IF EXISTS;
DROP TABLE member IF EXISTS;

CREATE TABLE reservation_time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE exercise_course
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE member
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(5) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(10) NOT NULL,
    date       VARCHAR(255) NOT NULL,
    member_id  BIGINT,
    time_id    BIGINT,
    exercise_course_id   BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id),
    FOREIGN KEY (exercise_course_id) REFERENCES exercise_course (id),
    FOREIGN KEY (member_id) REFERENCES member (id)
);
