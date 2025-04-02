insert into profiles(email, phone_number, residence) values ('email', '0623890342', 'Groeningen');
insert into users(username, password, profile_id) values ('pipje', 'password', 1);
insert into roles(role) values ('ROLE_USER'), ('ROLE_ADMIN');
insert into categories(parent_id, title) values (null, 'Auto');
insert into advertisements(category_id, user_id, title, description, price, image, details, state, date, has_to_go) values (1, 1, 'titel', null, 50.0, null, null, null, null,'checked');
insert into advertisements(category_id, user_id, title, description, price, image, details, state, date, has_to_go) values (1, 1, 'andere titel', null, 100.0, null, null, null, null,'checked');