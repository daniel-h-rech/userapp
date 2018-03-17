package rech.haeser.daniel.service.security;

import static rech.haeser.daniel.service.security.JWTGeneratorFilter.SECRET;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import rech.haeser.daniel.service.ResourcePath;

/**
 * @author daniel.rech
 */
@Provider
@PreMatching
public class AuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(final ContainerRequestContext requestContext) {

        final String path = requestContext.getUriInfo().getPath();
        if (!path.equals(ResourcePath.LOGIN)) {

            final String authorization = requestContext.getHeaders().getFirst("Authorization");
            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
            final String jwt = authorization.split(" ", 2)[1];

            final DecodedJWT decodedJWT = verifySignature(jwt);

            requestContext.setSecurityContext(new RolesSecurityContext(requestContext.getSecurityContext(), extractPermissions(decodedJWT)));
        }
    }

    private DecodedJWT verifySignature(final String jwt) {
        final Algorithm algorithm;
        try {
            algorithm = Algorithm.HMAC512(SECRET);
        } catch (UnsupportedEncodingException e) {
            throw new WebApplicationException();
        }
        return JWT.require(algorithm).build().verify(jwt);
    }

    private String[] extractPermissions(final DecodedJWT decodedJWT) {
        final Claim permissionsClaim = decodedJWT.getClaim("permissions");
        return permissionsClaim.asArray(String.class);
    }
}
