package rech.haeser.daniel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.ValidationExtension;

import com.mongodb.MongoClient;
import rech.haeser.daniel.model.EntityPackage;

/**
 * @author daniel.rech
 */
@Singleton
public class DatastoreProducer {

    private MongoClient mongoClient;

    @Inject
    private Configuration configuration;

    @PostConstruct
    public void postConstruct() {
        final String host = configuration.get(Configuration.Key.DATABASE_HOST);
        final int port = configuration.getAsInt(Configuration.Key.DATABASE_PORT);
        mongoClient = new MongoClient(host, port);
    }

    @Produces
    @javax.inject.Singleton
    public Datastore produceDatastore() {

        final Morphia morphia = new WildflyVFSCompatibleMorphia();
        new ValidationExtension(morphia);
        morphia.mapPackageFromClass(EntityPackage.class);

        return morphia.createDatastore(mongoClient, configuration.get(Configuration.Key.DATABASE_NAME));
    }

    @PreDestroy
    public void preDestroy() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
