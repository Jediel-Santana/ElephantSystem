package edu.uniaeso.ElephantSystem.repository.helper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.uniaeso.ElephantSystem.dto.VendaCategoria;
import edu.uniaeso.ElephantSystem.dto.VendaMes;
import edu.uniaeso.ElephantSystem.modelo.Usuario;
import edu.uniaeso.ElephantSystem.modelo.Venda;
import edu.uniaeso.ElephantSystem.repository.filter.VendaFilter;

public interface VendasQueries {
	
	List<Venda> porUsuario(Usuario usuario);
	
	Venda buscarComItens(Long codigo);

	Long total();
	
	BigDecimal valorTicketMedio();
	
	List<VendaMes> totalNoMes();
	
	List<VendaCategoria> totalPorCategoria();
	
	Page<Venda> filtro(VendaFilter filter, Pageable pageable);
	
}
