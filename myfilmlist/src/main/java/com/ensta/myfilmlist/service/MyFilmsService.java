package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;

import java.lang.reflect.Array;
import java.util.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.dto.*;


public interface MyFilmsService {
    public Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException;
    public int calculerDureeTotale(List<Film> filmRealises);
    public Optional<Double> calculerNoteMoyenne(List<Double> notes);
    public List<Realisateur> updateRealisateurCelebres(List<Realisateur> realisateurs) throws ServiceException;
    public List<Film> findAll() throws ServiceException;
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException;
    public RealisateurDTO createRealisateur(Realisateur realisateur);
    public List<Realisateur> findAllRealisateurs() throws ServiceException;
    public Realisateur findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException;
    public Film findFilmById(long id) throws ServiceException;
    public void deleteFilm(long id) throws ServiceException;
}