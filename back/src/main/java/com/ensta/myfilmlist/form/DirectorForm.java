package com.ensta.myfilmlist.form;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Contient les donnees pour créer ou demander un réalisateur.
 */
public class DirectorForm {

    @NotBlank(message = "Le surname du réalisateur ne peut pas être vide")
    private String surname;

    @NotBlank(message = "Le présurname du réalisateur ne peut pas être vide")
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
