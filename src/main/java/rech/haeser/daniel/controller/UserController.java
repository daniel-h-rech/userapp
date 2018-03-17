package rech.haeser.daniel.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import rech.haeser.daniel.model.User;

/**
 * @author daniel.rech
 */
@Stateless
public class UserController {

    @Inject
    private Datastore datastore;

    public User create(final User user) {
        datastore.save(user);
        return user;
    }

    public User retrieve(final ObjectId id) {
        return datastore.get(User.class, id);
    }

    public User update(final User user) {
        datastore.merge(user);
        return user;
    }

    public void delete(final ObjectId id) {
        datastore.delete(User.class, id);
    }

    public List<User> query() {
        return new ArrayList<>(); // FIXME implement
    }
}
