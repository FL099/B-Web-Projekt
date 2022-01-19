package com.example.projekt.data.model;

import com.example.projekt.util.Role;

public class Auth {

    private String email;
    private String password;
    private Role role;

    public Auth(String email, String password){
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public Auth(String email, String password, Role role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Auth(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
