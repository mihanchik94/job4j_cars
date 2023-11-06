create table auto_user (
	id serial primary key,
	login varchar not null unique,
	password varchar not null,
	phone_number varchar not null,
	full_name text not null
);