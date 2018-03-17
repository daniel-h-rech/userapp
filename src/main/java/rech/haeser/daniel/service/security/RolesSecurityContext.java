package rech.haeser.daniel.service.security;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.SecurityContext;

/**
 * @author daniel.rech
 */
public class RolesSecurityContext implements SecurityContext {

    private final SecurityContext securityContext;

    private final Set<String> userRolesSet;

    public RolesSecurityContext(final SecurityContext theSecurityContext, final String[] userRoles) {
        securityContext = theSecurityContext;
        userRolesSet = new HashSet<>(Arrays.asList(userRoles));
    }

    @Override
    public Principal getUserPrincipal() {
        return securityContext.getUserPrincipal();
    }

    @Override
    public boolean isUserInRole(final String role) {
        return userRolesSet.contains(role);
    }

    @Override
    public boolean isSecure() {
        return securityContext.isSecure();
    }

    @Override
    public String getAuthenticationScheme() {
        return securityContext.getAuthenticationScheme();
    }
}
