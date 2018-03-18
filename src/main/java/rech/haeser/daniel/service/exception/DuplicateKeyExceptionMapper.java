package rech.haeser.daniel.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.mongodb.DuplicateKeyException;

/**
 * @author daniel.rech
 */
@Provider
public class DuplicateKeyExceptionMapper implements ExceptionMapper<DuplicateKeyException> {
    @Override
    public Response toResponse(final DuplicateKeyException exception) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
