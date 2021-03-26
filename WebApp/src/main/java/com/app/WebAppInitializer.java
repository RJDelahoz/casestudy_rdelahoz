package com.app;

import com.app.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(WebConfig.class);
		//		create a dispatcher servlet
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);

		//		register the dispatcher servlet
		ServletRegistration.Dynamic registration = servletContext.addServlet(
				"rootDispatcher", dispatcherServlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
	}
}
