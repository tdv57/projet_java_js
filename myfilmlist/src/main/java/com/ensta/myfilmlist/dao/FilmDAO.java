package com.ensta.myfilmlist.dao;
import java.util.*;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.*;

public interface FilmDAO {
    public List<Film> findAll() throws ServiceException;
    public Film save(Film film);
    public Optional<Film> findById(long id);
    public void delete(Film film);
    public List<Film> findByRealisateurId(long realisateur_id);
}