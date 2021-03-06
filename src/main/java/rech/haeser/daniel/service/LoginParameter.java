package rech.haeser.daniel.service;

import javax.validation.constraints.NotNull;

/**
 * @author daniel.rech
 */
class LoginParameter {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
