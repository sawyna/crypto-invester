package com.pointy.assignment.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Programmatic counterpart for web.xml
 */
public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext webAppContext = new AnnotationConfigWebApplicationContext();
        webAppContext.register(AppConfig.class);
        webAppContext.setServletContext(servletContext);

        servletContext.addListener(new ContextLoaderListener(webAppContext));
        ServletRegistration.Dynamic cxfServlet = servletContext
            .addServlet("CXFServlet", CXFServlet.class);
        cxfServlet.setLoadOnStartup(1);
        cxfServlet.addMapping("/api/*");
    }
}
