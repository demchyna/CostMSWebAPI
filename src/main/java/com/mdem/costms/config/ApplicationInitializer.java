package com.mdem.costms.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { WebConfig.class, DataConfig.class, SecurityConfig.class, SwaggerConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
