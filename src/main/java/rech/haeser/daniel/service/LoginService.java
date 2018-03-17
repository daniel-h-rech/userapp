package rech.haeser.daniel.service;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import rech.haeser.daniel.controller.LoginController;

/**
 * @author daniel.rech
 */
@Path(ResourcePath.LOGIN)
@Produces(MediaType.APPLICATION_JSON)
@Api
public class LoginService {

    @EJB
    private LoginController controller;

    @POST
    public void login(@Valid @NotNull final LoginParameter loginParameter) {
        if (!controller.login(loginParameter.getEmail(), loginParameter.getPassword())) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }
}
