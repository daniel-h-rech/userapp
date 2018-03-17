package rech.haeser.daniel.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.mongodb.MongoException;

/**
 * @author daniel.rech
 */
@Provider
public class MongoExceptionMapper implements ExceptionMapper<MongoException> {
    @Override
    public Response toResponse(final MongoException exception) {
        return Response.serverError().build();
    }
}
