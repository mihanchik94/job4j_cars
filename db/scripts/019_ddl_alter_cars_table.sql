alter table cars drop column brand;
alter table cars drop column model;
alter table cars add column car_brand_id int references brands(id) not null;
alter table cars add column car_model_id int references models(id) not null;