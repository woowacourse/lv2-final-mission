INSERT INTO RESTAURANT(address, name, menus) VALUES ('address', 'matzip', 'menu');

INSERT INTO MEMBER(name, email, password) VALUES ('eve', 'eve@example.com', 'password');
INSERT INTO MEMBER(name, email, password) VALUES ('pop', 'pop@example.com', 'password');

INSERT INTO RESERVATION(reservation_date_time, member_id, restaurant_id, personnel)
VALUES ('2025-06-11T10:00:00', 1L, 1L, 2);