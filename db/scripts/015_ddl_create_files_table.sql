create table files (
   id serial primary key,
   name text not null,
   path text not null unique
);