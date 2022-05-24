package com.oz.unitel.security;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JUserDetails implements UserDetails{
	
	private static final long serialVersionUID = -2400879644978787510L;
	private String userName;  
    private String password;
    private String user_id;
    private ArrayList<GrantedAuthority> authorities;
    private String fullName;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
    
	public JUserDetails(String userName, String password, ArrayList<GrantedAuthority> authorities){  
         this.userName = userName;  
         this.password = password;
         this.authorities = authorities;  
    }  	
	
    @Override  
    public ArrayList<? extends GrantedAuthority> getAuthorities() {  
         return authorities;  
    }  
    
    @Override  
    public String getPassword() {  
         return password;  
    }  
    
    @Override  
    public String getUsername() {  
         return userName;  
    }  
    
    public String getFullName() {
    	return fullName;
    }
    public void setFullName(String fullName){
    	this.fullName = fullName;
    } 
    
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
