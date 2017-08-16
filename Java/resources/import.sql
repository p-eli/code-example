-- ALTER SCHEMA `airport` DEFAULT CHARACTER SET utf8_general_ci;
ALTER DATABASE `airport` CHARACTER SET utf8 COLLATE utf8_general_ci;
-- password=sha256(login + "|" + password)
INSERT INTO user (id,login,password,type,firstname,lastname) values(1,"a","1e91e4b7912308d290010127f2b8ad05262cff08ac94fe8e10783337e16c70d9",2,"Admin","Adolf");
INSERT INTO user (id,login,password,type,firstname,lastname) values(2,"t","a7b005fc192a13e0d9dbb5da34eb87fbb0470c7138f6a6856bebf6b6de078567",1,"Technik","Tonda");
INSERT INTO user (id,login,password,type,firstname,lastname) values(3,"o","ab3bd97a903ced8b40eeccf5709f9db1edc703a76311fe732cb8a0001f8dffb4",0,"Odbrony","Zamestnanec");

INSERT INTO user (id,login,password,type,firstname,lastname) values(4,"admin","e1e517804d3a2b2ef568eb2fb4a5847adc37579de2867da66c14b299dcc0f156",2,"Admin","Romel");
INSERT INTO user (id,login,password,type,firstname,lastname) values(5,"technik","8e020909c5e1552a9ed37df0b88975c7e5a385e20a0548bd5b0a09ad96d205c0",1,"Technik","Pomelo");
INSERT INTO user (id,login,password,type,firstname,lastname) values(6,"uzivatel","eee3ef426da2b1e2e007c25b5d3d9f1fd39b7db10b468190147aa9d2a452522c",0,"Uzivatel","Bruno");


INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (1,350, 18474, 'Boeing', '747');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (2,450, 18777, 'Boeing', '777');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (3,210, 21787, 'Boeing', '728');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (4,460, 18788, 'Boeing', '747');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (5,350, 20574, 'Boeing', '747');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (6,350, 18788, 'Boeing', '747');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (7,480, 18777, 'Boeing', '777');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (8,250, 21787, 'Boeing', '728');

INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (9,350, 18474, 'Airbus', 'A380');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (10,450, 22475, 'Airbus', 'A380');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (11, 210, 45645, 'Airbus', 'A380');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (12,460, 45645, 'Airbus', 'A420');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (13,350, 50450, 'Airbus', 'A420');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (14,330, 35433, 'Airbus', 'A500');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (15,360, 34544, 'Airbus', 'A500');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (16,840, 25447, 'Airbus', 'A510');

INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (17,150, 45645, 'Tupolev', 'TU-485');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (18,180, 50450, 'Tupolev', 'TU-485');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (19,200, 35433, 'Tupolev', 'TU-840');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (20,250, 34544, 'Tupolev', 'TU-840');
INSERT INTO airport.plane_model (id, capacity, enginePower, manufacturer, type) VALUES (21,260, 25447, 'Tupolev', 'TU-323');

INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (1, '2017-04-04', 432, 'BO-75d788s', 1);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (2, '2017-04-02', 844, 'BO-75d751s', 5);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (3, '2017-02-01', 584, 'BO-75de88s', 6);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (4, '2017-04-04', 10, 'BO-75qq8s', 4);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (5, '2016-04-18', 0, 'BO-75d99w', 2);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (6, '2016-04-02', 0, 'BO-75d78sq', 7);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (7, '2015-04-03', 57, 'BO-75d78wwq', 3);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (8, '2017-03-06', 8847, 'BO-75d78aq', 8);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (9, '2016-04-08', 78, 'A-q5d788s', 9);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (10, '2016-04-02', 588, 'A-q5d78qw', 9);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (11, '2016-12-06', 7878, 'A-q5d732s', 10);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (12, '2017-04-17', 0, 'A-q51opis', 11);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (13, '2016-10-13', 787, 'A-q5d7x8s', 12);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (14, '2013-12-06', 7887, 'A-q5d7uus', 13);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (15, '2016-12-05', 77, 'A-q5d7mtx', 12);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (16, '2016-07-27', 87, 'A-99qres3', 13);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (17, '2010-04-04', 0, 'A-q5d7ew3', 14);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (18, '1909-04-03', 0, 'A-q577wec', 15);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (19, '2015-09-14', 8448, 'TU-q5d788s', 18);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (20, '2010-02-13', 7778, 'TU-q5d1278s', 21);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (21, '2015-06-04', 57, 'BO-7577q', 1);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (22, '2011-04-02', 84, 'BO-75qwwwq', 5);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (23, '2012-02-01', 54, 'BO-aehp587', 6);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (24, '2014-04-04', 180, 'BO-75998wwq', 4);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (25, '2016-04-18', 15, 'BO-75d7www', 7);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (27, '2003-04-03', 56, 'BO-75d7qwq', 3);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (28, '2008-03-06', 847, 'BO-238wwq', 8);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (29, '2007-04-08', 787, 'A-99qyuts3', 9);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (30, '2004-04-02', 58, 'A-955we3', 9);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (31, '2013-12-06', 79, 'A-9dfsss3', 10);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (32, '2017-07-17', 575, 'A-975eew', 11);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (33, '2016-11-13', 7877, 'A-99jjs3', 12);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (34, '2013-12-28', 787, 'A-9jqres3', 13);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (35, '2016-01-05', 7778, 'A-99qjes3', 12);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (36, '2014-07-27', 8769, 'A-99ppres3', 13);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (37, '2001-04-04', 447, 'A-99pres3', 14);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (38, '1999-04-03', 9657, 'A-p9qres3', 15);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (39, '2011-09-14', 848, 'TU-qqq711s', 18);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (40, '2010-04-13', 778, 'TU-qewee8s', 21);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (41, '2016-12-05', 8447, 'TU-q5iuy8s', 17);
INSERT INTO airport.plane (id, dateOfManufacture, flyingHours, serialNumber, model_id) VALUES (42, '2012-12-05', 847, 'TU-78857s', 17);



INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (1, 30, 'Test Podvozku');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (2, 30, 'Test Klapek');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (3, 200, 'Malý servis');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (4, 300, 'Velký servis');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (5, 50, 'Test tlaku v kabině');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (6, 100, 'Test VOP');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (7, 100, 'Test SOP');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (8, 3, 'Test Elektroniky');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (9, 1, 'Test Zásob');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (10, 300, 'Test čerpadel');
INSERT INTO airport.test_type (id, checkingInterval, name) VALUES (11, 300, 'Test hydrauliky');


INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (1, 'Tlak v pneumatikách (Bar)', 90, 80, 1);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (2, 'Tlak v brzdách (Bar)', 50, 45, 1);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (3, 'Výška vzorku (mm)', 20, 3, 1);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (4, 'Síla tlumičů (N)', 1500, 1000, 1);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (5, 'Vzletové klapky (Stupně)', 40, 35, 2);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (6, 'Přistavací klapky (St)', 30, 25, 2);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (7, 'Rozmražování klapek (°C)', 70, 65, 2);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (8, 'Kabinové filtry (počet)', 80, 80, 3);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (9, 'Olejové filtry (počet)', 5, 5, 3);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (10, 'Palovové filtry (počet)', 2, 2, 3);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (11, 'Výměna oleje (litry)', 151, 149, 3);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (12, 'Vůle turbnínových ložisek (mm)', 3, 0, 3);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (13, 'Kabinové filtry (počet)', 80, 80, 4);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (14, 'Olejové filtry (počet)', 5, 5, 4);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (15, 'Palovové filtry (počet)', 2, 2, 4);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (16, 'Výměna oleje (litry)', 151, 149, 4);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (17, 'Vůle turbnínových ložisek (mm)', 3, 0, 4);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (18, 'Výměna turbnínových ložisek (počet)', 15, 15, 4);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (19, 'Výkon turbín', 1800, 1500, 4);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (20, 'Tlak v pilotní kabině (Bar)', 10, 5, 5);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (21, 'Tlak v prostoru pro cestující (Bar)', 8, 5, 5);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (22, 'Tlak v zavazadlovém prostoru (Bar)', 3, 1, 5);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (23, 'Tlak v podvozkovem prostoru (Bar)', 15, 10, 5);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (24, 'Tlak hydrauliky (Bar)', 10, 5, 6);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (25, 'Maximální výchylka (Stupně)', 30, 25, 6);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (26, 'Vůle (Stupně)', 3, 0, 6);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (27, 'Tlak hydrauliky (Bar)', 10, 5, 7);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (28, 'Maximální výchylka (Stupně)', 30, 25, 7);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (29, 'Okruh 1 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (30, 'Okruh 2 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (31, 'Okruh 3 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (32, 'Okruh 4 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (33, 'Okruh 5 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (34, 'Okruh 6 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (35, 'Okruh 7 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (36, 'Okruh 8 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (37, 'Okruh 9 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (38, 'Okruh 10 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (39, 'Okruh 11 (Volty)', 20, 18, 8);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (40, 'Občerstvení-jídlo typ 1 (Ks)', 20, 15, 9);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (41, 'Občerstvení-jídlo typ 2 (Ks)', 20, 15, 9);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (42, 'Občerstvení-jídlo typ 3 (Ks)', 20, 15, 9);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (43, 'Občerstvení-jídlo typ 4 (Ks)', 20, 15, 9);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (44, 'Občerstvení-nápoj typ 1 (Ks)', 200, 110, 9);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (45, 'Občerstvení-nápoj typ 2 (Ks)', 200, 110, 9);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (46, 'Občerstvení-nápoj typ 3 (Ks)', 200, 110, 9);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (47, 'Občerstvení-nápoj typ 4 (Ks)', 200, 110, 9);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (48, 'Čerpadlo 1 (Otáčky)', 200, 110, 10);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (49, 'Čerpadlo 2 (Otáčky)', 400, 370, 10);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (50, 'Čerpadlo 1 Tlak (Bar)', 10, 9, 10);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (51, 'Čerpadlo 2 Tlak (Bar)', 11, 10, 10);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (52, 'Okruh 1 (Bar)', 40, 36, 11);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (53, 'Okruh 2 (Bar)', 8, 6, 11);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (54, 'Okruh 3 (Bar)', 20, 18, 11);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (55, 'Okruh 4 (Bar)', 25, 23, 11);
INSERT INTO airport.test_type_parameter (id, checkingValue, max, min, type_id) VALUES (56, 'Okruh 5 (Bar)', 20, 16, 11);


INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (1, '2017-04-05', 1, 2, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (75, 1, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (40, 2, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (8, 3, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1800, 4, 1);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (2, '2017-03-01', 1, 2, 2);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (38, 5, 2);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (30, 6, 2);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (68, 7, 2);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (3, '2010-01-11', 1, 2, 8);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (118, 29, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 30, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 31, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 32, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (20, 33, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (21, 34, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 35, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (17, 36, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (18, 37, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 38, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 39, 3);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (4, '2017-04-05', 1, 2, 8);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 29, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 30, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 31, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 32, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (18, 33, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (18, 34, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 35, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 36, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (20, 37, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (21, 38, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 39, 4);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (5, '2010-12-07', 1, 2, 3);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (80, 8, 5);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (5, 9, 5);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (2, 10, 5);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (150, 11, 5);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1, 12, 5);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (6, '2017-02-14', 1, 2, 4);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (80, 13, 6);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (5, 14, 6);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (2, 15, 6);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (152, 16, 6);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (3, 17, 6);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (15, 18, 6);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1750, 19, 6);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (7, '2017-04-05', 1, 2, 5);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (8, 20, 7);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (8, 21, 7);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (4, 22, 7);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (8, 23, 7);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (8, '2017-04-05', 1, 2, 6);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (10, 24, 8);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (30, 25, 8);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (9, '2017-04-05', 1, 2, 7);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (2, 26, 8);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (7, 27, 9);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (24, 28, 9);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (10, '2017-04-05', 1, 2, 11);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (40, 52, 10);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (7, 53, 10);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (19, 54, 10);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (26, 55, 10);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (18, 56, 10);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (11, '2015-04-05', 1, 2, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (75, 1, 11);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (40, 2, 11);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (8, 3, 11);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1800, 4, 11);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (12, '2013-04-05', 1, 2, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (75, 1, 12);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (40, 2, 12);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (8, 3, 12);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1800, 4, 12);





INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (13, '2017-04-05', 2, 2, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (75, 1, 13);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (40, 2, 13);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (8, 3, 13);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1800, 4, 13);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (14, '2015-04-05', 2, 2, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (75, 1, 14);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (40, 2, 14);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (8, 3, 14);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1800, 4, 14);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (15, '2013-04-05', 2, 2, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (75, 1, 15);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (40, 2, 15);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (8, 3, 15);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1800, 4, 15);


INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (16, '2015-04-05', 3, 2, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (85, 1, 16);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (46, 2, 16);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (4, 3, 16);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1800, 4, 16);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (17, '2017-04-05', 3, 2, 1);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (90, 1, 17);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (48, 2, 17);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (5, 3, 17);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (1000, 4, 17);

INSERT INTO airport.test (id, date, plane_id, technician_id, type_id) VALUES (18, '2017-04-02', 3, 2, 9);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (18, 40, 18);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (30, 41, 18);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (10, 42, 18);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (18, 43, 18);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (0, 44, 18);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (18, 45, 18);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (18, 46, 18);
INSERT INTO airport.test_result (resultValue, checkingValue_id, test_id) VALUES (18, 47, 18);


