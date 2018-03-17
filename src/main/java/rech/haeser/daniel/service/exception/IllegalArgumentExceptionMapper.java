package rech.haeser.daniel.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author daniel.rech
 */
@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(final IllegalArgumentException exception) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
