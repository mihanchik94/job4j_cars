create table files (
   id serial primary key,
   name text not null,
   path text not null unique,
   post_id int references auto_post(id)
);