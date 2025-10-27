package org.example.study_group_service.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Root context (services, repo, data)
        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        rootCtx.register(RootConfig.class);

        // Web context (controllers, view resolvers)
        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        webCtx.register(WebConfig.class);
        webCtx.setParent(rootCtx);

        // DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webCtx);
        ServletRegistration.Dynamic reg = servletContext.addServlet("dispatcher", dispatcherServlet);
        reg.setLoadOnStartup(1);
        reg.addMapping("/");

        // Optional: add spring security filter chain via DelegatingFilterProxy in code or web.xml
        jakarta.servlet.FilterRegistration.Dynamic securityFilter =
                servletContext.addFilter("springSecurityFilterChain", new org.springframework.web.filter.DelegatingFilterProxy("springSecurityFilterChain"));
        securityFilter.addMappingForUrlPatterns(null, false, "/*");
    }
}
