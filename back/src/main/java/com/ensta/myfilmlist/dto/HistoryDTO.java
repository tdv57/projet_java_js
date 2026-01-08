package com.ensta.myfilmlist.dto;

import java.util.Optional;

/**
 * Class representing data of a History.
 * DTO: transferred data between layers.
 */
public class HistoryDTO {

    private long id;

    private FilmDTO filmDTO;

    private UserDTO userDTO;

    private Optional<Integer> rating;

    public HistoryDTO() {
        this.id = 0L;
        this.filmDTO = new FilmDTO();
        this.userDTO = new UserDTO();
        this.rating = Optional.empty();
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

    public Optional<Integer> getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 0) {
            this.rating = Optional.empty();
        } else {
            this.rating = Optional.of(rating);
        }
    }
}
