package view;

import java.sql.Connection;
import java.util.Scanner;

import model.Cliente;
import model.Conta;
import util.Util;

/*Revisão - ln 60 (switch-case 1)/
 * 
 */

public class Banco {
	static final String NOME = "T.E.M.P.L .E Bank";
	static final String CNPJ = "707070-700";
	static Scanner sc = new Scanner(System.in);
	static Util utilitario = new Util();
	static Cliente cliente = new Cliente();
	static Conta conta = new Conta();
	static Connection conexao;
	
	public static void main(String[] args) {
		conexao = utilitario.ConectandoMySQL("banco", "renan", "1234"); //Se conecta com o banco de dados;
		Banco.menu();
		System.exit(0);
	}
	
	public static void menu() {
		int escolha = 0;
		System.out.println("================================================================");
		System.out.println("==========================| ATM |===============================");
		System.out.println("===========| Bem vindo(a) ao " + NOME + " |=================");
		System.out.println("================================================================");
		System.out.println("================================================================");
		System.out.println("\n\n\nSelecione uma opção no menu: \n");
		System.out.println("1 - Criar conta;");
		System.out.println("2 - Efetuar saque;");
		System.out.println("3 - Efetuar depósito;");
		System.out.println("4 - Efetuar transferência;");
		System.out.println("5 - Listar contas;");
		System.out.println("6 - Sair do sistema;");
		try {
			escolha = Integer.parseInt(sc.nextLine()); // escolha de ação - do 1 ao 6;
			switch (escolha) {
				
				case 1:  // Criar conta
					System.out.println("O titular da conta já possui cadastro? \n  1 - para 'SIM'.\n  0 - para 'NÃO'.");
					int opcao; 
					try {
						opcao = Integer.parseInt(sc.nextLine()); // 1 ou 0;
						int codigo_cliente = 0; // receberá o id do cliente criado;
						if (opcao == 0) { // será necessário criar um cliente para ser titular da conta;
							codigo_cliente = cliente.criandoCliente();
						}else if (opcao == 1) { // apenas o número de identificação do cliente;
							System.out.println("Qual o código de identificação do cliente:");
							try {
								codigo_cliente = Integer.parseInt(sc.nextLine());
							}catch (NumberFormatException e) {
								System.out.println("Valor informado como identificação de cliente não é um número.\nReinicie o atendimento.");
								Util.Pausar(3);
								Banco.menu();
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
						Banco.menu();
					}
				}
			
		}catch (NumberFormatException e) {
			System.out.println("Opção incorreta.\nDigite uma opção numérica válida.");
			Util.Pausar(3);
			Banco.menu();
		}
	}
	
	public static Connection Conectando() {
		return conexao;
	}
}