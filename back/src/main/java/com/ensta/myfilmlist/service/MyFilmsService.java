package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;

import java.util.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.dto.*;


public interface MyFilmsService {
    FilmDTO createFilm(FilmForm filmForm) throws ServiceException;
    List<Film> findAll() throws ServiceException;
    Film findFilmById(long id) throws ServiceException;
    Film findFilmByTitle(String title) throws ServiceException;
    FilmDTO updateFilm(long id, FilmForm filmForm) throws ServiceException;
    void deleteFilm(long id) throws ServiceException;

    RealisateurDTO createRealisateur(RealisateurForm realisateurForm) throws ServiceException;
    List<Realisateur> findAllRealisateurs() throws ServiceException;
    Realisateur findRealisateurById(Long id) throws ServiceException;
    Realisateur findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException;
    Film findFilmById(long id) throws ServiceException;
    void deleteFilm(long id) throws ServiceException;
}