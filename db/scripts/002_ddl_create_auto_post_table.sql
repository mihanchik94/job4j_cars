create table auto_post (
	id serial primary key,
	description text not null,
	created timestamp not null
);