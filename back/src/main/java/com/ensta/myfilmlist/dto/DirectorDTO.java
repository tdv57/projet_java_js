package com.ensta.myfilmlist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * Class representing data of a Director.
 * DTO: transferred data between layers.
 */
public class DirectorDTO {
    private long id;
    private String surname;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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

    public long getId() {
        return this.id;
    }

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

    public boolean isFamous() {
        return this.famous;
    }

    public void setFamous(boolean famous) {
        this.famous = famous;
    }

    @Override
    public String toString() {
        return "DirectorDTO [id=" + this.id + ", surname=" + this.surname + ", name=" + this.name + ", birthdate=" + this.birthdate + ", famous=" + this.famous + "]";
    }

}