package rech.haeser.daniel.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;

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

    public List<User> query(final String filter,
                            final String orderBy,
                            final int offset,
                            final int limit) {


        final Query<User> query = datastore.createQuery(User.class);

        if (orderBy != null) {
            query.order(orderBy);
        }

        final FindOptions options = new FindOptions()
                .skip(offset)
                .limit(limit);

        // TODO use fetch
        return query.asList(options);
    }
}
