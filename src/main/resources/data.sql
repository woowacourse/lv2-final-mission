INSERT INTO member (name, email, password)
VALUES ('하루', 'haru@woowa.com', 'aaa');
INSERT INTO member (name, email, password)
VALUES ('두루', 'duru@woowa.com', 'aaa');

INSERT INTO site (name)
VALUES ('하늘');
INSERT INTO site (name)
VALUES ('바다');

INSERT INTO reservation (check_in_date, check_out_date, member_id, site_id)
VALUES ('2025-08-15', '2025-08-17', 1, 1);
INSERT INTO reservation (check_in_date, check_out_date, member_id, site_id)
VALUES ('2025-08-22', '2025-08-23', 1, 1);

INSERT INTO reservation (check_in_date, check_out_date, member_id, site_id)
VALUES ('2025-08-15', '2025-08-17', 2, 2);