INSERT INTO users(user_id, name) VALUES ('user1', '유저1');
INSERT INTO users(user_id, name) VALUES ('user2', '유저2');

INSERT INTO plan_Dates(date) VALUES('2025-07-01');
INSERT INTO plan_Dates(date) VALUES('2025-07-02');
INSERT INTO plan_Dates(date) VALUES('2025-07-03');

INSERT INTO plan_date_time_slots(plan_date_id, start_at, end_at) VALUES (1, '10:00', '11:00');
INSERT INTO plan_date_time_slots(plan_date_id, start_at, end_at) VALUES (1, '11:00', '12:00');
INSERT INTO plan_date_time_slots(plan_date_id, start_at, end_at) VALUES (1, '12:00', '13:00');

INSERT INTO plan_date_time_slots(plan_date_id, start_at, end_at) VALUES (2, '14:00', '15:00');
INSERT INTO plan_date_time_slots(plan_date_id, start_at, end_at) VALUES (2, '15:00', '16:00');

INSERT INTO plan_date_time_slots(plan_date_id, start_at, end_at) VALUES (3, '18:00', '19:00');
