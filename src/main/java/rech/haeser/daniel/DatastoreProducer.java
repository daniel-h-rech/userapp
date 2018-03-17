package rech.haeser.daniel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

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

        final Morphia morphia = new Morphia();
        morphia.mapPackage(EntityPackage.class.getPackage().getName());

        final Datastore datastore = morphia.createDatastore(mongoClient, "daniel_haeser_rech_userapp");
        datastore.ensureIndexes();
        return datastore;
    }

    @PreDestroy
    public void preDestroy() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
