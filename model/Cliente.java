package model;

import java.time.LocalDate;
import java.util.Date;

import utilitario.Util;

public class Cliente {
	private static int contador = 1;
	private int codigo;
	private String nome, email, cpf;
	private LocalDate data_nascimento; 
	private LocalDate data_cadastro;
		


	public Cliente(String nome, String email, String cpf, LocalDate data_nascimento) {
		super();
		this.codigo = contador;
		contador++;
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.data_nascimento = data_nascimento;
	//	this.data_cadastro  = Date.parse(LocalDate.now());
		data_cadastro = LocalDate.now();
	}

	public static int getContador() {
		return contador;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(LocalDate data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
	public LocalDate getDataCadastro() {
		return this.data_cadastro;
	}
	
	public String toString() {
		return "nome: " + this.getNome() +
				"\nemail: " + this.getEmail() +
				"\nCPF: " + this.getCpf() +
				"\nData de Nascimento: " + Util.DateParaString(this.getData_nascimento()) +
				"\nData de Cadastro: " + Util.LocalDateParaString(data_cadastro);
	}

}
