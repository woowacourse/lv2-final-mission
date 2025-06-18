INSERT INTO exercise_course (name, description)
VALUES ('수영', '물날리');
INSERT INTO exercise_course (name, description)
VALUES ('요가', '테레사수녀');
INSERT INTO exercise_course (name, description)
VALUES ('풋살', '익살');
INSERT INTO exercise_course (name, description)
VALUES ('크로스핏', '흥미진진');

INSERT INTO reservation_time (start_at)
VALUES ('10:00');
INSERT INTO reservation_time (start_at)
VALUES ('11:00');
INSERT INTO reservation_time (start_at)
VALUES ('12:00');
INSERT INTO reservation_time (start_at)
VALUES ('13:00');

INSERT INTO member (name, email, password, role)
VALUES ('포라', 'forarium20@gmail.com', '1234', 'USER');
INSERT INTO member (name, email, password, role)
VALUES ('운학', 'woonak@naver.com', '1234', 'USER');
INSERT INTO member (name, email, password, role)
VALUES ('주연', 'juyeon@naver.com', '1234', 'USER');
INSERT INTO member (name, email, password, role)
VALUES ('ADMIN', 'admin@naver.com', '1234', 'ADMIN');

INSERT INTO reservation (name, date, member_id, time_id, exercise_course_id)
VALUES ('예약자1', '2026-12-25', 1, 1, 1);
INSERT INTO reservation (name, date, member_id, time_id, exercise_course_id)
VALUES ('예약자2', '2026-12-26', 1, 1, 2);
INSERT INTO reservation (name, date, member_id, time_id, exercise_course_id)
VALUES ('예약자3', '2026-12-27', 1, 1, 3);
INSERT INTO reservation (name, date, member_id, time_id, exercise_course_id)
VALUES ('예약자4', '2026-12-27', 2, 1, 1);
INSERT INTO reservation (name, date, member_id, time_id, exercise_course_id)
VALUES ('예약자5', '2026-12-01', 1, 1, 3);
INSERT INTO reservation (name, date, member_id, time_id, exercise_course_id)
VALUES ('예약자6', '2026-12-02', 1, 3, 3);
INSERT INTO reservation (name, date, member_id, time_id, exercise_course_id)
VALUES ('예약자7', '2026-12-03', 1, 2, 2);
