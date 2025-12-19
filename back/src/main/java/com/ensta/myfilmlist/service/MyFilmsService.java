package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;

import java.util.*;

import com.ensta.myfilmlist.form.RealisateurForm;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.dto.*;


public interface MyFilmsService {
    FilmDTO createFilm(FilmForm filmForm) throws ServiceException;
    List<Film> findAll() throws ServiceException;
    Film findFilmById(long id) throws ServiceException;
    Film findFilmByTitle(String title) throws ServiceException;
    List<Film> findFilmByRealisateurId(long id) throws ServiceException;
    FilmDTO updateFilm(long id, FilmForm filmForm) throws ServiceException;
    void deleteFilm(long id) throws ServiceException;

    RealisateurDTO createRealisateur(RealisateurForm realisateurForm) throws ServiceException;
    List<Realisateur> findAllRealisateurs() throws ServiceException;
    Realisateur findRealisateurById(Long id) throws ServiceException;
    Realisateur findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException;
    Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException;
    List<Realisateur> updateRealisateurCelebres(List<Realisateur> realisateurs) throws ServiceException;
    RealisateurDTO updateRealisateur(long id, RealisateurForm realisateurForm) throws ServiceException;
    void deleteRealisateur(long id) throws ServiceException;

    List<Genre> findAllGenres() throws ServiceException;
    Genre findGenreById(Long id) throws ServiceException;
    GenreDTO updateGenre(long id, String nom) throws ServiceException;

    int calculerDureeTotale(List<Film> filmRealises);
    Optional<Double> calculerNoteMoyenne(List<Double> notes);
}