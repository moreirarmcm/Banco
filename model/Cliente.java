package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

import com.mysql.cj.xdevapi.PreparableStatement;

import util.Util;
import view.Banco;

public class Cliente {
	private static int contador = 1;
	private static int codigo; // código do ultimo cliente;
	private String nome, email, cpf;
	private LocalDate data_nascimento; 
	private LocalDate data_cadastro;
	private Scanner sc = new Scanner(System.in);
	private Connection conexao;
	
	public int criandoCliente() {
		conexao = Banco.Conectando();
		int id_cliente = 0;
		String recupera_ultimo_cliente = "SELECT max(id) FROM cliente";
		System.out.print("Digite o nome do cliente: ");
		String nome = sc.nextLine();
		System.out.print("\nDigite o email do cliente: ");
		String email = sc.nextLine();
		System.out.print("\nDigite o CPF do cliente: ");
		String cpf = sc.nextLine();
		System.out.println("\nDigite a data de nascimento do cliente:\nDigite os números separados por '/'");
		String dt_nascimento = sc.nextLine();
		String insere_cliente = "INSERT INTO cliente (nome, email, cpf, data_nascimento, data_cadastro) VALUES (?,?,?,?,?)";
		
		PreparedStatement inserindo;
		//System.out.println("Entrando no Try...");
		Util.Pausar(2);
		try{
			inserindo = conexao.prepareStatement(insere_cliente);
			inserindo.setString(1, nome);
			inserindo.setString(2, email);
			inserindo.setString(3, cpf);
			inserindo.setString(4, dt_nascimento);
			inserindo.setString(5, Util.DataAtual());
			inserindo.execute();
			try {	
				ResultSet recuperando_ultimo_cliente = conexao.createStatement().executeQuery(recupera_ultimo_cliente);
				if (recuperando_ultimo_cliente.next()) {
					id_cliente = recuperando_ultimo_cliente.getInt(1);
					recupera_ultimo_cliente = "SELECT* FROM cliente WHERE id = " + id_cliente;
					recuperando_ultimo_cliente = conexao.createStatement().executeQuery(recupera_ultimo_cliente);
					if( recuperando_ultimo_cliente.next()) {
						System.out.println("Usuário criado!"
								+ "\n    N° identificação: " + recuperando_ultimo_cliente.getInt("id")
								+ "\n    Nome do Titular: " + recuperando_ultimo_cliente.getString("nome")
								+ "\n    Data de criação da conta: " + recuperando_ultimo_cliente.getString("data_cadastro"));
					}
				}
			}catch (SQLException e) {
				System.out.println("Não foi possível criar cliente.\nRevise os dados informados.");
				Util.Pausar(3);
				Banco.menu();
			}
		}catch (Exception e) {
			System.out.println("Deu alguma pane");
		}
		return id_cliente;
	}
}