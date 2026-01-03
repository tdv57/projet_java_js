package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.dao.GenreDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.GenreMapper;
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Genre;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.service.*;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.DirectorDAO;
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
    public static final int MIN_NB_FILMS_FAMOUS_DIRECTOR = 3;
    @Autowired
    private FilmDAO filmDAO;

    @Autowired
    private DirectorDAO directorDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private FilmMapper filmMapper;

    @Override
    @Transactional
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException {
        Film film = filmMapper.convertFilmFormToFilm(filmForm);
        if (directorDAO.findById(filmForm.getDirectorId()).isEmpty()) {
            throw new ServiceException("Le réalisateur n'existe pas");
        }
        film = this.filmDAO.save(film);
        film.setDirector(updateDirectorFamous(film.getDirector()));
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
    public List<Film> findFilmByDirectorId(long id) throws ServiceException {
        List<Film> films = this.filmDAO.findByDirectorId(id);
        if (films.isEmpty()) {
            throw new ServiceException("Le réalistauer n'a réalisé aucun film");
        }
        return films;
    }

    @Override
    @Transactional
    public FilmDTO updateFilm(long id, FilmForm filmForm) throws ServiceException {
        Optional<Director> director = this.directorDAO.findById(filmForm.getDirectorId());
        if (director.isEmpty()) throw new ServiceException("Réalisateur inexistant");
        Film new_film = filmMapper.convertFilmFormToFilm(filmForm);
        Film film = this.filmDAO.update(id, new_film);
        return FilmMapper.convertFilmToFilmDTO(film);
    }

    @Override
    @Transactional
    public void deleteFilm(long id) throws ServiceException {
        Film film = findFilmById(id);
        this.filmDAO.delete(film);
        updateDirectorFamous(film.getDirector());
    }


    @Override
    @Transactional
    public DirectorDTO createDirector(DirectorForm directorForm) throws ServiceException {
        Director director = DirectorMapper.convertDirectorFormToDirector(directorForm);
        director = this.directorDAO.save(director);
        return DirectorMapper.convertDirectorToDirectorDTO(director);
    }

    @Override 
    public List<Director> findAllDirectors() throws ServiceException {
        return this.directorDAO.findAll();
    }

    @Override
    public Director findDirectorById(Long id) throws ServiceException {
        Optional<Director> director = this.directorDAO.findById(id);
        if (director.isEmpty()) {
            throw new  ServiceException ("Le réalisateur demandé n'existe pas");
        }
        return director.get();
    }

    @Override
    public Director findDirectorBySurnameAndName(String surname, String name) throws ServiceException {
        Optional<Director> director = this.directorDAO.findBySurnameAndName(surname, name);
        if (director.isEmpty()) {
            throw new  ServiceException ("Le réalisateur demandé n'existe pas");
        }
        return director.get();
    }

    /**
     * La méthode prend en entrée un Director non null, si le Director a fait au moins 3 films indique famous=true, le cas contraire famous=false, renvoit le Director modifié.
     */
    @Override
    @Transactional
    public Director updateDirectorFamous(Director director) throws ServiceException {
        try {
            List<Film> filmsDuDirector = filmDAO.findByDirectorId(director.getId());
            director.setfilmsProduced(filmsDuDirector);
            director.setFamous(director.getfilmsProduced().size() >= MIN_NB_FILMS_FAMOUS_DIRECTOR);
            return directorDAO.update(director.getId(), director);
        } catch (ServiceException e) {
            throw new ServiceException("Réalisateur inexistant");
        } catch(Throwable e) {
            throw new ServiceException("Erreur lors de la mise à jour de la célébrité", e);
        }
    }


    @Override
    @Transactional
    public List<Director> updateDirectorFamouss(List<Director> directors) throws ServiceException {
        try {
            return directors.stream()
                    .map(director -> {
                        try {
                            return updateDirectorFamous(director);
                        } catch(ServiceException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Director::isFamous)
                    .collect(Collectors.toList());
        } catch (Throwable e) {
            throw new ServiceException("Erreur dans la mise à jour de la célébrité", e);
        }
    }

    @Override
    @Transactional
    public DirectorDTO updateDirector(long id, DirectorForm directorForm) throws ServiceException {
        Director new_director = DirectorMapper.convertDirectorFormToDirector(directorForm);
        Director director = this.directorDAO.update(id, new_director);
        return DirectorMapper.convertDirectorToDirectorDTO(director);
    }

    @Override
    @Transactional
    public void deleteDirector(long id) throws ServiceException {
        this.directorDAO.delete(id);
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
    public GenreDTO updateGenre(long id, String name) throws ServiceException {
        Genre genre = this.genreDAO.update(id, name);
        return GenreMapper.convertGenreToGenreDTO(genre);
    }


    /**
     * Prend une liste de Film et renvoie une int représentant la somme des durées de chaque film
     */
    @Override
    public int calculerDurationTotale(List<Film> filmsProduced) {
        return filmsProduced.stream()
                .map(Film::getDuration)
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