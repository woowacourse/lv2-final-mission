INSERT INTO member(email, password, name, role)
VALUES ('member1@email.com', 'qwer1234!', '회원1', 'GENERAL');

INSERT INTO seat(name) VALUES ('pos1');
INSERT INTO seat(name) VALUES ('pos2');
INSERT INTO seat(name) VALUES ('pos3');

INSERT INTO reservation(member_id, seat_id, reason, date)
VALUES (1, 1, '사유1', '2025-10-01');
INSERT INTO reservation(member_id, seat_id, reason, date)
VALUES (1, 1, '사유2', '2025-10-02');
INSERT INTO reservation(member_id, seat_id, reason, date)
VALUES (1, 1, '사유3', '2025-10-03');

