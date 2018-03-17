package rech.haeser.daniel.controller;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import rech.haeser.daniel.model.User;

/**
 * @author daniel.rech
 */
@Stateless
public class LoginController {

    @Inject
    private Datastore datastore;

    public boolean login(final String email, final String password) {

        final Query<User> query = datastore.createQuery(User.class);
        final User user = query.field("email").equal(email).get();
        if (user == null) {
            return false;
        }

        return user.verifyPassword(password);
    }
}
