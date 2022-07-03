package edu.uniaeso.ElephantSystem.service.event.venda;

import edu.uniaeso.ElephantSystem.modelo.Venda;

public class VendaEvent {
	
	private Venda venda;
	
	public VendaEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
}
