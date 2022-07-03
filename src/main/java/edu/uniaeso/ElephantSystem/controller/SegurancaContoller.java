package edu.uniaeso.ElephantSystem.controller;

import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SegurancaContoller {

	
	@GetMapping("/login")
	public String login(@AuthenticationPrincipal User user) {
		if(Objects.nonNull(user))
			return "redirect:/api/public/home";
		return "Login";
	}
	
}
