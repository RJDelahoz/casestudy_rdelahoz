package com.app.controller;

import com.app.model.Property;
import com.app.model.User;
import com.app.service.CredentialService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
								   HttpServletRequest request,
								   @RequestParam("page") Optional<Integer> page) {
		model.addAttribute("message",
				HelperClass.greetingHelper(request.getUserPrincipal().getName()));

		List<User> users  = userService.getAllUsers();

		int currentPage = page.orElse(1);

		Page<User> userPage = userService.getUsersPaginated(PageRequest
				.of(currentPage-1, 2), users);

		model.addAttribute("userPage", userPage);

		int totalPages = userPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "admin";
	}

	@RequestMapping(value="/delete")
	public String deleteUserHandler(@RequestParam("id") long id) {
		credentialService.deleteCredential(id);
		return "redirect:/admin";
	}


}
