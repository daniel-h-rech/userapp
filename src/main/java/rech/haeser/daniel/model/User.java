package rech.haeser.daniel.model;

import javax.validation.constraints.NotNull;

import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * @author daniel.rech
 */
@Entity
public class User {

    @Id
    private String id;

    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String address;

    private String phoneNumber;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(final String value) {
        password = BCrypt.hashpw(value, BCrypt.gensalt());
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean verifyPassword(final String value) {
        return BCrypt.checkpw(value, password);
    }
}
