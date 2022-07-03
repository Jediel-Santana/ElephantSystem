

INSERT INTO pessoa(nome, data_nascimento, cpf) VALUES('Jediel Santana', '2022-09-05 22:00:00', '43012433058');
INSERT INTO usuario(email, senha, ativo, id_pessoa) VALUES('jediel@elephantsystem.com', '$2a$10$xXejrZCIaVbd/JTYhu1IZ.P1te2uVw7ccxpi2.vjPeL2kUsTSS602', 1, (SELECT id FROM pessoa WHERE cpf = '43012433058'));
UPDATE pessoa p JOIN (SELECT id FROM pessoa WHERE cpf = '43012433058') AS sub SET p.id_usuario = (SELECT u.id FROM usuario u WHERE email = 'jediel@elephantsystem.com') WHERE p.id = sub.id;

INSERT INTO pessoa(nome, data_nascimento, cpf) VALUES('Daniella Farias', '2022-09-04 22:00:00', '68711843098');
INSERT INTO usuario(email, senha, ativo, id_pessoa) VALUES('daniella@elephantsystem.com', '$2a$10$xXejrZCIaVbd/JTYhu1IZ.P1te2uVw7ccxpi2.vjPeL2kUsTSS602', 1, (SELECT id FROM pessoa WHERE cpf = '68711843098'));
UPDATE pessoa p JOIN (SELECT id FROM pessoa WHERE cpf = '68711843098') AS sub SET p.id_usuario = (SELECT u.id FROM usuario u WHERE email = 'daniella@elephantsystem.com') WHERE p.id = sub.id;

INSERT INTO pessoa(nome, data_nascimento, cpf) VALUES('Ícaro Batalha', '2022-09-03 22:00:00', '96765714073');
INSERT INTO usuario(email, senha, ativo, id_pessoa) VALUES('icaro@elephantsystem.com', '$2a$10$xXejrZCIaVbd/JTYhu1IZ.P1te2uVw7ccxpi2.vjPeL2kUsTSS602', 1, (SELECT id FROM pessoa WHERE cpf = '96765714073'));
UPDATE pessoa p JOIN (SELECT id FROM pessoa WHERE cpf = '96765714073') AS sub SET p.id_usuario = (SELECT u.id FROM usuario u WHERE email = 'icaro@elephantsystem.com') WHERE p.id = sub.id;

INSERT INTO pessoa(nome, data_nascimento, cpf) VALUES('Saulo Henrique', '2022-09-02 22:00:00', '26915545034');
INSERT INTO usuario(email, senha, ativo, id_pessoa) VALUES('saulo@elephantsystem.com', '$2a$10$xXejrZCIaVbd/JTYhu1IZ.P1te2uVw7ccxpi2.vjPeL2kUsTSS602', 1, (SELECT id FROM pessoa WHERE cpf = '26915545034'));
UPDATE pessoa p JOIN (SELECT id FROM pessoa WHERE cpf = '26915545034') AS sub SET p.id_usuario = (SELECT u.id FROM usuario u WHERE email = 'saulo@elephantsystem.com') WHERE p.id = sub.id;

INSERT INTO pessoa(nome, data_nascimento, cpf) VALUES('Vinicius Augusto', '2022-09-01 22:00:00', '84205346093');
INSERT INTO usuario(email, senha, ativo, id_pessoa) VALUES('vinicius@elephantsystem.com', '$2a$10$xXejrZCIaVbd/JTYhu1IZ.P1te2uVw7ccxpi2.vjPeL2kUsTSS602', 1, (SELECT id FROM pessoa WHERE cpf = '84205346093'));
UPDATE pessoa p JOIN (SELECT id FROM pessoa WHERE cpf = '84205346093') AS sub SET p.id_usuario = (SELECT u.id FROM usuario u WHERE email = 'vinicius@elephantsystem.com') WHERE p.id = sub.id;

#Adm Testes
INSERT INTO pessoa(nome, data_nascimento, cpf) VALUES('Administrador Master teste', '2022-09-01 22:00:00', '11774348063');
INSERT INTO usuario(email, senha, ativo, id_pessoa) VALUES('admMasterTeste@elephantsystem.com', '$2a$10$xXejrZCIaVbd/JTYhu1IZ.P1te2uVw7ccxpi2.vjPeL2kUsTSS602', 1, (SELECT id FROM pessoa WHERE cpf = '11774348063'));
UPDATE pessoa p JOIN (SELECT id FROM pessoa WHERE cpf = '11774348063') AS sub SET p.id_usuario = (SELECT u.id FROM usuario u WHERE email = 'admMasterTeste@elephantsystem.com') WHERE p.id = sub.id;

INSERT INTO pessoa(nome, data_nascimento, cpf) VALUES('Administrador teste', '2022-09-01 22:00:00', '29574265013');
INSERT INTO usuario(email, senha, ativo, id_pessoa) VALUES('admTeste@elephantsystem.com', '$2a$10$xXejrZCIaVbd/JTYhu1IZ.P1te2uVw7ccxpi2.vjPeL2kUsTSS602', 1, (SELECT id FROM pessoa WHERE cpf = '29574265013'));
UPDATE pessoa p JOIN (SELECT id FROM pessoa WHERE cpf = '29574265013') AS sub SET p.id_usuario = (SELECT u.id FROM usuario u WHERE email = 'admTeste@elephantsystem.com') WHERE p.id = sub.id;

INSERT INTO usuario_grupo(id_usuario, id_grupo) VALUES((SELECT id FROM usuario WHERE email = 'jediel@elephantsystem.com'), 1);
INSERT INTO usuario_grupo(id_usuario, id_grupo) VALUES((SELECT id FROM usuario WHERE email = 'daniella@elephantsystem.com'), 1);
INSERT INTO usuario_grupo(id_usuario, id_grupo) VALUES((SELECT id FROM usuario WHERE email = 'icaro@elephantsystem.com'), 1);
INSERT INTO usuario_grupo(id_usuario, id_grupo) VALUES((SELECT id FROM usuario WHERE email = 'saulo@elephantsystem.com'), 1);
INSERT INTO usuario_grupo(id_usuario, id_grupo) VALUES((SELECT id FROM usuario WHERE email = 'vinicius@elephantsystem.com'), 1);

#Adms Testes
INSERT INTO usuario_grupo(id_usuario, id_grupo) VALUES((SELECT id FROM usuario WHERE email = 'admMasterTeste@elephantsystem.com'), 1);
INSERT INTO usuario_grupo(id_usuario, id_grupo) VALUES((SELECT id FROM usuario WHERE email = 'admTeste@elephantsystem.com'), 2);

#Clientes
INSERT INTO pessoa(nome, data_nascimento, cpf) VALUES('Severino Silva', '2022-09-05 22:00:00', '33902463040');
INSERT INTO usuario(email, senha, ativo, id_pessoa) VALUES('severino@elephantsystem.com', '$2a$10$OMcstIJJwAnX66Y0OATA9ejBMk.mpVedkz6w4BudSbP7F0zuSXaui', 1, (SELECT id FROM pessoa WHERE cpf = '33902463040'));
UPDATE pessoa p JOIN (SELECT id FROM pessoa WHERE cpf = '33902463040') AS sub SET p.id_usuario = (SELECT u.id FROM usuario u WHERE email = 'severino@elephantsystem.com') WHERE p.id = sub.id;

INSERT INTO pessoa(nome, data_nascimento, cpf) VALUES('Augustinho Carrara', '2022-09-04 22:00:00', '66734619001');
INSERT INTO usuario(email, senha, ativo, id_pessoa) VALUES('augustinho@elephantsystem.com', '$2a$10$OMcstIJJwAnX66Y0OATA9ejBMk.mpVedkz6w4BudSbP7F0zuSXaui', 1, (SELECT id FROM pessoa WHERE cpf = '68711843098'));
UPDATE pessoa p JOIN (SELECT id FROM pessoa WHERE cpf = '66734619001') AS sub SET p.id_usuario = (SELECT u.id FROM usuario u WHERE email = 'augustinho@elephantsystem.com') WHERE p.id = sub.id;

INSERT INTO usuario_grupo(id_usuario, id_grupo) VALUES((SELECT id FROM usuario WHERE email = 'severino@elephantsystem.com'), 3);
INSERT INTO usuario_grupo(id_usuario, id_grupo) VALUES((SELECT id FROM usuario WHERE email = 'augustinho@elephantsystem.com'), 3);

