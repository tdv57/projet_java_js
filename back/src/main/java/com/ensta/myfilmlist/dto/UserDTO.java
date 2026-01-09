package com.ensta.myfilmlist.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing data of a User.
 * DTO: transferred data between layers.
 */
public class UserDTO {
    private long id;
    private String surname;
    private String name;
    private String password;
    private String roles;
    private List<FilmDTO> watchedFilms;

    public UserDTO() {
        this.id = 0L;
        this.surname = "";
        this.name = "";
        this.watchedFilms = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<FilmDTO> getWatchedFilms() {
        return watchedFilms;
    }

    public void setWatchedFilms(List<FilmDTO> watchedFilms) {
        this.watchedFilms = watchedFilms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getRoles() { return roles; }

    public void setRoles(String roles) { this.roles = roles; }
}
