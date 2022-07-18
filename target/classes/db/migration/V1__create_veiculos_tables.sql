CREATE TABLE IF NOT EXISTS tb_veiculo(
	id_veiculo INT AUTO_INCREMENT PRIMARY KEY,
	veiculo  VARCHAR(60),
	marca VARCHAR(30),
    ano	  int,
	cor    VARCHAR(20),
	descricao VARCHAR(255),
	vendido Boolean,
	dt_created DATE,
	dt_updated DATE
);


