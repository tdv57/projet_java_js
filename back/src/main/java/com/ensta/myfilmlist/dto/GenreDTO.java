package com.ensta.myfilmlist.dto;

public class GenreDTO {

    private Long id;

    private String nom;

    public GenreDTO() {
        this.id = 0L;
        this.nom = "";
    }

    public GenreDTO(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString(){
        return "GenreDTO [id=" + this.id + ", nom=" + this.nom + "]";
    }

}
