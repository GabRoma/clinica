package com.clinica.odontologica.authentication;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {
    // Getters y Setters
    private String username;
    private String password;

    // Constructor vacío (necesario para la deserialización)
    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}