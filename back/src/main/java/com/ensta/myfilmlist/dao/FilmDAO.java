package com.ensta.myfilmlist.dao;
import java.util.*;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.*;

public interface FilmDAO {
    List<Film> findAll() throws ServiceException;
    Film save(Film film);
    Optional<Film> findById(long id);
    Optional<Film> findByTitle(String title);
    List<Film> findByDirectorId(long director_id);
    Film update(long id, Film film) throws ServiceException;
    void delete(Film film);
}