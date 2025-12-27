package com.ensta.myfilmlist.dto;

public class GenreDTO {

    private long id;

    private String name;

    public GenreDTO() {
        this.id = 0L;
        this.name = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "GenreDTO [id=" + this.id + ", name=" + this.name + "]";
    }

}
