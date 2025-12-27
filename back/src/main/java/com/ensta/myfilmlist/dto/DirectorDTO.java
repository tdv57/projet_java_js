package com.ensta.myfilmlist.dto;

import java.time.LocalDate;
import java.util.*;

public class DirectorDTO {
    private long id;
    private String surname;
    private String name;
    private LocalDate birthdate;
    private boolean famous;

    public DirectorDTO() {
        this.id = 0L;
        this.surname = "";
        this.name = "";
        this.birthdate = null;
        this.famous = false;
    }
    public DirectorDTO(DirectorDTO directorDTO) {
        this.id = directorDTO.id;
        this.surname = directorDTO.surname;
        this.name = directorDTO.name;
        this.birthdate = directorDTO.birthdate;
        this.famous = directorDTO.famous;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
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

    public boolean isFamous() {
        return this.famous;
    }

    public void setFamous(boolean famous) {
        this.famous = famous;
    }

    @Override
    public String toString(){
        return "DirectorDTO [id=" + this.id + ", surname=" + this.surname + ", name=" + this.name +", date de naissance=" + this.birthdate+ ", famous=" + this.famous + "]";
    }

}