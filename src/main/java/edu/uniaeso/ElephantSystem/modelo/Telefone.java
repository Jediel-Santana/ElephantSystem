package edu.uniaeso.ElephantSystem.modelo;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class Telefone {
	
	private String ddd;
	private String numero;
	
	public Telefone() {
	}
	
	public Telefone(String ddd, String numero) {
		super();
		this.ddd = ddd;
		this.numero = numero;
	}
	public String getDdd() {
		return ddd;
	}
	public String getNumero() {
		return numero;
	}
	
	
	@Override
	public String toString() {
		
		return '[' + "(" + this.ddd + ") " + this.numero + ']';
		
	}
	
}
