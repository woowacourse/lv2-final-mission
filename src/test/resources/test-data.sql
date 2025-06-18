INSERT INTO member(name, email, password, role)
VALUES ('flint', 'flint@flint.com', 'flint', 'ADMIN'),
       ('ban', 'ban@ban.com', 'ban', 'MEMBER'),
       ('test', 'test@test.com', 'test', 'MEMBER');

INSERT INTO reservation_time(start_at)
VALUES ('10:00'),
       ('11:00');

INSERT INTO meeting_room(name, describe, available_people_count)
VALUES ('test1', 'test1', 10),
       ('test2', 'test2', 20);

INSERT INTO black_list(member_id, reason, banned_since)
VALUES (2, 'reason', '2025-01-01');

INSERT INTO reservation(member_id, date, time_id, meeting_room_id)
VALUES (1, '3000-01-01', 1, 1);