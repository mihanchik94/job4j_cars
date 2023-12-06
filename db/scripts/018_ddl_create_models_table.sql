create table models (
  id serial primary key,
  name varchar not null unique,
  car_brand_id int references brands(id)
);