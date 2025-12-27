package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;

import java.util.*;

import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.dto.*;


public interface MyFilmsService {
    FilmDTO createFilm(FilmForm filmForm) throws ServiceException;
    List<Film> findAll() throws ServiceException;
    Film findFilmById(long id) throws ServiceException;
    Film findFilmByTitle(String title) throws ServiceException;
    List<Film> findFilmByDirectorId(long id) throws ServiceException;
    FilmDTO updateFilm(long id, FilmForm filmForm) throws ServiceException;
    void deleteFilm(long id) throws ServiceException;

    DirectorDTO createDirector(DirectorForm directorForm) throws ServiceException;
    List<Director> findAllDirectors() throws ServiceException;
    Director findDirectorById(Long id) throws ServiceException;
    Director findDirectorBySurnameAndName(String surname, String name) throws ServiceException;
    Director updateDirectorFamous(Director director) throws ServiceException;
    List<Director> updateDirectorFamouss(List<Director> directors) throws ServiceException;
    DirectorDTO updateDirector(long id, DirectorForm directorForm) throws ServiceException;
    void deleteDirector(long id) throws ServiceException;

    List<Genre> findAllGenres() throws ServiceException;
    Genre findGenreById(Long id) throws ServiceException;
    GenreDTO updateGenre(long id, String name) throws ServiceException;

    List<Film> findWatchList(long userId) throws ServiceException;
    History addFilmToWatchList(long userId, long filmId) throws ServiceException;
    void removeFilmFromWatchList(long userId, long filmId) throws ServiceException;
    History rateFilm(long userId, long filmId, int rating) throws ServiceException;
    Optional<Integer> getNote(long userId, long filmId) throws ServiceException;

    int calculerDurationTotale(List<Film> filmsProduced);
    Optional<Double> calculerNoteMoyenne(List<Double> notes);
}