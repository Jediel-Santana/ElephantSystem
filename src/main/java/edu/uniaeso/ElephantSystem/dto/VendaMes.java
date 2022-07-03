package edu.uniaeso.ElephantSystem.dto;

public class VendaMes {

	private String mes;
	private Integer total;

	public VendaMes() {
	}

	public VendaMes(String mes, Integer total) {
		this.mes = mes;
		this.total = total;
	}

	public final String getMes() {
		return mes;
	}

	public final Integer getTotal() {
		return total;
	}

}
