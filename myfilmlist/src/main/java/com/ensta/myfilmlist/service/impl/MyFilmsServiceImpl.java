package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.*;
import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.dto.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class MyFilmsServiceImpl implements MyFilmsService {
    public static final int NB_FILMS_MIN_REALISATEUR_CELEBRE = 3; 
    @Autowired
    private FilmDAO filmDAO;

    @Autowired
    private RealisateurDAO realisateurDAO;
    /**
     * La méthode prend en entré un Realisateur non null, si le Realisateur a fait au moins 3 films indique celebre=true, le cas contraire celebre=false, renvoit le Realisateur modifié.
     */
    @Override
    @Transactional
    public Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException {
        try {
            if (realisateur==null) {
                System.out.println("realisateur==null");
            }
            List<Film> filmsDuRealisateur = filmDAO.findByRealisateurId(realisateur.getId());
            realisateur.setFilmRealises(filmsDuRealisateur);
            if (realisateur.getFilmRealises().size() >= NB_FILMS_MIN_REALISATEUR_CELEBRE) {
                realisateur.setCelebre(true);
            } else {
                realisateur.setCelebre(false);
            }
            return realisateurDAO.update(realisateur);
        } catch(Throwable e) {
            throw new ServiceException("MyFilmsServiceImpl::updateRealisateurCelebre", e);
        }
    }

    /**
     * Prend une liste de Film et renvoie une int représentant la somme des durées de chaque film 
     */
    @Override 
    public int calculerDureeTotale(List<Film> filmRealises) {
        int dureeTotale = filmRealises.stream()
                                       .map(film -> film.getDuree())
                                       .reduce(0, (dureetotale, duree) -> dureetotale + duree);
        return dureeTotale;
    }
    
    /**
     * Prend un array de double et calcule un double représentant la note moyenne, renvoie 0 par défaut;
     */
    @Override
    public Optional<Double> calculerNoteMoyenne(List<Double> notes) {
        if (notes.size() == 0) {
            return Optional.empty();
        }
    
        double noteMoyenne = notes.stream()
                                    .reduce(0.0, (notemoyenne, note) -> notemoyenne + note);
        return Optional.of(((double) Math.round(noteMoyenne*100 / notes.size())) / 100);
    }

    @Override
    @Transactional
    public List<Realisateur> updateRealisateurCelebres(List<Realisateur> realisateurs) throws ServiceException {
        try {
        List<Realisateur> realisateursCelebres = realisateurs.stream()
                    .map(realisateur -> {
                            try {
                                return updateRealisateurCelebre(realisateur);
                            } catch(ServiceException e) {
                                throw new RuntimeException(e);
                            }
                        })
                    .filter(realisateur -> realisateur.isCelebre())
                    .collect(Collectors.toList());
        return realisateursCelebres;
        } catch (Throwable e) {
            throw new ServiceException("MyFilmsServiceImpl::updateRealisateurCelebres", e);
        }
    }

    @Override
    public List<Film> findAll() throws ServiceException {
        return this.filmDAO.findAll();
    }
    
    @Override
    @Transactional
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException {
        Film film = this.filmDAO.save(FilmMapper.convertFilmFormToFilm(filmForm));
        if (film.getRealisateur() == null) {
            throw new ServiceException("Le film associé ne possède pas de réalisateur");
        } else {
            film.setRealisateur(updateRealisateurCelebre(film.getRealisateur()));
            return FilmMapper.convertFilmToFilmDTO(film);
        }
    }

    @Override
    public RealisateurDTO createRealisateur(Realisateur realisateur) throws ServiceException {
        realisateur = this.realisateurDAO.save(realisateur);
        return RealisateurMapper.convertRealisateurToRealisateurDTO(realisateur);
    }

    @Override 
    public List<Realisateur> findAllRealisateurs() throws ServiceException {
        JdbcRealisateurDAO jdbcRealisateurDAO = new JdbcRealisateurDAO();
        return jdbcRealisateurDAO.findAll();
    }

    @Override
    public Realisateur findRealisateurById(Long id) throws ServiceException {
        JdbcRealisateurDAO jdbcRealisateurDAO = new JdbcRealisateurDAO();
        Optional<Realisateur> realisateur = jdbcRealisateurDAO.findById(id);
        if (realisateur.isPresent()) {
            return realisateur.get();
        } else {
            throw new ServiceException ("Le realisateur n'existe pas");
        }
    }

    @Override
    public Realisateur findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException {
        JdbcRealisateurDAO jdbcRealisateurDAO = new JdbcRealisateurDAO();
        return jdbcRealisateurDAO.findByNomAndPrenom(nom, prenom);
    }

    @Override
    public Film findFilmById(long id) throws ServiceException {
        Optional<Film> film = this.filmDAO.findById(id);
        if (film.isEmpty()) return null;
        return film.get();
    }

    @Override
    public void deleteFilm(long id) throws ServiceException {
        Film film = findFilmById(id);
        this.filmDAO.delete(film);
        updateRealisateurCelebre(film.getRealisateur());
    }
}