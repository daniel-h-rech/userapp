package rech.haeser.daniel.model;

import static org.mongodb.morphia.utils.IndexType.TEXT;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;

/**
 * @author daniel.rech
 */
@Entity
@Indexes(@Index(fields = {
        @Field(value = "name", type = TEXT),
        @Field(value = "address", type = TEXT)
}))
public class User {

    private static final int MINIMUM_EMAIL_LENGTH = 3;

    private static final int MAXIMUM_EMAIL_LENGTH = 64;

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    private static final int MAXIMUM_PASSWORD_LENGTH = 64;

    @Id
    private ObjectId id;

    private String name;

    @Email
    @Size(min = MINIMUM_EMAIL_LENGTH, max = MAXIMUM_EMAIL_LENGTH)
    @NotNull
    @Indexed(options = @IndexOptions(unique = true))
    private String email;

    @NotNull
    private String password;

    private String address;

    private String phoneNumber;

    protected User() {
    }

    public User(final String anEmail, final String aPassword) {
        email = anEmail;
        setPassword(aPassword);
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(final String value) {
        if (value == null || value.length() < MINIMUM_PASSWORD_LENGTH || value.length() > MAXIMUM_PASSWORD_LENGTH) {
            final String message = "Password length must be between %s and %s";
            throw new IllegalArgumentException(String.format(message, MINIMUM_PASSWORD_LENGTH, MAXIMUM_PASSWORD_LENGTH));
        }
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
