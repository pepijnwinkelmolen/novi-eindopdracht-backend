insert into profiles(email, phone_number, residence) values ('email', '0623890342', 'Groeningen');
insert into users(username, password, profile_id) values ('pipje', '$2a$12$GcbQlWzYl6xf1DbXTipSzOe2qrDKLj.TRxgDIDQoizHJ8bJoBLjlW', 1);
insert into roles(role) values ('ROLE_USER'), ('ROLE_ADMIN');
insert into users_roles(user_id, role) values ('1', 'ROLE_USER');
insert into users_roles(user_id, role) values ('1', 'ROLE_ADMIN');
insert into categories(parent_id, title) values (null, 'Auto');
insert into advertisements(category_id, user_id, title, description, price, image, details, state, date, has_to_go) values (1, 1, 'titel', null, 50.0, null, null, null, null,null);
insert into advertisements(category_id, user_id, title, description, price, image, details, state, date, has_to_go) values (1, 1, 'andere titel', null, 100.0, null, null, null, null,'checked');