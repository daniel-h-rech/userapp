package rech.haeser.daniel.service;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import org.bson.types.ObjectId;

import io.swagger.annotations.Api;
import rech.haeser.daniel.controller.UserController;
import rech.haeser.daniel.model.User;
import rech.haeser.daniel.service.security.Permission;

/**
 * @author daniel.rech
 */
@Path(ResourcePath.USER)
@Produces(MediaType.APPLICATION_JSON)
@Api
public class UserService {

    private static final String DEFAULT_MAX_RESULTS = "" + 100;

    private static final String ORDERBY_REGEX = "[-?\\d\\w]+(,[-?\\d\\w]+)*";

    @EJB
    private UserController controller;

    @POST
    @RolesAllowed(Permission.USER_CREATE)
    public User create(@Valid @NotNull final User user) {
        return controller.create(user);
    }

    @GET
    @Path("{id}")
    @RolesAllowed(Permission.USER_RETRIEVE)
    public User retrieve(@PathParam("id") final String id) {
        final User user = controller.retrieve(new ObjectId(id));
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    @PUT
    @RolesAllowed(Permission.USER_UPDATE)
    public User update(@Valid @NotNull final User user) {
        controller.update(user);
        return user;
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed(Permission.USER_DELETE)
    public void delete(@PathParam("id") final String id) {
        controller.delete(new ObjectId(id));
    }

    @GET
    @RolesAllowed(Permission.USER_RETRIEVE)
    public StreamingOutput query(
            @QueryParam("filter")                                                    final String filter, // TODO validate with @Pattern
            @QueryParam("orderby") @Valid @Pattern(regexp = ORDERBY_REGEX)           final String orderBy,
            @QueryParam("offset")  @Valid @Min(0) @DefaultValue("0")                 final int offset,
            @QueryParam("limit")   @Valid @Min(1) @DefaultValue(DEFAULT_MAX_RESULTS) final int limit) {

        // Using a StreamingOutput to reduce the memory footprint for large result sets
        return new QueryResultsStreamingOutput(controller.query(filter, orderBy, offset, limit));
    }
}
