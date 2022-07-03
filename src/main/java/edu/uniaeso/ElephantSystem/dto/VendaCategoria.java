package edu.uniaeso.ElephantSystem.dto;

import java.math.BigDecimal;

public class VendaCategoria {

	private String categoria;
	private BigDecimal total;

	public VendaCategoria() {
	}

	public VendaCategoria(String categoria, BigDecimal total) {
		this.categoria = categoria;
		this.total = total;
	}

	public String getCategoria() {
		return categoria;
	}

	public BigDecimal getTotal() {
		return total;
	}

}
