package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Util;
import view.Banco;

public class Conta {
	private int codigo;
	
	private int codigo_conta;
	//private Cliente cliente;
	private double saldo, limite, saldo_total = 0.0;
	private Connection conexao;

	public void CriandoConta(int id_cliente) {
		conexao = Banco.Conectando();
		String cria_conta = "INSERT INTO conta (id, id_cliente, saldo, limite, saldo_total) VALUES (?,?,?,?,?)";
		String verifica_cliente = "SELECT* FROM cliente WHERE id = " + id_cliente;
		String id_ultima_conta = "SELECT max(id) FROM conta";
		try {
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
				}else {
					codigo_conta = x + 1;
				}
			}
			criando_conta.setInt(1, codigo_conta);
			criando_conta.setInt(2, codigo);
			criando_conta.setFloat(3, 0.0f);
			criando_conta.setFloat(4, 0.0f);
			criando_conta.setFloat(5, 0.0f); //Para o saldo total. Revise
			criando_conta.execute();
			System.out.println("\nConta criada.");
			verifica_cliente = "SELECT* FROM cliente WHERE id = " + codigo;
			cliente = conexao.createStatement().executeQuery(verifica_cliente);
			if (cliente.next()) {
				System.out.println("Dados da conta:"
						+ "\n    n° identificador: " + codigo_conta 
						+ "\n    Titular: " + cliente.getString("nome")
						+ "\n    Saldo: " + Util.doubleParaDinheiro(0.0)
						+ "\n    Limite: " + Util.doubleParaDinheiro(0.0));
			}
			Util.Pausar(2);
			Banco.menu();
		} catch (SQLException e) {
			System.out.println("Cliente não encontrado.");
			Util.Pausar(3);
			Banco.menu();
		}
	}
}