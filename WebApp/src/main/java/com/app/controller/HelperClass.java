package com.app.controller;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public class HelperClass {

	public static String[] getUsernameAndRole(HttpServletRequest request, Authentication authentication) {
		return new String[] {request.getUserPrincipal().getName(),
				authentication.getAuthorities().toString()};
	}

	public static String greetingHelper(String username) {
		int hour = java.time.LocalTime.now().getHour();
		String greeting;
		if(hour >= 18)
			greeting = String.format("Good evening, %s", username);
		else if (hour >= 12)
			greeting = String.format("Good afternoon, %s",  username);
		else
			greeting = String.format("Good morning, %s", username);

		return greeting;
	}
}
