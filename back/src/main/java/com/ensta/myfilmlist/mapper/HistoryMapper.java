package com.ensta.myfilmlist.mapper;

import com.ensta.myfilmlist.dto.HistoryDTO;
import com.ensta.myfilmlist.model.History;

import java.util.Optional;


public class HistoryMapper {

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

