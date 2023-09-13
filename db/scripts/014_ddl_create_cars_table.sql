create table cars (
	id serial primary key,
	brand varchar not null,
	model varchar not null,
	car_year int not null,
	mileage bigint not null,
	vin varchar,
	engine_id int references engines(id),
	body_id int references bodies(id),
	gear_box_id int references gear_boxes(id),
	fuel_type_id int references fuel_types(id),
	drive_type_id int references drive_types(id),
	color_id int references colors(id)
);