INSERT INTO users (id, username, email, password, wallet) VALUES (
                                                                  'fa5357e7-fd4c-4e55-a3a7-f5c99a7f6b53',
                                                                  'Ariana',
                                                                  'aitk@gmail.com',
                                                                  '123456',
                                                                  10.54
                                                                  );
INSERT INTO users (id, username, email, password, wallet) VALUES (
                                                                  '478e610f-a26f-4d66-843c-0ce9c6df195a',
                                                                  'Carlos',
                                                                  'reng@gmail.com',
                                                                  '123456',
                                                                  12.00
                                                                  );
INSERT INTO users (id, username, email, password, wallet) VALUES (
                                                                  '55da1623-2f77-4042-9730-083b3070180f',
                                                                  'Márcia',
                                                                  'segfault@gmail.com',
                                                                  '123456',
                                                                  22.75
                                                                  );
INSERT INTO users (id, username, email, password, wallet) VALUES (
                                                                  '1681eadd-eb3e-45ec-9db5-3c5d862c1cbf',
                                                                  'Tiago',
                                                                  'arguz@gmail.com',
                                                                  '123456',
                                                                  2.44
                                                                  );



INSERT INTO sports (id, name, format) VALUES ('5a92eb3b-a817-4631-a14b-46997c74d5a5', 'Futebol', 'Team');
INSERT INTO sports (id, name, format) VALUES ('45e8e00f-16f3-44f7-8784-c4662540bf22', 'Ténis', 'Individual');
INSERT INTO sports (id, name, format) VALUES ('d0977c68-412b-499a-a31f-0e03c92f2b4e', 'Basquetebol', 'Team');
INSERT INTO sports (id, name, format) VALUES ('da0ef815-f2cb-4033-af38-c8e2f7ff7db3', 'Golfe', 'Individual');


--EQUIPAS FUTEBOL
INSERT INTO teams (id, name, sport) VALUES ('9cbb08f3-65cd-4fa8-92e4-56ad190ec1f5', 'FC Porto', '5a92eb3b-a817-4631-a14b-46997c74d5a5');
INSERT INTO teams (id, name, sport) VALUES ('990fa1d6-5847-47be-aeda-f78cfc4311df', 'Sporting CP', '5a92eb3b-a817-4631-a14b-46997c74d5a5');
INSERT INTO teams (id, name, sport) VALUES ('168ee262-1bfa-4d5a-aec0-657289b7a155', 'SL Benfica', '5a92eb3b-a817-4631-a14b-46997c74d5a5');

--EQUIPAS BASQUET
INSERT INTO teams (id, name, sport) VALUES ('6166013b-3e28-4638-86df-cf2b1fb17191', 'SL Benfica (B)', 'd0977c68-412b-499a-a31f-0e03c92f2b4e');
INSERT INTO teams (id, name, sport) VALUES ('db0cadfc-5cfb-461e-a6a6-489ed46952a2', 'FC Porto (B)', 'd0977c68-412b-499a-a31f-0e03c92f2b4e');
INSERT INTO teams (id, name, sport) VALUES ('b4b89d1a-a094-400e-b6ca-d59b53cfe852', 'Sporting CP (B)', 'd0977c68-412b-499a-a31f-0e03c92f2b4e');

--JOGADORES TENIS
INSERT INTO teams (id, name, sport) VALUES ('dd86ae5d-17da-4fe8-a5cb-65b1d88b3a7d', 'Júlio Iglesias', '45e8e00f-16f3-44f7-8784-c4662540bf22');
INSERT INTO teams (id, name, sport) VALUES ('b2e102e9-8466-48a9-9a16-aedec05eab56', 'Gandim', '45e8e00f-16f3-44f7-8784-c4662540bf22');
INSERT INTO teams (id, name, sport) VALUES ('ab85829d-18f1-4665-b1ff-54a39d983101', 'Jarad Higgins', '45e8e00f-16f3-44f7-8784-c4662540bf22');



--JOGOS FUTEBOL
INSERT INTO games (id, sport, game_date, team1, team2, game_result) VALUES (
                                                                            'e3aecbc1-28c9-49b6-8dbf-c0277e6c994f',
                                                                            '5a92eb3b-a817-4631-a14b-46997c74d5a5',
                                                                            '2022-01-15 21:00','168ee262-1bfa-4d5a-aec0-657289b7a155',
                                                                            '9cbb08f3-65cd-4fa8-92e4-56ad190ec1f5',
                                                                            '2'
                                                                            );

INSERT INTO games (id, sport, game_date, team1, team2) VALUES (
                                                               '2ae993b3-a7d8-45c4-b878-14adae76288a',
                                                               '5a92eb3b-a817-4631-a14b-46997c74d5a5',
                                                               '2022-02-03 20:30', '990fa1d6-5847-47be-aeda-f78cfc4311df',
                                                               '9cbb08f3-65cd-4fa8-92e4-56ad190ec1f5'
                                                               );

INSERT INTO games (id, sport, game_date, team1, team2) VALUES (
                                                               '58936970-d7cf-4629-84b6-15162bcbaa96',
                                                               '5a92eb3b-a817-4631-a14b-46997c74d5a5',
                                                               '2022-02-08 21:00',
                                                               '168ee262-1bfa-4d5a-aec0-657289b7a155',
                                                               '990fa1d6-5847-47be-aeda-f78cfc4311df'
                                                               );

--JOGOS BASQUET
INSERT INTO games (id, sport, game_date, team1, team2) VALUES (
                                                               'fa1fe90a-3cc6-4832-b7bc-d8dad0d32d41',
                                                               'd0977c68-412b-499a-a31f-0e03c92f2b4e',
                                                               '2022-01-26 18:00',
                                                               '6166013b-3e28-4638-86df-cf2b1fb17191',
                                                               'b4b89d1a-a094-400e-b6ca-d59b53cfe852'
                                                               );

--JOGOS TENIS
INSERT INTO games (id, sport, game_date, team1, team2) VALUES (
                                                               'f779fe47-c090-4125-9a48-861a01e6d33a',
                                                               '45e8e00f-16f3-44f7-8784-c4662540bf22',
                                                               '2022-01-26 19:45',
                                                               'ab85829d-18f1-4665-b1ff-54a39d983101',
                                                               'b2e102e9-8466-48a9-9a16-aedec05eab56'
                                                               );



--BETS FUTEBOL
INSERT INTO house_bets (id, bet_state, game_id, odd_win1, odd_draw, odd_win2) VALUES ('702715c0-fdc5-40b1-8d5c-ee7ed9fdc755', 'Closed', 'e3aecbc1-28c9-49b6-8dbf-c0277e6c994f', 1.65, 3.05, 4.75);
INSERT INTO house_bets (id, bet_state, game_id, odd_win1, odd_draw, odd_win2) VALUES ('fd54daf8-6054-476f-9336-3cb267833819', 'Open', '2ae993b3-a7d8-45c4-b878-14adae76288a', 2.05, 2.32, 3.35);
INSERT INTO house_bets (id, bet_state, game_id, odd_win1, odd_draw, odd_win2) VALUES ('aa5ed3cf-dc1c-412c-a687-fcb0fe5c8e50', 'Open', '58936970-d7cf-4629-84b6-15162bcbaa96', 7.40, 3.35, 1.23);

--BETS BASQUET
INSERT INTO house_bets (id, bet_state, game_id, odd_win1, odd_draw, odd_win2) VALUES ('96f81c4b-0baa-4a5c-bbd4-725524c7a63d', 'Open', 'fa1fe90a-3cc6-4832-b7bc-d8dad0d32d41', 4.60, 12.55, 1.04);

--BETS TENIS
INSERT INTO house_bets (id, bet_state, game_id, odd_win1, odd_win2) VALUES ('384add5f-db64-4dda-bcc8-7660d1f4141d', 'Open', 'f779fe47-c090-4125-9a48-861a01e6d33a', 2.10, 1.50);



INSERT INTO user_bets (id, user, price, bet, house_bet) VALUES (
                                                                '80e599f9-a6da-40a1-9fee-6ffef46716ca',
                                                                '55da1623-2f77-4042-9730-083b3070180f',
                                                                10.0,
                                                                '1',
                                                                '702715c0-fdc5-40b1-8d5c-ee7ed9fdc755'
                                                                );

INSERT INTO user_bets (id, user, price, bet, house_bet) VALUES (
                                                               'b45213c8-68d6-453c-bca4-074829f769eb',
                                                               '55da1623-2f77-4042-9730-083b3070180f',
                                                               5.0,
                                                               '2',
                                                               '702715c0-fdc5-40b1-8d5c-ee7ed9fdc755'
                                                               );

INSERT INTO user_bets (id, user, price, bet, house_bet) VALUES (
                                                                '7ea8a0f4-8e8c-4845-93aa-8f74b503081f',
                                                                '55da1623-2f77-4042-9730-083b3070180f',
                                                                1.0,
                                                                'X',
                                                                '96f81c4b-0baa-4a5c-bbd4-725524c7a63d'
                                                               );
