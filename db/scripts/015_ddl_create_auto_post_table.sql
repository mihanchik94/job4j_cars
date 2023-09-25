create table auto_post (
	id serial primary key,
	description text not null,
	price int not null,
	created timestamp not null,
	auto_user_id int references auto_user(id),
	car_id int references cars(id)
);