package view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.Cliente;
import model.Conta;
import utilitario.Util;

public class Banco {
	static final String NOME = "T.E.M.P.L.E Bank";
	static final String CNPJ = "707070-700";
	static Scanner sc = new Scanner(System.in);
	static List<Conta> contas;
	static List<Cliente> clientes;

	public static void main(String[] args) {
		Banco.contas = new ArrayList<Conta>();
		Banco.menu();
	}
	
	public static void menu() {
		int escolha = 0;
		System.out.println("================================================================");
		System.out.println("==========================| ATM |===============================");
		System.out.println("===========| Bem vindo(a) ao " + NOME + " |=================");
		System.out.println("==========================| ATM |===============================");
		System.out.println("Selecione uma opção no menu: ");
		System.out.println("==========================| ATM |===============================");
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
					System.out.println("O titular da conta já possui cadastro?");
					try {
						int opcao = Integer.parseInt(sc.nextLine());
						if (opcao == 1) {
							System.out.print("Digite o nome do cliente: ");
							String nome = sc.nextLine();
							System.out.print("\nDigite o email do cliente: ");
							String email = sc.nextLine();
							System.out.print("\nDigite o CPF do cliente: ");
							String cpf = sc.nextLine();
							System.out.println("\nDigite a data de nascimento do cliente: ");
							Date dt_nascimento = Util.StringParaDate(sc.nextLine());
							clientes.add(new Cliente(nome, email, cpf, dt_nascimento));
							contas.add(new Conta(clientes.get(clientes.size() - 1)));
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
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
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
