package rech.haeser.daniel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;

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

    @PostConstruct
    public void postConstruct() {
        mongoClient = new MongoClient();
    }

    @Produces
    @javax.inject.Singleton
    public Datastore produceDatastore() {

        final Morphia morphia = new WildflyVFSCompatibleMorphia();
        new ValidationExtension(morphia);
        morphia.mapPackageFromClass(EntityPackage.class);

        // TODO get the DB name from an external config file
        return morphia.createDatastore(mongoClient, "daniel_haeser_rech_userapp");
    }

    @PreDestroy
    public void preDestroy() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
