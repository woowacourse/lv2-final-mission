SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE reservation;
ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1;
TRUNCATE TABLE member;
ALTER TABLE member ALTER COLUMN id RESTART WITH 1;
TRUNCATE TABLE yoga_session;
ALTER TABLE yoga_session ALTER COLUMN id RESTART WITH 1;
TRUNCATE TABLE yoga_course;
ALTER TABLE yoga_course ALTER COLUMN id RESTART WITH 1;
SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO yoga_course (name, instructor)
VALUES ('아쉬탕가베이직', '아이나'),
       ('빈야사', '아이나');

INSERT INTO yoga_session (course_id, date, time, maximum_attendance)
VALUES (1, '2025-07-01', '19:00', 8),
       (2, '2025-07-01', '21:00', 8);

INSERT INTO member (name, email, password)
VALUES ('브라운', 'brown@email.com', 'brown'),
       ('솔라', 'solar@email.com', 'solar'),
       ('검프', 'gump@email.com', 'gump'),
       ('브리', 'brie@email.com', 'brie'),
       ('네오', 'neo@email.com', 'neo'),
       ('저스틴', 'justin@email.com', 'justin'),
       ('구구', 'gugu@email.com', 'gugu'),
       ('워니', 'wonie@email.com', 'wonie'),
       ('리사', 'lisa@email.com', 'lisa');

INSERT INTO reservation (member_id, session_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 2);
