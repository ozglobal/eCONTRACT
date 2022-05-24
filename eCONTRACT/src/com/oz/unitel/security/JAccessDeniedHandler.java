package com.oz.unitel.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

public class JAccessDeniedHandler implements AccessDeniedHandler{
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
    
	private String errorPage;
	 
	public JAccessDeniedHandler() {
	}
 
	public JAccessDeniedHandler(String errorPage) {
		this.errorPage = errorPage;
	}
 
	public String getErrorPage() {
		return errorPage;
	}
 
	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		//do some business logic, then redirect to errorPage url
		redirectStrategy.sendRedirect(request, response, errorPage);
		
	}

}
