insert into MEMBER(nickname, email, password, phone_number)
values ('테스트1', 'test1@mail.com', 'password', '01012341234'),
       ('테스트2', 'test2@mail.com', 'password', '01012346759');

insert into ROOM(name)
values ('어드레스룸'),
       ('백스윙룸');

insert into RESERVATION(member_id, room_id, date, start_time, end_time)
values (1, 1, '2025-06-15', '19:00:00', '20:00:00'),
       (1, 2, '2025-06-16', '20:00:00', '21:00:00'),
       (2, 2, '2025-06-17', '20:00:00', '21:00:00')
