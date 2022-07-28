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

	
	