INSERT INTO `authorities` (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO `authorities` (id, name) VALUES (2, 'ROLE_KORISNIK');
INSERT INTO `authorities` (id, name) VALUES (3, 'ROLE_SELLER');
INSERT INTO `authorities` (id, name) VALUES (4, 'ROLE_CA');
INSERT INTO `authorities` (id, name) VALUES (5, 'ROLE_NOT_CA');

INSERT INTO `user` (user_type, id, username, first_name, last_name, organization, organization_unit, country, email, password, enabled, last_password_reset_date, is_ca) VALUES (0, 1,  'admin1','Nevena', 'Djukin', 'FTN', 'Elektro', 'Srbija', 'nvn@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, '2017-10-01 21:58:58', true);

INSERT INTO `user` (user_type, id, username, first_name, last_name, organization, organization_unit, country, email, password, enabled, last_password_reset_date, is_ca) VALUES (0, 2, 'admin2', 'Milica', 'Radovanovic',  'FTN', 'Elektro', 'Spanija', 'mic@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, '2017-10-01 21:58:58', false);

INSERT INTO `user` (user_type, id, username, first_name, last_name, organization, organization_unit, country, email, password, enabled, last_password_reset_date, is_ca) VALUES (0, 3, 'admin3', 'Katarina', 'Prodanovic', 'FTN', 'Elektro', 'Mongolija', 'kac@gmail.com', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', true, '2017-10-01 21:58:58', false);

INSERT INTO `user_authority` (user_id, authority_id) VALUES (1, 1);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (2, 1);
INSERT INTO `user_authority` (user_id, authority_id) VALUES (3, 1);

