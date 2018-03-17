package rech.haeser.daniel.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.exceptions.JWTVerificationException;

/**
 * @author daniel.rech
 */
@Provider
public class JWTVerificationExceptionMapper implements ExceptionMapper<JWTVerificationException> {
    @Override
    public Response toResponse(final JWTVerificationException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
