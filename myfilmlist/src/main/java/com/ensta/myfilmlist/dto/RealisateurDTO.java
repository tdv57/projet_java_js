package com.ensta.myfilmlist.dto;

import java.time.LocalDate;
import java.util.*;

import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;

public class RealisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private List<FilmDTO> filmRealises;
    private boolean celebre;

    public RealisateurDTO() {
        this.id = null;
        this.nom = new String();
        this.prenom = new String();
        this.dateNaissance = null;
        this.filmRealises = new ArrayList<>();
        this.celebre = false;
    }
    public RealisateurDTO(RealisateurDTO realisateurDTO) {
        this.id = realisateurDTO.id;
        this.nom = realisateurDTO.nom;
        this.prenom = realisateurDTO.prenom;
        this.dateNaissance = realisateurDTO.dateNaissance;
        this.filmRealises = new ArrayList<>();
        for (FilmDTO film : realisateurDTO.filmRealises) {
            this.filmRealises.add(new FilmDTO(film));
        }
        this.celebre = realisateurDTO.celebre;
    }

    public RealisateurDTO(Long id, String nom, String prenom, LocalDate dateNaissance, List<FilmDTO> filmRealises, boolean celebre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.filmRealises = new ArrayList<>();
        for (FilmDTO film : filmRealises) {
            this.filmRealises.add(new FilmDTO(film));
        }
        this.celebre = celebre;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    } 

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    } 
    public String getPrenom() {
        return this.prenom;
    }

    public LocalDate getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public List<FilmDTO> getFilmRealises() {
        return this.filmRealises;
    }

    public void setFilmRealises(List<FilmDTO> filmRealises) {
        this.filmRealises = filmRealises;
    }

    public boolean isCelebre() {
        return this.celebre;
    }

    public void setCelebre(boolean celebre) {
        this.celebre = celebre;
    }

    @Override
    public String toString(){
        return "RealisateurDTO [id=" + this.id + ", nom=" + this.nom + ", prenom=" + this.prenom +", date de naissance=" + this.dateNaissance+ ", celebre=" + this.celebre + "]";
    }

}