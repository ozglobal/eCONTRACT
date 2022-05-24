package com.oz.unitel.security;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SuppressWarnings("deprecation")
public class AuthenticationProviderImpl implements AuthenticationProvider {
	
	@Autowired
	UserDetailsServiceImpl userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	final static Logger logger = Logger.getLogger(AuthenticationProviderImpl.class);
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String username = authentication.getName();
        String raw_password = (String) authentication.getCredentials();

        JUserDetails user;
        Collection<? extends GrantedAuthority> authorities;

        try {
        	user = userService.loadUserByUsername(username);
            if (!passwordEncoder.isPasswordValid(user.getPassword(), raw_password, null) ) throw new BadCredentialsException("The passwords do not match.");
            
            authorities = user.getAuthorities();
        } catch(UsernameNotFoundException e) {
            logger.info(e.toString());
            throw new UsernameNotFoundException(e.getMessage());
        } catch(BadCredentialsException e) {
            logger.info(e.toString());
            throw new BadCredentialsException(e.getMessage());
        } catch(Exception e) {
            logger.info(e.toString());
            throw new RuntimeException(e.getMessage());
        }

        return new UsernamePasswordAuthenticationToken(user, raw_password, authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		
		return true;
	}

}
