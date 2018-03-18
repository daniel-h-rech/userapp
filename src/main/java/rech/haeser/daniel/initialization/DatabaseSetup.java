package rech.haeser.daniel.initialization;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import rech.haeser.daniel.model.User;

/**
 * @author daniel.rech
 */
@Singleton
@Startup
public class DatabaseSetup {

    @Inject
    private Datastore datastore;

    @PostConstruct
    void postConstruct() {
        datastore.ensureIndexes();
        ensureAdminExists();
    }

    // FIXME Created for demo purposes for setting up an admin user. In a real world scenario it must not be used since
    // the default admin password would stay in memory and in the app bundle (war file).
    private void ensureAdminExists() {
        final Query<User> query = datastore.createQuery(User.class);
        User admin = query.field("email").equal("admin@foobar.com").get();
        if (admin == null) {
            admin = new User("admin@foobar.com", "123456");
            datastore.save(admin);
        }
    }
}
