package org.iglootools.commons.springsecurity;


import com.google.common.collect.Lists;
import org.springframework.security.authentication.TestingAuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class Authentications {
    private static String DEFAULT_ROLE_USER = "ROLE_USER";

    public static String principalToUsername(Object principal) {
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public static void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }

    public static void simulateAuthentication(String user, String password) {
        simulateAuthentication(user, password, false);
    }

    public static void simulateAuthentication(String user, String password,boolean createRealUsernamePasswordToken) {
        simulateAuthentication(user, password, createRealUsernamePasswordToken, DEFAULT_ROLE_USER);
    }

    public static void simulateAuthentication(String user, String password, boolean createRealUsernamePasswordToken, String role) {
        List<GrantedAuthority> authorities = Lists.<GrantedAuthority>newArrayList(new GrantedAuthorityImpl(role));
        Object principal = createRealUsernamePasswordToken
                ? new User(user,password, true, true, true, true, authorities)
                : user;
        Authentication auth = createRealUsernamePasswordToken
                ? new UsernamePasswordAuthenticationToken(principal, password)
                : new TestingAuthenticationToken(principal, password, authorities);
        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new TestingAuthenticationProvider().authenticate(auth));

        SecurityContextHolder.setContext(securityContext);
    }


}
