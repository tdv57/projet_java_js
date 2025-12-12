package com.ensta.myfilmlist.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;
import java.security.Timestamp;
import java.time.LocalDate;

/**
 * Contient les donnees pour créer ou demander un réalisateur.
 */
public class RealisateurForm {

    @NotBlank(message = "Le nom du réalisateur ne peut pas être vide")
    private String nom;

    @NotBlank(message = "Le prénom du réalisateur ne peut pas être vide")
    private String prenom;

    private LocalDate dateNaissance;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

}
