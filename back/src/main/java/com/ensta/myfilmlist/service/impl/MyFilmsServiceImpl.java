package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.dao.GenreDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.form.RealisateurForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.GenreMapper;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Genre;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.*;
import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.dto.*;

import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class MyFilmsServiceImpl implements MyFilmsService {
    public static final int NB_FILMS_MIN_REALISATEUR_CELEBRE = 3; 
    @Autowired
    private FilmDAO filmDAO;

    @Autowired
    private RealisateurDAO realisateurDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private FilmMapper filmMapper;

    @Override
    @Transactional
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException {
        Film film = filmMapper.convertFilmFormToFilm(filmForm);
        if (realisateurDAO.findById(filmForm.getRealisateurId()).isEmpty()) {
            throw new ServiceException("Le réalisateur n'existe pas");
        }
        film = this.filmDAO.save(film);
        film.setRealisateur(updateRealisateurCelebre(film.getRealisateur()));
        return FilmMapper.convertFilmToFilmDTO(film);
    }

    @Override
    public List<Film> findAll() throws ServiceException {
        return this.filmDAO.findAll();
    }

    @Override
    public Film findFilmById(long id) throws ServiceException {
        Optional<Film> film = this.filmDAO.findById(id);
        if (film.isEmpty()) {
            throw new  ServiceException ("Le film demandé n'existe pas");
        }
        return film.get();
    }

    @Override
    public Film findFilmByTitle(String title) throws ServiceException {
        Optional<Film> film = this.filmDAO.findByTitle(title);
        if (film.isEmpty()) {
            throw new ServiceException("Le film demandé n'existe pas");
        }
        return film.get();
    }

    @Override
    public List<Film> findFilmByRealisateurId(long id) throws ServiceException {
        List<Film> films = this.filmDAO.findByRealisateurId(id);
        if (films.isEmpty()) {
            throw new ServiceException("Le réalistauer n'a réalisé aucun film");
        }
        return films;
    }

    @Override
    @Transactional
    public FilmDTO updateFilm(long id, FilmForm filmForm) throws ServiceException {
        Film new_film = filmMapper.convertFilmFormToFilm(filmForm);
        Film film = this.filmDAO.update(id, new_film);
        return FilmMapper.convertFilmToFilmDTO(film);
    }

    @Override
    @Transactional
    public void deleteFilm(long id) throws ServiceException {
        Film film = findFilmById(id);
        this.filmDAO.delete(film);
        updateRealisateurCelebre(film.getRealisateur());
    }


    @Override
    @Transactional
    public RealisateurDTO createRealisateur(RealisateurForm realisateurForm) throws ServiceException {
        Realisateur realisateur = RealisateurMapper.convertRealisateurFormToRealisateur(realisateurForm);
        realisateur = this.realisateurDAO.save(realisateur);
        return RealisateurMapper.convertRealisateurToRealisateurDTO(realisateur);
    }

    @Override 
    public List<Realisateur> findAllRealisateurs() throws ServiceException {
        return this.realisateurDAO.findAll();
    }

    @Override
    public Realisateur findRealisateurById(Long id) throws ServiceException {
        Optional<Realisateur> realisateur = this.realisateurDAO.findById(id);
        if (realisateur.isEmpty()) {
            throw new  ServiceException ("Le réalisateur demandé n'existe pas");
        }
        return realisateur.get();
    }

    @Override
    public Realisateur findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException {
        Optional<Realisateur> realisateur = this.realisateurDAO.findByNomAndPrenom(nom, prenom);
        if (realisateur.isEmpty()) {
            throw new  ServiceException ("Le réalisateur demandé n'existe pas");
        }
        return realisateur.get();
    }

    /**
     * La méthode prend en entrée un Realisateur non null, si le Realisateur a fait au moins 3 films indique celebre=true, le cas contraire celebre=false, renvoit le Realisateur modifié.
     */
    @Override
    @Transactional
    public Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException {
        try {
            List<Film> filmsDuRealisateur = filmDAO.findByRealisateurId(realisateur.getId());
            realisateur.setFilmRealises(filmsDuRealisateur);
            realisateur.setCelebre(realisateur.getFilmRealises().size() >= NB_FILMS_MIN_REALISATEUR_CELEBRE);
            return realisateurDAO.update(realisateur.getId(), realisateur);
        } catch(Throwable e) {
            throw new ServiceException("Erreur lors de la mise à jour de la célébrité", e);
        }
    }


    @Override
    @Transactional
    public List<Realisateur> updateRealisateurCelebres(List<Realisateur> realisateurs) throws ServiceException {
        try {
            return realisateurs.stream()
                    .map(realisateur -> {
                        try {
                            return updateRealisateurCelebre(realisateur);
                        } catch(ServiceException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Realisateur::isCelebre)
                    .collect(Collectors.toList());
        } catch (Throwable e) {
            throw new ServiceException("Erreur dans la mise à jour de la célébrité", e);
        }
    }

    @Override
    @Transactional
    public RealisateurDTO updateRealisateur(long id, RealisateurForm realisateurForm) throws ServiceException {
        Realisateur new_realisateur = RealisateurMapper.convertRealisateurFormToRealisateur(realisateurForm);
        Realisateur realisateur = this.realisateurDAO.update(id, new_realisateur);
        return RealisateurMapper.convertRealisateurToRealisateurDTO(realisateur);
    }

    @Override
    @Transactional
    public void deleteRealisateur(long id) throws ServiceException {
        this.realisateurDAO.delete(id);
    }


    @Override
    public List<Genre> findAllGenres() throws ServiceException {
        return this.genreDAO.findAll();
    }

    @Override
    public Genre findGenreById(Long id) throws ServiceException {
        Optional<Genre> genre = this.genreDAO.findById(id);
        if (genre.isEmpty()) {
            throw new  ServiceException ("Le genre demandé n'existe pas");
        }
        return genre.get();
    }

    @Override
    @Transactional
    public GenreDTO updateGenre(long id, String nom) throws ServiceException {
        Genre genre = this.genreDAO.update(id, nom);
        return GenreMapper.convertGenreToGenreDTO(genre);
    }


    /**
     * Prend une liste de Film et renvoie une int représentant la somme des durées de chaque film
     */
    @Override
    public int calculerDureeTotale(List<Film> filmRealises) {
        return filmRealises.stream()
                .map(Film::getDuree)
                .reduce(0, Integer::sum);
    }

    /**
     * Prend un array de double et calcule un double représentant la note moyenne, renvoie 0 par défaut;
     */
    @Override
    public Optional<Double> calculerNoteMoyenne(List<Double> notes) {
        if (notes.isEmpty()) {
            return Optional.empty();
        }

        double noteMoyenne = notes.stream()
                .reduce(0.0, Double::sum);
        return Optional.of(BigDecimal.valueOf(noteMoyenne / notes.size()).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}