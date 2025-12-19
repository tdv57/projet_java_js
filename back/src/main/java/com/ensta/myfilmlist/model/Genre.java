package com.ensta.myfilmlist.model;

import javax.persistence.*;

@Entity
@Table
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String nom;

    public Genre() {
        this.id = 0;
        this.nom = "";
    }

    public Genre(Genre genre) {
        this.id = genre.getId();
        this.nom = genre.getNom();
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id) {this.id = id; }

    public String getNom() {
        return this.nom;
    }
    public void setNom(String nom) {this.nom = nom; }

    @Override
    public String toString() {
        return "Genre{" + "id=" + id + ", nom=" + nom + '}';
    }
}
