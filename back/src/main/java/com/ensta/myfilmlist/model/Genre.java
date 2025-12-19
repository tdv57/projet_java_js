package com.ensta.myfilmlist.model;

import javax.persistence.*;

@Entity
@Table
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    public Genre() {
        this.id = 0;
        this.name = "";
    }

    public Genre(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id) {this.id = id; }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {this.name = name; }

    @Override
    public String toString() {
        return "Genre{" + "id=" + id + ", name=" + name + '}';
    }
}
