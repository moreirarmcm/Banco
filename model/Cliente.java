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
	private Scanner sc = new Scanner(System.in);
	private Connection conexao;
	private Conta conta = new Conta();
	
	/**
	 * Método que cria conta. Caso o cliente ainda não possua cadastro, cria um registro na tabela 'cliente' com os dados
	 * do usuário. Após verificação do cliente (já existen ou criado), cria uma nova conta.
	 */ 
	public void gerenciandoCliente() {
		System.out.println("O titular da conta já possui cadastro? \n  1 - para 'SIM'.\n  0 - para 'NÃO'.");
		int opcao; 
		int codigo_cliente = 0; // receberá o id do cliente criado;
		try {
			opcao = Integer.parseInt(sc.nextLine()); // 1 ou 0;
			if (opcao == 0) { // será necessário criar um cliente para ser titular da conta;
				System.out.println("Iniciando a criação de um novo cliente.\nAguarde...");
				Util.Pausar(3);
				codigo_cliente = criandoCliente();
			}else if (opcao == 1) { // apenas o número de identificação do cliente;
				System.out.println("Qual o código de identificação do cliente:");
				try {
					codigo_cliente = Integer.parseInt(sc.nextLine());
				}catch (NumberFormatException e) {
					System.out.println("Valor informado como identificação de cliente não é um número.\nReinicie o atendimento.");
					Util.Pausar(3);
					gerenciandoCliente();
				}
			}
			if (codigo_cliente != 0) { //Caso exista um cliente
				System.out.println("Iniciando a criação de uma nova conta.\nAguarde...");
				Util.Pausar(3);
				conta.CriandoConta(codigo_cliente);
			}
		}catch (NumberFormatException e) {
				System.out.println("Informação não compreendida. \nInforme se o cliente possui cadastro (apenas valores numéricos.");
				Util.Pausar(3);
				gerenciandoCliente();
		}
	}
	
	/**
	 * Método que cria um novo cliente.
	 * 
	 * Faz conexão com o banco de dados e insere os dados do novo cliente, então retorna o n° de identificação do cliente criado (int). 
	 * 
	 * @return id_cliente = chave primária da tabela 'cliente'.
	 */
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
						System.out.println("Cadastrando cliente.\nAguarde...\n");
						Util.Pausar(2);
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
		Util.Pausar(3);
		return id_cliente;
	}
}