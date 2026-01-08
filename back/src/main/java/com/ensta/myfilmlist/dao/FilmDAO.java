package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmDAO {
    List<Film> findAll() throws ServiceException;

    Film save(Film film) throws ServiceException;

    Optional<Film> findById(long id) throws ServiceException;

    Optional<Film> findByTitle(String title) throws ServiceException;

    List<Film> findByDirectorId(long director_id) throws ServiceException;

    Film update(long id, Film film) throws ServiceException;

    void delete(Film film) throws ServiceException;
}