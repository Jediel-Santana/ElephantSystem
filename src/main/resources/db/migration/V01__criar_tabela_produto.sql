CREATE TABLE produto(
		id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
		descricao VARCHAR(100) NOT NULL,
		codigo_de_barras BIGINT(20) NOT NULL,
		genero VARCHAR(50) NOT NULL,
		categoria VARCHAR(100) NOT NULL,
		cor VARCHAR(30),
		quantidade_estoque INTEGER(11) NOT NULL DEFAULT 0,
		preco DECIMAL(10, 2) NOT NULL,
		foto VARCHAR(1000),
		content_type VARCHAR(1000),
		data_criacao DATETIME
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

