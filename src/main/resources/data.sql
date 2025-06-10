insert into reservation_time(time)
values ('09:00');
insert into reservation_time(time)
values ('10:00');
insert into reservation_time(time)
values ('11:00');



insert into reservation_capacity(reservation_time, number_of_people)
values (1, 3);
insert into reservation_capacity(reservation_time, number_of_people)
values (2, 3);
insert into reservation_capacity(reservation_time, number_of_people)
values (3, 3);

insert into restaurant(name, address, open_time, closing_time)
values ('솥밥', '송파구', '10:00', '22:00');

insert into restaurant(name, address, open_time, closing_time)
values ('스시준', '송파구', '11:00', '23:00');

insert into restaurant(name, address, open_time, closing_time)
values ('차이나스토리', '송파구', '10:00', '21:00');


insert into restaurant_reservation_capacities(reservation_capacities_id, restaurant_id)
values(1, 1);
insert into restaurant_reservation_capacities(reservation_capacities_id, restaurant_id)
values(2, 2);
insert into restaurant_reservation_capacities(reservation_capacities_id, restaurant_id)
values(3, 3);

