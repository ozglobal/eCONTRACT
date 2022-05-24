package com.oz.unitel.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.oz.unitel.utils.CustomGenericException;

public class JAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	final static Logger logger = Logger.getLogger(JAuthenticationSuccessHandler.class);
	 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }
 
    protected void handle(HttpServletRequest request, 
      HttpServletResponse response, Authentication authentication) throws IOException {
    	String userAgent = request.getHeader("user-agent");
        boolean isMobileAgent = false;
        if(userAgent.indexOf("Mobile") != -1 || userAgent.indexOf("Android") != -1){
        	isMobileAgent = true;
        }
    	
    	String targetUrl = determineTargetUrl(authentication, isMobileAgent);
    	JUserDetails user = (JUserDetails) authentication.getPrincipal();
    	
    	request.getSession().setAttribute("login_user_nm", user.getFullName());
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
       
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
 
    /** Builds the target URL according to the logic defined in the main class Javadoc. */
    protected String determineTargetUrl(Authentication authentication, boolean isMobileAgent) {
        boolean isTeller = false;
        boolean isManager = false;
        boolean isReporter = false;
        boolean isContrator = false;
        boolean isAdministrator = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
        	        	
            if (grantedAuthority.getAuthority().equals("ROLE_TELLER")) {
            	isTeller = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
            	isManager = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_REPORTER")) {
            	isReporter = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_CONTRACT")) {
            	isContrator = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
            	isAdministrator = true;
                break;
            }
        }
        
    	if (isTeller || isManager || isReporter || isContrator || isAdministrator) {
            if (isMobileAgent){
            	return "/m-index";
            }else {
            	return "/m-index";
            }
        } else {
            //throw new IllegalStateException();
        	//throw new CustomGenericException("Таньд нэвтрэх эрх байхгүй байна. Системийн админтай холбогдоно уу.");
        	
        	return "/login?missingrole=true";
        }
    }
 
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
    
}
