package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;

import java.lang.reflect.Array;
import java.util.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.dto.*;


public interface MyFilmsService {
    Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException;
    int calculerDureeTotale(List<Film> filmRealises);
    Optional<Double> calculerNoteMoyenne(List<Double> notes);
    List<Realisateur> updateRealisateurCelebres(List<Realisateur> realisateurs) throws ServiceException;
    List<Film> findAll() throws ServiceException;
    FilmDTO createFilm(FilmForm filmForm) throws ServiceException;
    RealisateurDTO createRealisateur(Realisateur realisateur) throws ServiceException;
    List<Realisateur> findAllRealisateurs() throws ServiceException;
    Realisateur findRealisateurById(Long id) throws ServiceException;
    Realisateur findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException;
    Film findFilmById(long id) throws ServiceException;
    void deleteFilm(long id) throws ServiceException;
}