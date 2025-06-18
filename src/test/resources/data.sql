INSERT INTO member (email, password, name, role)
VALUES ('admin@email.com', 'password', '관리자', 'ADMIN'),
       ('member1@email.com', '1234', '회원1', 'USER'),
       ('member2@email.com', '1234', '회원2', 'USER');

INSERT INTO reservation_time (start_at)
VALUES ('09:00'),
       ('10:00'),
       ('11:00'),
       ('12:00'),
       ('13:00'),
       ('14:00'),
       ('15:00'),
       ('16:00');

INSERT INTO meeting_room (room_name, maximum_number)
VALUES ('회의실1', 5),
       ('회의실2', 4),
       ('회의실3', 6),
       ('회의실4', 7);

INSERT INTO reservation (date, time_id, member_id, room_id)
VALUES ('2026-12-06', 1, 2, 3),
       ('2026-12-07', 2, 2, 2),
       ('2026-12-08', 3, 2, 3),
       ('2026-12-09', 4, 2, 4),
       ('2026-12-15', 4, 3, 4);
