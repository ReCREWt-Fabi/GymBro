package de.othr.im.gymbro.model;


import java.io.Serial;
import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public AuthenticationResponse() {
        this.jwt = "";
    }

    public String getJwt() {
        return jwt;
    }
}
