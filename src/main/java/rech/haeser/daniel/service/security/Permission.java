package rech.haeser.daniel.service.security;

/**
 * @author daniel.rech
 */
public final class Permission {

    public static final String USER_CREATE = "USER_CREATE";
    public static final String USER_RETRIEVE = "USER_RETRIEVE";
    public static final String USER_UPDATE = "USER_UPDATE";
    public static final String USER_DELETE = "USER_DELETE";

    static final String[] USER = {USER_CREATE, USER_RETRIEVE, USER_UPDATE, USER_DELETE};

    private Permission() {
    }
}
