
ALTER TABLE venda ADD COLUMN id_usuario BIGINT(20),
		ADD FOREIGN KEY (id_usuario) REFERENCES usuario(id);