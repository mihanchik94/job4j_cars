CREATE TABLE PRICE_HISTORY(
   id SERIAL PRIMARY KEY,
   before int not null,
   after int not null,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   auto_post_id int references auto_post(id)
);