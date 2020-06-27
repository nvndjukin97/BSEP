INSERT INTO `authorities` (id, name) VALUES (1, 'ROLE_ADMIN');


INSERT INTO `user` (user_type, id, username, first_name, last_name, organization, organization_unit, country, email, password, enabled, last_password_reset_date, is_ca) VALUES (0, 1,  'admin1','Nevena', 'Djukin', 'FTN', 'Elektro', 'Srbija', 'nvn@gmail.com', '$2y$12$0o/gYj8ArmMlTxUAsyAIMespRLS.CNw5YLgs3QkUIYssaS7ol/nf6', true, '2017-10-01 21:58:58', true);

INSERT INTO `user` (user_type, id, username, first_name, last_name, organization, organization_unit, country, email, password, enabled, last_password_reset_date, is_ca) VALUES (0, 2, 'admin2', 'Milica', 'Radovanovic',  'FTN', 'Elektro', 'Spanija', 'mic@gmail.com', '$2y$12$Bks5gYZfy1zS8zgUUNPL8uaJ.kRIJt1hpK1TDaxd2Mc2MggzEwqOm', true, '2017-10-01 21:58:58', false);

INSERT INTO `user` (user_type, id, username, first_name, last_name, organization, organization_unit, country, email, password, enabled, last_password_reset_date, is_ca) VALUES (0, 3, 'admin3', 'Katarina', 'Prodanovic', 'FTN', 'Elektro', 'Mongolija', 'kac@gmail.com', '$2y$12$G49uAyswmO/ts2OBOOZqyeRHJS3h41yTJ3ruVN5a8XDkKrcZrHxuW', true, '2017-10-01 21:58:58', false);

INSERT INTO `user_authority` (user_id, authority_id) VALUES (1, 1);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (2, 1);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (3, 1);

