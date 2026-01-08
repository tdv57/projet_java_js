package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.dao.*;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.form.UserForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.GenreMapper;
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.mapper.UserMapper;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.service.*;
import com.ensta.myfilmlist.dto.*;

import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private UserDAO userDAO;

    @Autowired
    private HistoryDAO historyDAO;

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException {
        Film film = filmMapper.convertFilmFormToFilm(filmForm);
        Optional<Director> director = directorDAO.findById(filmForm.getDirectorId());
        if (director.isEmpty()) {
            throw new ServiceException("Director can't be found.");
        }
        film.setDirector(director.get());
        Optional<Genre> genre = genreDAO.findById(filmForm.getGenreId());
        if (genre.isEmpty()) {
            throw new ServiceException("Genre can't be found.");
        }
        film.setGenre(genre.get());
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
            throw new  ServiceException ("Film can't be found.");
        }
        return film.get();
    }

    @Override
    public Film findFilmByTitle(String title) throws ServiceException {
        Optional<Film> film = this.filmDAO.findByTitle(title);
        if (film.isEmpty()) {
            throw new ServiceException("Film can't be found.");
        }
        return film.get();
    }

    @Override
    public List<Film> findFilmByDirectorId(long id) throws ServiceException {
        List<Film> films = this.filmDAO.findByDirectorId(id);
        if (films.isEmpty()) {
            throw new ServiceException("Director isn't related to any film.");
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
    public DirectorDTO createDirector(DirectorForm directorForm) {
        Director director = DirectorMapper.convertDirectorFormToDirector(directorForm);
        director = this.directorDAO.save(director);
        return DirectorMapper.convertDirectorToDirectorDTO(director);
    }

    @Override 
    public List<Director> findAllDirectors() {
        return this.directorDAO.findAll();
    }

    @Override
    public Director findDirectorById(long id) throws ServiceException {
        Optional<Director> director = this.directorDAO.findById(id);
        if (director.isEmpty()) {
            throw new  ServiceException ("Director can't be found.");
        }
        return director.get();
    }

    @Override
    public Director findDirectorByNameAndSurname(String name, String surname) throws ServiceException {
        Optional<Director> director = this.directorDAO.findByNameAndSurname(name, surname);
        if (director.isEmpty()) {
            throw new  ServiceException ("Director can't be found.");
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
            List<Film> DirectorsFilms = filmDAO.findByDirectorId(director.getId());
            director.setFilmsProduced(DirectorsFilms);
            director.setFamous(director.getFilmsProduced().size() >= MIN_NB_FILMS_FAMOUS_DIRECTOR);
            return directorDAO.update(director.getId(), director);
        } catch (ServiceException e) {
            throw new ServiceException("Réalisateur inexistant");
        } catch(Throwable e) {
            throw new ServiceException("Director can't be updated.", e);
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
                        } catch(ServiceException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(Director::isFamous)
                    .collect(Collectors.toList());
        } catch (Throwable e) {
            throw new ServiceException("Director can't be updated.", e);
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
        if (optionalDirector.isEmpty()) throw new ServiceException("Can't get Director");
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
            throw new  ServiceException ("Genre can't be found.");
        }
        return genre.get();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public GenreDTO updateGenre(long id, String name) throws ServiceException {
        Genre genre = this.genreDAO.update(id, name);
        return GenreMapper.convertGenreToGenreDTO(genre);
    }


    @Override
    @Transactional
    public List<Film> findWatchList(long userId) throws ServiceException{
        return this.historyDAO.getWatchList(userId);
    }

    @Override
    @Transactional
    public History addFilmToWatchList(long userId, long filmId) throws ServiceException{
        return this.historyDAO.addFilmToWatchList(userId, filmId);
    }

    @Override
    public void removeFilmFromWatchList(long userId, long filmId) {
        this.historyDAO.deleteFilm(userId, filmId);
    }

    @Override
    @Transactional
    public History rateFilm(long userId, long filmId, int rating) throws ServiceException {
        return this.historyDAO.rateFilm(userId, filmId, rating);
    }

    @Override
    public Optional<Integer> getRate(long userId, long filmId) throws ServiceException {
        return this.historyDAO.getRate(userId, filmId);
    }

        
    @Override 
    public Optional<Double> getFilmMeanRating(long filmId) throws ServiceException {
        List<Integer> notes = this.historyDAO.getNotesByFilmId(filmId);
        return calculateMeanRating(notes.stream().map(Integer::doubleValue).collect(Collectors.toList()));
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAllUsers() throws ServiceException {
        return this.userDAO.findAll();
    }

    @Override
    public UserDTO createUser(UserForm userForm) throws ServiceException {
        User user = UserMapper.convertUserFormToUser(userForm);
        user = this.userDAO.save(user);
        return UserMapper.convertUserToUserDTO(user);
    }

    @Override
    public User findUserById(long id) throws ServiceException {
        Optional<User> user = this.userDAO.findById(id);
        if (user.isEmpty()) {
            throw new  ServiceException ("User can't be found.");
        }
        return user.get();
    }

    @Override
    public User findUserBySurnameAndName(String name, String surname) throws ServiceException {
        Optional<User> user = this.userDAO.findByNameAndSurname(name, surname);
        if (user.isEmpty()) {
            throw new  ServiceException ("User can't be found.");
        }
        return user.get();
    }

    @Override
    public UserDTO updateUser(long id, UserForm userForm) throws ServiceException {
        User new_user = UserMapper.convertUserFormToUser(userForm);
        User user = this.userDAO.update(id, new_user);
        return UserMapper.convertUserToUserDTO(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO setUserAsAdmin(long id) throws ServiceException {
        try {
            Optional<User> user = userDAO.findById(id);
            if (user.isEmpty()) {
                throw new  ServiceException ("User can't be found.");
            }
            user.get().setRoles("USER, ADMIN");
            return UserMapper.convertUserToUserDTO(userDAO.update(id, user.get()));
        } catch(Throwable e) {
            throw new ServiceException("User can't be updated.", e);
        }
    }

    @Override
    public void deleteUser(long id) throws ServiceException {
        this.userDAO.delete(id);
    }


    /**
     * Prend une liste de Film et renvoie une int représentant la somme des durées de chaque film
     */
    @Override
    public int calculateTotalDuration(List<Film> filmsProduced) {
        return filmsProduced.stream()
                .map(Film::getDuration)
                .reduce(0, Integer::sum);
    }

    /**
     * Prend un array de double et calcule un double représentant la note moyenne, renvoie 0 par défaut;
     */
    @Override
    public Optional<Double> calculateMeanRating(List<Double> notes) {
        if (notes.isEmpty()) {
            return Optional.empty();
        }

        double noteMoyenne = notes.stream()
                .reduce(0.0, Double::sum);
        return Optional.of(BigDecimal.valueOf(noteMoyenne / notes.size()).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}