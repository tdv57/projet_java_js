package com.ensta.myfilmlist.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    public Genre() {
        this.id = 0L;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true; 
        if (! (o instanceof Genre)) return false;
        Genre genre = (Genre) o;    
        if (Objects.equals(genre.getId(), this.getId()) && Objects.equals(genre.getName(), this.getName())) return true;
        return false;
    }
}
