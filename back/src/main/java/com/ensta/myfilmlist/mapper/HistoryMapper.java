package com.ensta.myfilmlist.mapper;

import com.ensta.myfilmlist.dto.HistoryDTO;
import com.ensta.myfilmlist.model.History;
import java.util.Optional;

/**
 * Functions to cast History into and from DTO and Form.
 */
public class HistoryMapper {

    /**
     * Convert a history's DTO into a list of history.
     *
     * @param historyDTO    history's DTO to be converted
     * @return              history created from the parameter
     */
    public static History convertHistoryDTOToHistory(HistoryDTO historyDTO) {
        if (historyDTO == null) {
            return null;
        }
        History history = new History();
        history.setId(historyDTO.getId());
        history.setFilm(FilmMapper.convertFilmDTOToFilm(historyDTO.getFilmDTO()));
        history.setUser(UserMapper.convertUserDTOToUser(historyDTO.getUserDTO()));
        Optional<Integer> rating = historyDTO.getRating();
        if (rating.isPresent()) {
            history.setRating(rating.get());
        } else {
            history.setRating(-1);
        }
        return history;
    }

    /**
     * Convert a history into a history's DTO.
     *
     * @param history   history to be converted
     * @return          history's DTO created from the parameter
     */
    public static HistoryDTO convertHistoryToHistoryDTO(History history) {
        if (history == null) {
            return null;
        }
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setId(history.getId());
        historyDTO.setFilmDTO(FilmMapper.convertFilmToFilmDTO(history.getFilm()));
        historyDTO.setUserDTO(UserMapper.convertUserToUserDTO(history.getUser()));
        historyDTO.setRating(history.getRating());
        return historyDTO;
    }
}

