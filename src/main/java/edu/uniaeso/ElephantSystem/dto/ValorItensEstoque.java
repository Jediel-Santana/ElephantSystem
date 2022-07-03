package edu.uniaeso.ElephantSystem.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ValorItensEstoque {

	private BigDecimal valor = BigDecimal.ZERO;
	private Long totalItens = 0l;

	public ValorItensEstoque() {
	}

	public ValorItensEstoque(BigDecimal valor, Long totalItens) {
		setValor(valor);
		setTotalItens(totalItens);
	}

	public final BigDecimal getValor() {
		return valor;
	}

	public final void setValor(BigDecimal valor) {
		if (Objects.nonNull(valor)) {
			this.valor = valor;
		}
	}

	public final Long getTotalItens() {
		return totalItens;
	}

	public final void setTotalItens(Long totalItens) {
		if (Objects.nonNull(totalItens)) {
			this.totalItens = totalItens;
		}
	}

}
