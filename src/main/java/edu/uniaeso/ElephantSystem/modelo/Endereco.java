package edu.uniaeso.ElephantSystem.modelo;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {
	
	
	private String cep;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	
	private String numero;
	private String complemento;
	
	
	public Endereco() {
	}

    public Endereco(String CEP, String logradouro, String Bairro, String cidade, String estado, String num, String complemento) {
        this.cep = CEP;
        this.logradouro = logradouro;
        this.bairro = Bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = num;
        this.complemento = complemento;
    }

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	
}
