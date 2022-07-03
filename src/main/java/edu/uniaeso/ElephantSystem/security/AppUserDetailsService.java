package edu.uniaeso.ElephantSystem.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.uniaeso.ElephantSystem.modelo.Usuario;
import edu.uniaeso.ElephantSystem.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private Usuarios usuarios;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuarioBuscado = usuarios.porEmailEAtivo(username);
		Usuario usuario = usuarioBuscado
				.orElseThrow(() -> new UsernameNotFoundException("O E-mail e/ou Senha está inválido"));

		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		List<String> permissoes = usuarios.permissoes(usuario);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));

		return authorities;
	}

}
