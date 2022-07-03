package edu.uniaeso.ElephantSystem.modelo;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.sun.istack.NotNull;

//@Entity
//@Table(name = "funcionarios")
public class Cliente {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long cpf;

	@NotNull
	private String nome;

	@Enumerated(EnumType.STRING)
	private Sexo sexo;

	@Embedded
	private Endereco endereco;

	private LocalDate dataNascimento;

	@Embedded
	private Telefone telefone;

	private String email;


	public Cliente(Long cpf, String nome, Sexo sexo, Endereco endereco, LocalDate dataNascimento, Telefone telefone,
			String email, String redeSocial) {
		this.nome = nome;
		this.sexo = sexo;
		this.endereco = endereco;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

}
