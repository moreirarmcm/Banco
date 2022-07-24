package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.Cliente;
import model.Conta;
import utilitario.Util;

/*Revisão - ln 60 (switch-case 1)/
 * 
 */

public class Banco {
	static final String NOME = "T.E.M.P.L.E Bank";
	static final String CNPJ = "707070-700";
	static Scanner sc = new Scanner(System.in);
	static List<Conta> contas;
	static List<Cliente> clientes;

	public static void main(String[] args) {
		Banco.contas = new ArrayList<Conta>();
		Banco.clientes = new ArrayList<Cliente>();
		Banco.menu();
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
			escolha = Integer.parseInt(sc.nextLine());
			switch (escolha) {
				case 1:
					System.out.println("O titular da conta já possui cadastro? \n<1 para 'SIM'    0 para 'NÃO'");
					try {
						int opcao = Integer.parseInt(sc.nextLine());
						if (opcao == 0) {
							System.out.print("Digite o nome do cliente: ");
							String nome = sc.nextLine();
							System.out.print("\nDigite o email do cliente: ");
							String email = sc.nextLine();
							System.out.print("\nDigite o CPF do cliente: ");
							String cpf = sc.nextLine();
							System.out.println("\nDigite a data de nascimento do cliente: ");
							//Gambiarra, revisar.
							int dia = sc.nextInt();
							int mes = sc.nextInt();
							int ano = sc.nextInt();
							LocalDate data = LocalDate.of(ano, mes, dia);
							clientes.add(new Cliente(nome, email, cpf, data));
							System.out.println("cliente  criado.");
							contas.add(new Conta(clientes.get(clientes.size() - 1)));
							System.out.println("Conta criada");
						}else {
							System.out.println("Qual o código de identificação do cliente?");
							int cliente = 0;
							try {
								cliente = Integer.parseInt(sc.nextLine());
								contas.add(new Conta(clientes.get(cliente)));
							}catch (Exception e) {
								System.out.println("Identificação não reconhecida.");
								Util.Pausar(3);
								Banco.menu();
							}
						}
					}catch (Exception e) {
						System.out.println("Informação não compreendida. Reinice o atendimento.");
						Util.Pausar(3);
						Banco.menu();
					}
					break;
					
				case 2:
					System.out.println("Informe o código de identificação da conta: ");
					int codigo = Integer.parseInt(sc.nextLine());
					System.out.println("O saldo total da conta é : " + Util.doubleParaDinheiro(contas.get(codigo).getSaldo_total()) + "\nQual o valor desejado para saque?");
					double valor = Double.parseDouble(sc.nextLine());
					contas.get(codigo).sacar(valor);
					Util.Pausar(3);
					Banco.menu();
					break;
				case 3:
					System.out.println("Informe o código de identificação da conta: ");
					codigo = Integer.parseInt(sc.nextLine());
					System.out.println("Informe o valor a ser depositado:");
					valor = sc.nextDouble();
					contas.get(codigo).depositar(valor);	
					break;
				case 4:
					System.out.println("Informe o código de identificação da conta: ");
					codigo = sc.nextInt();
					System.out.println("Informe o código de identificação da conta recebedora: ");
					int codigo_destino = sc.nextInt();
					System.out.println("Qual valor deseja tranaferir?");
					valor = sc.nextDouble();
					contas.get(codigo).transferir(contas.get(codigo_destino), valor);
					break;
				case 5:
					contas.forEach(System.out::println);
					break;
				case 6:
					System.out.println("O " + NOME + " agradece sua preferência!");
					Util.Pausar(3);
					System.exit(0);
					break;
				default:
					System.out.println("O número " + escolha + " não é uma opção válida. Escolha uma opção válida.");
					Util.Pausar(3);
					Banco.menu();
				}
		} catch (NumberFormatException e) {
			System.out.println("Não foi possível realizar o procedimento.");
			Util.Pausar(3);
			Banco.menu();
		}
		
	}
}
