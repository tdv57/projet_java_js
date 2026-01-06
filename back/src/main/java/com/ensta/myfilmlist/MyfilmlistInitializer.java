package com.ensta.myfilmlist;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyfilmlistInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;  // No root configuration needed for this example
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { MyfilmlistConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };  // All requests are handled by this servlet
    }
}
