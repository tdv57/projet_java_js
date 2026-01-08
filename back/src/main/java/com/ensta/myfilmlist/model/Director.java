package com.ensta.myfilmlist.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Data representing a Director.
 */
@Entity
@Table
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String surname;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @OneToMany(mappedBy = "director")
    private List<Film> filmsProduced;

    private boolean famous;

    /**
     * Constructors for a Director
     */

    public Director() {
        this.id = 0L;
        this.surname = "";
        this.name = "";
        this.birthdate = null;
        this.filmsProduced = new ArrayList<>();
        this.famous = false;
    }

    public Director(Director director) {
        this.id = director.id;
        this.surname = director.surname;
        this.name = director.name;
        this.birthdate = director.birthdate;
        this.filmsProduced = new ArrayList<>();
        for (Film film : director.filmsProduced) {
            this.filmsProduced.add(new Film(film));
        }
        this.famous = director.famous;
    }

    public Director(long id, String surname, String name, LocalDate birthdate, List<Film> filmsProduced, boolean famous) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.birthdate = birthdate;
        this.filmsProduced = new ArrayList<>();
        for (Film film : filmsProduced) {
            this.filmsProduced.add(new Film(film));
        }
        this.famous = famous;
    }

    public long getId() {
        return this.id;
    }

    /**
     * Getter and setter for every attribute
     */

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Film> getFilmsProduced() {
        return this.filmsProduced;
    }

    public void setFilmsProduced(List<Film> filmsProduced) {
        this.filmsProduced = filmsProduced;
    }

    public boolean isFamous() {
        return this.famous;
    }

    public void setFamous(boolean famous) {
        this.famous = famous;
    }

    @Override
    public String toString() {
        return "Director [id=" + this.id + ", surname=" + this.surname + ", name=" + this.name + ", date de naissance=" + this.birthdate + ", famous=" + this.famous + "]";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Director)) {
            return false;
        }
        Director director = (Director) object;
        return Objects.equals(this.id, director.getId())
                && director.isFamous() == this.famous
                && Objects.equals(director.getBirthdate(), this.birthdate)
                && Objects.equals(director.getName(), this.name)
                && Objects.equals(director.getSurname(), this.surname);
    }
}