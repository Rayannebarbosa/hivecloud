CREATE DATABASE transp;

USE transp;

/***** TABELA TRANSPORTADORAS *****/
CREATE TABLE tb_transportadoras (
  id int auto_increment primary key,
  nome varchar(100),
  email varchar (100),
  empresa varchar(100),
  telefone varchar(30),
  celular varchar(30),
  whatsapp varchar(30),
  modal varchar (100),
  cep varchar(100),
  estado varchar (2),
  cidade varchar (100),
  bairro varchar (100),
  endereco varchar (255),
  numero int  
);

/***** TABELA USUARIO *****/
CREATE TABLE tb_usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR (100),
  senha VARCHAR (100)
);

INSERT INTO transp.tb_usuario (email, senha) VALUES ('rayannelisse@hotmail.com', '1234'); 
INSERT INTO transp.tb_usuario (email, senha) VALUES ('hivecloud@hotmail.com', '1234'); 
INSERT INTO transp.tb_usuario (email, senha) VALUES ('teste@teste.com', '1234'); 