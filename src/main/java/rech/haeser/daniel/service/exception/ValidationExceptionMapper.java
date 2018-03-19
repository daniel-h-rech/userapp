package rech.haeser.daniel.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.mongodb.morphia.query.ValidationException;

/**
 * @author daniel.rech
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(final ValidationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
