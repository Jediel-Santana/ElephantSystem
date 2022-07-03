package edu.uniaeso.ElephantSystem.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.uniaeso.ElephantSystem.modelo.Usuario;
import edu.uniaeso.ElephantSystem.repository.Grupos;
import edu.uniaeso.ElephantSystem.security.UsuarioSistema;
import edu.uniaeso.ElephantSystem.service.CadastroUsuarioService;

@Controller
@RequestMapping("/api/")
public class UsuarioController {
	
	
	@Autowired 
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private Grupos grupos;
	
	@GetMapping("/public/usuarios/novo")
	public ModelAndView registrar(Usuario usuario, @AuthenticationPrincipal UsuarioSistema usuarioLogado) {
		ModelAndView mv = new ModelAndView();
		if(Objects.nonNull(usuarioLogado)) {
			mv.setViewName("redirect:/api/public/home");
			return mv;
		}
		mv.setViewName("Registrar");
		return mv;
	}
	
	@GetMapping(value = "/usuarios/colaborador/novo")
	public ModelAndView novoColaborador(Usuario usuario) {
		return getMVModalColaborador(usuario);
	}
	
	@GetMapping(value = "/usuarios/colaborador/{idUsuario}")
	public ModelAndView editarColaborador(@PathVariable("idUsuario") Usuario usuario) {
		return getMVModalColaborador(usuario);
	}

	@PostMapping(value = {"/usuarios/colaborador/novo", "/usuarios/colaborador/{\\d+}"})
	public ModelAndView novoColaborador(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		ModelAndView mv = getMVModalColaborador(usuario);
		if(result.hasErrors()) {
			return mv;
		}
		
		
		cadastroUsuarioService.salvar(usuario);
		String msg = usuario.isNovo() ? "Usuário cadastrado com sucesso!" : "Usuário atualizado com sucesso!";
		mv.addObject("mensagem", msg);
		
		
		return mv;
	}
	
	@PostMapping("/public/usuarios/novo")
	public ModelAndView novo(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return registrar(usuario, null);
		}
		ModelAndView mv = new ModelAndView("redirect:/api/public/usuarios/novo");
		
		cadastroUsuarioService.salvar(usuario);
		
		String msg = usuario.isNovo() ? "Usuário cadastrado com sucesso!" : "Usuário atualizado com sucesso!";
		attributes.addFlashAttribute("mensagem", msg);
		return mv;
	}
	
	@GetMapping("/public/usuarios/esqueciSenha")
	public String esqueceuSenha() {
		return "EsqueceuSenha";
	}
	
	
	private ModelAndView getMVModalColaborador(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuarioModal :: modal-content");
		mv.addObject("usuario", usuario);
		mv.addObject("grupos", grupos.colaboradores());
		return mv;
	}
	
}
