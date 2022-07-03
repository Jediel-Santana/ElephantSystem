#Grupos
INSERT INTO grupo (id, nome) VALUES (1, 'Administrador Master');
INSERT INTO grupo (id, nome) VALUES (2, 'Administrador');
INSERT INTO grupo (id, nome) VALUES (3, 'Cliente');

#Permissoes
INSERT INTO permissao VALUES (1, 'ROLE_ADMINISTRADOR_MASTER');
INSERT INTO permissao VALUES (2, 'ROLE_ADMINISTRADOR');
INSERT INTO permissao VALUES (3, 'ROLE_CLIENTE');

#Grupo permissao
INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (1, 2);
INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (2, 2);
INSERT INTO grupo_permissao (id_grupo, id_permissao) VALUES (3, 3);

