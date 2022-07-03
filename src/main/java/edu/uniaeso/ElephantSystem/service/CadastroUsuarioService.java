package edu.uniaeso.ElephantSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uniaeso.ElephantSystem.modelo.Grupo;
import edu.uniaeso.ElephantSystem.modelo.Usuario;
import edu.uniaeso.ElephantSystem.modelo.UsuarioGrupo;
import edu.uniaeso.ElephantSystem.repository.Usuarios;

@Service
public class CadastroUsuarioService {
	
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder enconder;
	
	@Transactional
	public void salvar(Usuario usuario) {
		Optional<Usuario> usuarioBuscado = usuarios.porEmail(usuario.getEmail());
		
		if(usuarioBuscado.isPresent() && usuarioBuscado.get().equals(usuario)) {
			Usuario usuarioBanco = usuarioBuscado.get();
			usuario.setSenha(usuarioBanco.getSenha());
			usuario.getPessoa().setIdUsuario(usuarioBanco.getId());
//			List<Grupo> gruposDTO = usuario.getGrupos();
			
//			List<UsuarioGrupo> usuarioGrupos = new ArrayList<>();
//			gruposDTO.stream().forEach(g -> {
//				if(usuarioBanco.getGrupos().stream().noneMatch(gb -> gb.equals(g))) {
//					usuarioBanco
//				}
//			});
			
//			for (UsuarioGrupo ug : usuarioBanco.getUsuarioGrupos()) {
//				if(grupos.stream().anyMatch(g -> g.equals(ug.getId().getGrupo()))) {
//					usuarioGrupos.add(ug);
//				}
//			}
//			
//			for (Grupo grupo : grupos) {
//				if(usuarioBanco.getUsuarioGrupos().stream().noneMatch(ug -> ug.getId().getGrupo().equals(grupo))) {
//					usuarioGrupos.add(new UsuarioGrupo(grupo, usuario));
//				}
//			}
//			
//			usuarioBanco.getUsuarioGrupos().stream().forEach(ug -> {
//				if(usuarioGrupos.stream().noneMatch(ug2 -> ug2.getId().getGrupo().equals(ug.getId().getGrupo()))) {
//					
//				}
//			});
			
//			usuario.setUsuarioGrupos(usuarioGrupos);
			
		} else if(usuario.isNovo() && usuario.getGrupos().isEmpty()) {
			Grupo grupoCliente = new Grupo();
			grupoCliente.setId(3l);
			usuario.adicionarGrupo(grupoCliente);
			usuario.setSenha(enconder.encode(usuario.getSenha()));
		}
		
		usuarios.save(usuario);
	}

}
