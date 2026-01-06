package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.History;

import java.util.List;
import java.util.Optional;

public interface HistoryDAO {
    List<Film> getWatchList(long userId) throws ServiceException;
    History addFilmToWatchList(long userId, long filmId) throws ServiceException;
    void deleteFilm(long userId, long filmId);
    History rateFilm(long userId, long filmId, int rating) throws ServiceException;
    Optional<Integer> getNote(long userId, long filmId) throws ServiceException;
    List<Integer> getNotesByFilmId (long filmId) throws ServiceException;
}
