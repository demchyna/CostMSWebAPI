package com.mdem.cms.security;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserCredential {

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9_\\-]+", message = "{user.username.pattern}")
    @Size(min = 2, max = 31, message = "{user.username.size}")
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
