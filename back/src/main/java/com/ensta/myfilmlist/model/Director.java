package com.ensta.myfilmlist.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String surname;

    private String name;

    private LocalDate birthdate;

    @OneToMany(mappedBy = "director")
    private List<Film> filmsProduced;

    private boolean famous;

    public Director() {
        this.id = null;
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

    public Director(Long id, String surname, String name, LocalDate birthdate, List<Film> filmsProduced, boolean famous) {
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

    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return this.surname;
    } 

    public void setName(String name) {
        this.name = name;
    } 
    public String getName() {
        return this.name;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public List<Film> getfilmsProduced() {
        return this.filmsProduced;
    }

    public void setfilmsProduced(List<Film> filmsProduced) {
        this.filmsProduced = filmsProduced;
    }

    public boolean isFamous() {
        return this.famous;
    }

    public void setFamous(boolean famous) {
        this.famous = famous;
    }

    @Override
    public String toString(){
        return "Director [id=" + this.id + ", surname=" + this.surname + ", name=" + this.name +", date de naissance=" + this.birthdate+ ", famous=" + this.famous + "]";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;
        Director director = (Director) object;
        if (this.id == director.getId() 
            && director.isFamous() == this.famous 
            && Objects.equals(director.getBirthdate(), this.birthdate) 
            && Objects.equals(director.getName(), this.name) 
            && Objects.equals(director.getSurname(), this.surname)) {
            return true;
        }
        return false;
    }
}