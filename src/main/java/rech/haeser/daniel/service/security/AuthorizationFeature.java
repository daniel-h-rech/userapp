package rech.haeser.daniel.service.security;

import javax.ws.rs.ext.Provider;

/**
 * Subclassed and annotated with @Provider so that RESTEasy's scanner will pick it up
 * and enable @RolesAllowed verification.
 *
 * @author daniel.rech
 */
@Provider
public class AuthorizationFeature extends org.jboss.resteasy.plugins.interceptors.RoleBasedSecurityFeature {
}
