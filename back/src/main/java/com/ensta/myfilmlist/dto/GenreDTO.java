package com.ensta.myfilmlist.dto;

public class GenreDTO {

    private Long id;

    private String surname;

    public GenreDTO() {
        this.id = 0L;
        this.surname = "";
    }

    public GenreDTO(Long id, String surname) {
        this.id = id;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString(){
        return "GenreDTO [id=" + this.id + ", surname=" + this.surname + "]";
    }

}
