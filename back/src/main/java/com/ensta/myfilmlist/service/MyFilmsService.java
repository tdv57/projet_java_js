package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.GenreDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Genre;
import com.ensta.myfilmlist.model.History;

import java.util.List;
import java.util.Optional;
import com.ensta.myfilmlist.form.UserForm;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.dto.*;


public interface MyFilmsService {
    FilmDTO createFilm(FilmForm filmForm) throws ServiceException;
    List<Film> findAll();
    Film findFilmById(long id) throws ServiceException;
    Film findFilmByTitle(String title) throws ServiceException;
    List<Film> findFilmByDirectorId(long id) throws ServiceException;
    FilmDTO updateFilm(long id, FilmForm filmForm) throws ServiceException;
    void deleteFilm(long id) throws ServiceException;

    DirectorDTO createDirector(DirectorForm directorForm) throws ServiceException;
    List<Director> findAllDirectors() throws ServiceException;
    Director findDirectorById(long id) throws ServiceException;
    Director findDirectorByNameAndSurname(String name, String surname) throws ServiceException;
    Director updateDirectorFamous(Director director) throws ServiceException;
    List<Director> updateDirectorsFamous(List<Director> directors) throws ServiceException;
    DirectorDTO updateDirector(long id, DirectorForm directorForm) throws ServiceException;
    void deleteDirector(long id) throws ServiceException;

    List<Genre> findAllGenres() throws ServiceException;
    Genre findGenreById(long id) throws ServiceException;
    GenreDTO updateGenre(long id, String name) throws ServiceException;

    List<Film> findWatchList(long userId) throws ServiceException;
    History addFilmToWatchList(long userId, long filmId) throws ServiceException;
    void removeFilmFromWatchList(long userId, long filmId) throws ServiceException;
    History rateFilm(long userId, long filmId, int rating) throws ServiceException;

    Optional<Integer> getUserRating(long userId, long filmId) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;
    UserDTO createUser(UserForm userForm) throws ServiceException;
    User findUserById(long id) throws ServiceException;
    User findUserBySurnameAndName(String surname, String name) throws ServiceException;
    UserDTO updateUser(long id, UserForm userForm) throws ServiceException;
    UserDTO setUserAsAdmin(long id) throws ServiceException;
    void deleteUser(long id) throws ServiceException;

    Optional<Double> getMeanRating(long filmId) throws ServiceException;

    int getFullWatchTime(List<Film> filmsProduced);

    Optional<Double> getMeanRating(List<Double> notes);
}