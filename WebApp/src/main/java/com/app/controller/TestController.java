package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping("/")
	public String getHomePage(Model model) {
		model.addAttribute("title", "TEST");
		model.addAttribute("message", "DUMMY TEXT!");
		return "index";
	}


}
