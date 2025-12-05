package com.ensta.myfilmlist.model;

import java.time.LocalDate;
import java.util.*;


public class Realisateur {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private List<Film> filmRealises;
    private boolean celebre;

    public Realisateur() {
        this.id = null;
        this.nom = new String();
        this.prenom = new String();
        this.dateNaissance = null;
        this.filmRealises = new ArrayList<>();
        this.celebre = false;
    }
    public Realisateur(Realisateur realisateur) {
        this.id = realisateur.id;
        this.nom = realisateur.nom;
        this.prenom = realisateur.prenom;
        this.dateNaissance = realisateur.dateNaissance;
        this.filmRealises = new ArrayList<>();
        for (Film film : realisateur.filmRealises) {
            this.filmRealises.add(new Film(film));
        }
        this.celebre = realisateur.celebre;
    }

    public Realisateur(Long id, String nom, String prenom, LocalDate dateNaissance, List<Film> filmRealises, boolean celebre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.filmRealises = new ArrayList<>();
        for (Film film : filmRealises) {
            this.filmRealises.add(new Film(film));
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

    public List<Film> getFilmRealises() {
        return this.filmRealises;
    }

    public void setFilmRealises(List<Film> filmRealises) {
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
        return "Realisateur [id=" + this.id + ", nom=" + this.nom + ", prenom=" + this.prenom +", date de naissance=" + this.dateNaissance+ ", celebre=" + this.celebre + "]";
    }
}