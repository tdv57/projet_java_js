package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.dao.DirectorDAO;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.GenreDAO;
import com.ensta.myfilmlist.dao.HistoryDAO;
import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.GenreDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.GenreMapper;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Genre;
import com.ensta.myfilmlist.model.History;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
    private HistoryDAO historyDAO;

    @Autowired
    private FilmMapper filmMapper;

    @Override
    @Transactional
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException {
        Film film = filmMapper.convertFilmFormToFilm(filmForm);
        if (directorDAO.findById(filmForm.getDirectorId()).isEmpty()) {
            throw new ServiceException("Director doens't exist");
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
            throw new ServiceException("Given Film doesn't exist");
        }
        return film.get();
    }

    @Override
    public Film findFilmByTitle(String title) throws ServiceException {
        Optional<Film> film = this.filmDAO.findByTitle(title);
        if (film.isEmpty()) {
            throw new ServiceException("Given Film doesn't exist");
        }
        return film.get();
    }

    @Override
    public List<Film> findFilmByDirectorId(long id) throws ServiceException {
        return this.filmDAO.findByDirectorId(id);
    }

    @Override
    @Transactional
    public FilmDTO updateFilm(long id, FilmForm filmForm) throws ServiceException {
        Optional<Director> director = this.directorDAO.findById(filmForm.getDirectorId());
        if (director.isEmpty()) {
            throw new ServiceException("Director doesn't exist");
        }
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
    public Director findDirectorById(long id) throws ServiceException {
        Optional<Director> director = this.directorDAO.findById(id);
        if (director.isEmpty()) {
            throw new ServiceException("Given Director doesn't exist");
        }
        return director.get();
    }

    @Override
    public Director findDirectorBySurnameAndName(String surname, String name) throws ServiceException {
        Optional<Director> director = this.directorDAO.findBySurnameAndName(surname, name);
        if (director.isEmpty()) {
            throw new ServiceException("Given Director doesn't exist");
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
            List<Film> directorFilms = filmDAO.findByDirectorId(director.getId());
            director.setFilmsProduced(directorFilms);
            boolean newFame = directorFilms.size() >= MIN_NB_FILMS_FAMOUS_DIRECTOR;
            director.setFamous(newFame);
            return directorDAO.update(director.getId(), director);
        } catch (ServiceException e) {
            throw new ServiceException("Director doesn't exist");
        } catch (Throwable e) {
            throw new ServiceException("Can't update famous", e);
        }
    }


    @Override
    @Transactional
    public List<Director> updateDirectorsFamous(List<Director> directors) throws ServiceException {
        try {
            return directors.stream()
                    .map(director -> {
                        try {
                            return updateDirectorFamous(director);
                        } catch (ServiceException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Director::isFamous)
                    .collect(Collectors.toList());
        } catch (Throwable e) {
            System.err.println("update director famous: T");
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
        Optional<Director> optionalDirector = this.directorDAO.findById(id);
        if (optionalDirector.isEmpty()) throw new ServiceException("Director doesn't exist");
        this.directorDAO.delete(id);
    }


    @Override
    public List<Genre> findAllGenres() throws ServiceException {
        return this.genreDAO.findAll();
    }

    @Override
    public Genre findGenreById(long id) throws ServiceException {
        Optional<Genre> genre = this.genreDAO.findById(id);
        if (genre.isEmpty()) {
            throw new ServiceException("Given Genre doesn't exist");
        }
        return genre.get();
    }

    @Override
    @Transactional
    public GenreDTO updateGenre(long id, String name) throws ServiceException {
        Genre genre = this.genreDAO.update(id, name);
        return GenreMapper.convertGenreToGenreDTO(genre);
    }


    @Override
    @Transactional
    public List<Film> findWatchList(long userId) throws ServiceException {
        return this.historyDAO.getWatchList(userId);
    }

    @Override
    @Transactional
    public History addFilmToWatchList(long userId, long filmId) throws ServiceException {
        return this.historyDAO.addFilmToWatchList(userId, filmId);
    }

    @Override
    public void removeFilmFromWatchList(long userId, long filmId) {
        try {
            this.historyDAO.deleteFilm(userId, filmId);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public History rateFilm(long userId, long filmId, int rating) throws ServiceException {
        return this.historyDAO.rateFilm(userId, filmId, rating);
    }

    @Override
    public int getUserRating(long userId, long filmId) throws ServiceException {
        return this.historyDAO.getUserRating(userId, filmId);
    }

    @Override
    public Optional<Double> getMeanRating(long filmId) {
        try {
            return this.historyDAO.getMeanRating(filmId);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Prend une liste de Film et renvoie une int représentant la somme des durées de chaque film
     */
    @Override
    public int getFullWatchTime(List<Film> filmsProduced) {
        return this.historyDAO.getFullWatchTime(filmsProduced);
    }

    /**
     * Prend un array de double et calcule un double représentant la note moyenne, renvoie 0 par défaut;
     */
    @Override
    public Optional<Double> getMeanRating(List<Double> notes) {
        return this.historyDAO.getMeanRating(notes);
    }
}