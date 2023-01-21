package de.othr.im.gymbro.model;

import java.io.Serial;
import java.io.Serializable;

public class ShareWithUserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
