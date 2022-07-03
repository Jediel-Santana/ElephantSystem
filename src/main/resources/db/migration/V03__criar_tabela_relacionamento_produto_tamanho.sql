CREATE TABLE produto_tamanho(
	id_produto_tamanho BIGINT PRIMARY KEY AUTO_INCREMENT,
	id_produto BIGINT NOT NULL,
	id_tamanho BIGINT NOT NULL,
	quantidade INT NOT NULL,
	FOREIGN KEY (id_produto) REFERENCES produto(id),
	FOREIGN KEY (id_tamanho) REFERENCES tamanho(id) 
	) ENGINE=INNODB;
	

