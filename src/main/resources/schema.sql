CREATE TABLE IF NOT EXISTS room (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    capacity BIGINT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS member (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS reservation (
    id BIGINT NOT NULL AUTO_INCREMENT,
    date DATE NOT NULL,
    time TIME NOT NULL,
    description VARCHAR(255) DEFAULT '',
    room_id BIGINT,
    member_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(room_id) REFERENCES room(id),
    FOREIGN KEY(member_id) REFERENCES member(id),
    UNIQUE (date, time, room_id)
);
