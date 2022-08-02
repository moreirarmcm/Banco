CREATE DATABASE banco;

USE banco;

CREATE TABLE cliente(
	id INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR (30),
	email VARCHAR(30),
	cpf CHAR(11),
	data_nascimento DATE,
	data_cadastro DATE
);
	
CREATE TABLE conta(
	id INT PRIMARY KEY AUTO_INCREMENT,
	id_cliente INT,
	saldo FLOAT(11,2),
	limite FLOAT(11,2)
);

ALTER TABLE cliente 
	MODIFY cpf CHAR(14);

ALTER TABLE cliente
	MODIFY data_nascimento VARCHAR(11);
	
ALTER TABLE cliente
	MODIFY data_cadastro VARCHAR(11);
	
/*INSERT INTO cliente (nome, email, cpf, data_nascimento, data_cadastro) VALUES ("Renan", "moreirarmcm@gmail.com", "123.456.789-21", "1996-10-09", "2022-07-29");*/
	
SELECT* FROM CLIENTE;

ALTER TABLE conta
	MODIFY id INT(5);

ALTER TABLE conta
	ADD saldo_total FLOAT(11,2);