package com.app.controller;

import com.app.model.Credential;
import com.app.model.User;
import com.app.repo.UserRepository;
import com.app.service.PostService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Controller
public class TestController {

	@RequestMapping("/")
	public String getLandingPage() {
		return "index";
	}

	@RequestMapping("/blog")
	public String getBlogPage(Model model) {
		model.addAttribute("message", "BLOG PAGE");
		return "blog";
	}

	@RequestMapping("/support")
	public String getSupportPage(Model model) {
		model.addAttribute("message", "SUPPORT PAGE");
		return "support";
	}

	@RequestMapping("/login")
	public String getLoginPage(Model model) {
		model.addAttribute("message", "LOGIN PAGE");

		return "login";
	}

	@RequestMapping(value = "/User")
	public void getUser(@RequestParam("email") String email){
		// do whatever with email
	}
}
