CREATE TABLE pessoa(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL,
	data_nascimento DATE,
	cpf VARCHAR(13)
);