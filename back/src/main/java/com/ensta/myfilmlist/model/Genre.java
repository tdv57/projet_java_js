package com.ensta.myfilmlist.model;

import javax.persistence.*;

@Entity
@Table
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String surname;

    public Genre() {
        this.id = 0;
        this.surname = "";
    }

    public Genre(Genre genre) {
        this.id = genre.getId();
        this.surname = genre.getSurname();
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id) {this.id = id; }

    public String getSurname() {
        return this.surname;
    }
    public void setSurname(String surname) {this.surname = surname; }

    @Override
    public String toString() {
        return "Genre{" + "id=" + id + ", surname=" + surname + '}';
    }
}
