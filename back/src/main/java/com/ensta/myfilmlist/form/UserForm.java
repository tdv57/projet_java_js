package com.ensta.myfilmlist.form;

import javax.validation.constraints.NotBlank;

public class UserForm {

    @NotBlank(message = "User's name can't be blank/empty.")
    private String name;

    @NotBlank(message = "User's surname can't be blank/empty.")
    private String surname;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
