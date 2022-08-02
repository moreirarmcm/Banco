package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import util.Util;
import view.Banco;

public class Conta {
	private int codigo;

	private int codigo_conta;
	// private Cliente cliente;
	private float saldo, limite, saldo_total = 0.0f;
	private Connection conexao;
	private Scanner sc = new Scanner(System.in);

	/**
	 * Método que recebe a chave primária do cliente e cria uma nova conta.
	 * 
	 * @param id_cliente
	 */
	public void CriandoConta(int id_cliente) {
		conexao = Banco.Conectando();
		String cria_conta = "INSERT INTO conta (id, id_cliente, saldo, limite, saldo_total) VALUES (?,?,?,?,?)";
		String verifica_cliente = "SELECT* FROM cliente WHERE id = " + id_cliente;
		String id_ultima_conta = "SELECT max(id) FROM conta";
		try {
			System.out.println("Recolhendo dados do cliente...");
			Util.Pausar(1);
			ResultSet cliente = conexao.createStatement().executeQuery(verifica_cliente);
			ResultSet id_conta = conexao.createStatement().executeQuery(id_ultima_conta);
			PreparedStatement criando_conta = conexao.prepareStatement(cria_conta);
			if (cliente.next()) {
				codigo = cliente.getInt(1);
			}
			if (id_conta.next()) {
				int x = id_conta.getInt(1);
				if (x == 0) {
					codigo_conta = 1001;
				} else {
					codigo_conta = x + 1;
				}
			}
			System.out.println("Inserindo dados da conta no sistema...");
			Util.Pausar(3);
			criando_conta.setInt(1, codigo_conta);
			criando_conta.setInt(2, codigo);
			criando_conta.setFloat(3, 0.0f);
			criando_conta.setFloat(4, 0.0f);
			criando_conta.setFloat(5, 0.0f); // Para o saldo total. Revise
			criando_conta.execute();
			System.out.println("\nConta criada!\n");
			Util.Pausar(2);
			verifica_cliente = "SELECT* FROM cliente WHERE id = " + codigo;
			cliente = conexao.createStatement().executeQuery(verifica_cliente);
			if (cliente.next()) {
				System.out.println("Dados da conta:" + "\n    n° identificador: " + codigo_conta + "\n    Titular: "
						+ cliente.getString("nome") + "\n    Saldo: " + Util.doubleParaDinheiro(0.0) + "\n    Limite: "
						+ Util.doubleParaDinheiro(0.0));
			}
			Util.Pausar(5);
			Banco.menu();
		} catch (SQLException e) {
			System.out.println("Cliente não encontrado.");
			Util.Pausar(3);
			Banco.menu();
		}
	}
	
	/**
	 * Método para efetuar saques.
	 * 
	 */
	public void sacar() {
		conexao = Banco.Conectando();
		System.out.println("Informe o número da conta: ");
		int id_conta = 0;
		try {
			id_conta = Integer.parseInt(sc.nextLine());

		} catch (Exception e) {
			System.out.println("O número da conta é UM NÚMERO.");
			Util.Pausar(2);
			Banco.menu();
		}
		String selecao = "SELECT* FROM conta where id = " + id_conta;
		try {
			ResultSet conta = conexao.createStatement().executeQuery(selecao);
			if (conta.next()) {
				saldo = conta.getFloat("saldo");
				limite = conta.getFloat("limite");
				saldo_total = saldo + limite;
				System.out.println("O saldo total da conta '" + id_conta + "' é: "
						+ Util.doubleParaDinheiro((double) saldo_total) + ". Qual valor deseja sacar: ");
				float valor = 0;
				try {
					valor = Float.parseFloat(sc.nextLine());
					if (valor > 0 && valor <= saldo_total) {
						if (saldo >= valor) {
							saldo -= valor;
						} else {
							valor -= saldo;
							limite -= valor;
							saldo = 0.0f;
						}
						saldo_total = saldo + limite;
						String atualiza_saldo = "UPDATE conta SET saldo = " + saldo + " where id = " + id_conta;
						String atualiza_limite = "UPDATE conta SET limite = " + limite + " where id = " + id_conta;
						String atualiza_total = "UPDATE conta SET saldo_total = " + saldo_total + " where id = "
								+ id_conta;
						PreparedStatement atualizando;
						try {
							atualizando = conexao.prepareStatement(atualiza_saldo);
							atualizando.execute();
							atualizando = conexao.prepareStatement(atualiza_limite);
							atualizando.execute();
							atualizando = conexao.prepareStatement(atualiza_total);
							atualizando.execute();
						} catch (SQLException e) {
							System.out.println("Não atualizou.");// TODO: handle exception
						}
						System.out.println("Aguarde enquanto a opreção está sendo processada...");
						Util.Pausar(4);
						System.out.println("Saque efetuado com sucesso.\nDados da conta '" + id_conta + "'"
								+ "\n    Novo saldo: " + Util.doubleParaDinheiro((double) saldo) + "\n    novo limite: "
								+ Util.doubleParaDinheiro((double) limite) + "\n    novo saldo total: "
								+ Util.doubleParaDinheiro((double) saldo_total) + "\n\n");
						Util.Pausar(5);
						Banco.menu();
					} else {
						System.out.println("Erro ao realizar o saque.");
					}
				} catch (Exception e) {
					System.out.println("Valor não reconhecido.");
					Util.Pausar(2);
					Banco.menu();
				}
			}
		} catch (Exception e) {
			System.out.println("Dane-se cara.");
		}
	}
	
	/**
	 * Método para efetuar depósitos.
	 * 
	 */
	public void depositar() {
		conexao = Banco.Conectando();
		System.out.println("Informe o número da conta: ");
		int id_conta = 0;
		String selecao;
		ResultSet conta = null;
		try {
			id_conta = Integer.parseInt(sc.nextLine());
			selecao = "SELECT* FROM conta where id = " + id_conta;
			conta = conexao.createStatement().executeQuery(selecao);
			if (conta.next()) {
				saldo = conta.getFloat("saldo");
				limite = conta.getFloat("limite");
				saldo_total = saldo + limite;
				System.out.println("O saldo atual da conta '" + id_conta + "' é: " + Util.doubleParaDinheiro((double) saldo) + ". Qual valor deseja depositar: ");
				float valor = 0;
				try {
					valor = Float.parseFloat(sc.nextLine());
					saldo = saldo + valor;
					saldo_total = saldo + limite;
					String atualiza_saldo = "UPDATE conta SET saldo = " + saldo + "where id = " + id_conta;
					PreparedStatement atualizando = conexao.prepareStatement(atualiza_saldo);
					atualizando.execute();
					atualiza_saldo = "UPDATE conta SET saldo_total = " + saldo_total + "where id = " + id_conta;
					atualizando = conexao.prepareStatement(atualiza_saldo);
					atualizando.execute();
					System.out.println("Depósito realizado!");
					Util.Pausar(3);
					Banco.menu();
				}catch (NumberFormatException depositoFormat) {
					System.out.println("Esse valor não é válido.\n" + depositoFormat);
					Util.Pausar(2);
					depositar();
				}
			}else if(!conta.next()) {
				System.out.println("Conta não encontrada.\n");
			}
		}catch (NumberFormatException numFormat) {
			System.out.println("Não foi possível encontrar a conta.\n" + numFormat);
		}catch (SQLException eSQL) {
			System.out.println("Conta não encontrada.\n" + eSQL);// TODO: handle exception
		}
	}
					
	/**
	 * Método para efetuar transferências.
	 * 
	 */
	public void transferir() {
		conexao = Banco.Conectando();
		int conta_origem = 0;
		int conta_destino = 0;
		try {
			System.out.println("Informe a conta de origem: ");
			conta_origem = Integer.parseInt(sc.nextLine());
			System.out.println("Informe a conta de destino: ");
			conta_destino = Integer.parseInt(sc.nextLine());
			try {
				ResultSet seleciona_origem = conexao.createStatement().executeQuery("SELECT* FROM conta where id = " + conta_origem);
				ResultSet seleciona_destino = conexao.createStatement().executeQuery("SELECT* FROM conta where id = " + conta_destino);
				if(seleciona_destino.next() && seleciona_origem.next()) {
					saldo = seleciona_origem.getFloat("saldo");
					limite = seleciona_origem.getFloat("limite");
					saldo_total = seleciona_origem.getFloat("saldo_total");
					System.out.println("O saldo da conta '" + conta_origem + "' é: " + Util.doubleParaDinheiro((double) saldo_total) +
											"\nQuanto você deseja transferir para '"+ conta_destino + "'?");
					float valor = 0;
					try {
						valor = Float.parseFloat(sc.nextLine());
						//float transferencia = valor;
						if (valor > 0 && valor <= saldo_total) {
							String atualiza_saldo;
							String atualiza_limite;
							String atualiza_total;
							PreparedStatement atualizando;
							if (saldo >= valor) {
								saldo = saldo - valor;
								saldo_total = saldo + limite;
								atualiza_saldo = "UPDATE conta SET saldo = " + saldo + " where id = " + conta_origem;
								atualizando = conexao.prepareStatement(atualiza_saldo);
								atualizando.execute();
								atualiza_total = "UPDATE conta SET saldo_total = " + saldo_total + " where id = " + conta_origem;
								atualizando = conexao.prepareStatement(atualiza_total);
								atualizando.execute();
							}else if (saldo < valor){
								saldo = saldo - valor;
								limite = limite + saldo;
								saldo = 0.0f;
								saldo_total = limite;
								atualiza_saldo = "UPDATE conta SET saldo = 0 where id = " + conta_origem;
								atualizando = conexao.prepareStatement(atualiza_saldo);
								atualizando.execute();
								atualiza_limite = "UPDATE conta SET limite = " + limite + " where id = " + conta_origem;
								atualizando = conexao.prepareStatement(atualiza_limite);
								atualizando.execute();
								atualiza_total = "UPDATE conta SET saldo_total = " + saldo_total + " where id = " + conta_origem;
								atualizando = conexao.prepareStatement(atualiza_total);
								atualizando.execute();
							}
							saldo = seleciona_destino.getFloat("saldo");
							limite = seleciona_destino.getFloat("limite");
							atualiza_saldo = "UPDATE conta SET saldo = " + (saldo + valor) + " where id = " + conta_destino;
							atualizando = conexao.prepareStatement(atualiza_saldo);
							atualizando.execute();
							saldo_total = saldo + valor + limite;
							atualiza_total = "UPDATE conta SET saldo_total = " + saldo_total + " where id = " + conta_destino;
							atualizando = conexao.prepareStatement(atualiza_total);
							atualizando.execute();
							
							System.out.println("Transferência realizada!");
							Util.Pausar(5);
							Banco.menu();
												
						}
					}catch (NumberFormatException e) {
						System.out.println("Valor incorreto.");
						Util.Pausar(3);
						Banco.menu();
					}
				}
			}catch (NumberFormatException e) {
			System.out.println("Contas inexistentes.");
			Util.Pausar(3);
			Banco.menu();
		}
		
		}catch (SQLException e) {
			// TODO: handle exception
		}
	}
	/**
	 * Método para lstas dados bancários das contas.
	 * 
	 */
	public void listar() {
		conexao = Banco.Conectando();
		try {
			ResultSet contas = conexao.createStatement().executeQuery("SELECT* FROM conta");
			System.out.println("\nLista de contas:\n ");
			while (contas.next()) {
				ResultSet cliente = conexao.createStatement().executeQuery("SELECT nome FROM cliente where id = " + contas.getInt("id_cliente"));
				if(cliente.next()) {
				System.out.println(
						" Conta: " + contas.getInt("id") + 
						"\n    Titular: " + cliente.getString(1) +
						"\n    Saldo:   " + Util.doubleParaDinheiro((double) contas.getFloat("saldo")) +  
						"\n    Limite:  " + Util.doubleParaDinheiro((double)contas.getFloat("limite")) +  
						"\n    Total:   " + Util.doubleParaDinheiro((double)contas.getFloat("saldo_total"))
				
						); 
				System.out.println("---------------------------");
				Util.Pausar(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fim da lista.");
		Util.Pausar(15);
		Banco.menu();
	}
}
