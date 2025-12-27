package com.ensta.myfilmlist.dto;

public class HistoryDTO {

    private long id;

    private FilmDTO filmDTO;

    private UserDTO userDTO;

    private int rating;

    public HistoryDTO() {
        this.id = 0L;
        this.filmDTO = new FilmDTO();
        this.userDTO = new UserDTO();
        this.rating = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FilmDTO getFilmDTO() {
        return filmDTO;
    }

    public void setFilmDTO(FilmDTO filmDTO) {
        this.filmDTO = filmDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
