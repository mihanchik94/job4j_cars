create table files (
   id serial primary key,
   name text,
   path text,
   post_id int references auto_post(id)
)