package com.ensta.myfilmlist.form;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Form to parse create a Director from an entry.
 */
public class DirectorForm {

    @NotBlank(message = "Director's surname can't be blank/empty.")
    private String surname;

    @NotBlank(message = "Director's name can't be blank/empty.")
    private String name;

    private LocalDate birthdate;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

}
