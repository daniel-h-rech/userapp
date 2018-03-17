package rech.haeser.daniel.service.security;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import rech.haeser.daniel.service.ResourcePath;

/**
 * @author daniel.rech
 */
@Provider
public class JWTGeneratorFilter implements ContainerResponseFilter {

    private static final int DEFAULT_EXPIRATION_TIME = 10;

    static final String SECRET = UUID.randomUUID().toString();

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {

        if (requestContext.getUriInfo().getPath().equals(ResourcePath.LOGIN)
                && responseContext.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {

            responseContext.setEntity(new TokenHolder(), null, MediaType.APPLICATION_JSON_TYPE);
            responseContext.setStatus(Response.Status.OK.getStatusCode());
        }
    }

    public static class TokenHolder {

        private final String token;

        public TokenHolder() {

            final Algorithm algorithm;
            try {
                algorithm = Algorithm.HMAC512(SECRET);
            } catch (UnsupportedEncodingException e) {
                throw new WebApplicationException();
            }

            final Instant utcInstant = ZonedDateTime
                    .now(ZoneOffset.UTC)
                    .plusMinutes(DEFAULT_EXPIRATION_TIME)
                    .toInstant();

            token = JWT
                    .create()
                    .withExpiresAt(Date.from(utcInstant))
                    .withArrayClaim("permissions", Permission.USER)
                    .sign(algorithm);
        }

        public String getToken() {
            return token;
        }
    }
}
