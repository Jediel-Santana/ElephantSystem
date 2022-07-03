CREATE TABLE venda(
	id BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
	data_criacao DATETIME,
	valor_frete DECIMAL(10, 2),
	valor_desconto DECIMAL(10, 2),
	valor_total DECIMAL(10, 2),
	observacao VARCHAR(255),
	data_hora_entrega DATETIME,
	status VARCHAR(50),
	forma_pagamento VARCHAR(50),
	cep VARCHAR(30),
	logradouro VARCHAR(255),
	bairro VARCHAR(50),
	cidade VARCHAR(50),
	estado VARCHAR(50),
	numero VARCHAR(20),
	complemento VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE item_venda(
		id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
		id_produto BIGINT(20),
		id_venda BIGINT(20),
		preco_unitario DECIMAL(10,2),
		FOREIGN KEY (id_produto) REFERENCES produto(id),
		FOREIGN KEY (id_venda) REFERENCES venda(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE item_venda_tamanho(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	id_item_venda BIGINT(20),
	id_tamanho BIGINT(20),
	quantidade INT,
	FOREIGN KEY (id_item_venda) REFERENCES item_venda(id),
	FOREIGN KEY (id_tamanho) REFERENCES tamanho(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;