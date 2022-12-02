package net.javaguides.springboot.web;

import net.javaguides.springboot.model.Recipe;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.RecipeServices;
import net.javaguides.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

	@Autowired
	RecipeServices service;

	@Autowired
	UserService UserService;
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}

}
