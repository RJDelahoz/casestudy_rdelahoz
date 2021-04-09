package com.app.controller;

import com.app.model.User;
import com.app.service.CredentialService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {

	private final UserService userService;
	private final CredentialService credentialService;

	@Autowired
	public AdminController(UserService userService, CredentialService credentialService) {
		this.userService = userService;
		this.credentialService = credentialService;
	}

	@RequestMapping("/admin")
	public String adminPageHandler(Model model,
								   HttpServletRequest request) {
		model.addAttribute("message",
				HelperClass.greetingHelper(request.getUserPrincipal().getName()));

		List<User> userList = userService.getAllUsers();
		model.addAttribute("userListBean", userList);
		return "admin";
	}

	@RequestMapping(value="/delete")
	public String deleteUserHandler(@RequestParam("id") long id) {
		credentialService.deleteCredential(id);

		return "redirect:/admin";
	}


}
