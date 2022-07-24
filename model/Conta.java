package model;

import utilitario.Util;

public class Conta {
	private static int codigo = 1001;
	
	private int numero;
	private Cliente cliente;
	private double saldo, limite, saldo_total = 0.0;
	
	
	public Conta(Cliente cliente) {
		super();
		this.numero = codigo;
		codigo++;
		this.cliente = cliente;
		this.saldo = getSaldo();
		this.limite = getLimite();
		this.atualizaSaldoTotal();
	}
	private void atualizaSaldoTotal() {
		this.saldo_total = this.getSaldo() + this.getLimite();
	}
		
	@Override
	public String toString() {
		return "conta: " + this.getNumero() + 
				"\ncliente: " + this.getCliente().getNome() + 
				"\nsaldo: " + Util.doubleParaDinheiro(this.getSaldo()) + 
				"\nlimite: " + Util.doubleParaDinheiro(this.getLimite()) +
				"\nsaldo total: " + this.getSaldo_total();
	}
	public void depositar (Double valor){
		if (valor > 0) {
			this.saldo = this.getSaldo() + valor;
			this.atualizaSaldoTotal();
			System.out.println("Depósito efetuado com sucesso.");
		}else {
			System.out.println("Erro ao efeturar o depósito.");
		}
	}
	public void sacar(Double valor) {
		if (valor > 0 && valor <= this.getSaldo_total()) {
			if (this.getSaldo()>= valor) {
				this.setSaldo(this.getSaldo() - valor);
				this.atualizaSaldoTotal();
			}else {
				valor = valor - this.getSaldo();
				this.setLimite(this.getLimite() - valor);
				this.setSaldo(0.0);
				this.atualizaSaldoTotal();
			}
			System.out.println("Saque efetuado com sucesso.");
		}else {
			System.out.println("Erro ao realizar o saque.");
		}
	}
	public void transferir (Conta destino, double valor) {
		if (valor > 0 && valor <= this.getSaldo_total()) {
			if (this.getSaldo() > valor) {
				this.setSaldo(this.getSaldo() - valor);
				destino.setSaldo(destino.getSaldo() + valor);
				this.atualizaSaldoTotal();
				destino.atualizaSaldoTotal();
			}else {
				valor = this.getSaldo() - valor;
				this.setLimite(this.getLimite() - valor);
				this.setSaldo(0);
				destino.setSaldo(destino.getSaldo() );
			}
			System.out.println("Transferência realizada.");

		}else {
			System.out.println("Transferência não realizada.");
		}
	}
	
	public static int getCodigo() {
		return codigo;
	}
	public static void setCodigo(int codigo) {
		Conta.codigo = codigo;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public double getLimite() {
		return limite;
	}
	public void setLimite(double limite) {
		this.limite = limite;
		this.atualizaSaldoTotal();
	}
	public double getSaldo_total() {
		return saldo_total;
	}
	public void setSaldo_total(double saldo_total) {
		this.saldo_total = saldo_total;
	}
}
