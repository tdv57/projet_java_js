package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.model.Film;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JpaFilmDAO implements FilmDAO {
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * Returns the list of all Films present in the database.
     * A ServicException is thrown in case of an error (can't get Films, list empty)
     *
     * @return the list of Films
     */
    @Override
    public List<Film> findAll() {
        return entityManager
                .createQuery("SELECT f FROM Film f", Film.class)
                .getResultList();
    }

    /**
     * Creates a Film in the database based on a film argument
     *
     * @param film the film to register
     * @return the film created
     */
    @Override
    public Film save(Film film) throws ServiceException {
        Director director = film.getDirector();
        List<Film> films = findByDirectorId(director.getId());
        for (Film existing_film : films) {
            if (existing_film.getTitle().trim().equals(film.getTitle().trim())) {
                throw new ServiceException("Film already exists");
            }
        }
        entityManager.persist(film);
        return film;
    }

    /**
     * Returns the Film corresponding to the id argument (or an empty option if there is none)
     *
     * @param id the id of the film to return
     * @return the corresponding film
     */
    @Override
    public Optional<Film> findById(long id) {
        return Optional.ofNullable(entityManager.find(Film.class, id));
    }

    /**
     * Returns the Film corresponding to the title argument (or an empty option if there is none)
     *
     * @param title the title of the film to return
     * @return the corresponding film
     */
    @Override
    public Optional<Film> findByTitle(String title) {
        List<Film> films = entityManager
                .createQuery("SELECT f FROM Film f WHERE f.title = :title", Film.class)
                .setParameter("title", title)
                .getResultList();
        if (films.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(films.get(0));
    }

    /**
     * Returns the list of Films that were realised by the Director correpsonding to the director_id argument.
     *
     * @param director_id the id of the Director
     * @return the corresponding films
     * @throws ServiceException in case the director doesn't exist
     */
    @Override
    public List<Film> findByDirectorId(long director_id) throws ServiceException {
        Director director = entityManager.find(Director.class, director_id);
        if (director == null) {
            throw new ServiceException("Director doesn't exist");
        }
        return entityManager
                .createQuery("SELECT f FROM Film f WHERE director.id = :director_id", Film.class)
                .setParameter("director_id", director_id)
                .getResultList();
    }

    /**
     * Updates the Film corresponding to the id argument with the film argument
     *
     * @param id   the id of the film to update
     * @param film the state of the film updated
     * @return the corresponding film updated
     * @throws ServiceException in case the film doesn't exist
     */
    @Override
    public Film update(long id, Film film) throws ServiceException {
        Optional<Film> prev_film = this.findById(id);
        if (prev_film.isEmpty()) {
            throw new ServiceException("Film doesn't exist");
        }
        Film film_to_modify = entityManager.merge(prev_film.get());
        film_to_modify.setTitle(film.getTitle());
        film_to_modify.setDuration(film.getDuration());
        film_to_modify.setDirector(film.getDirector());
        entityManager.merge(film_to_modify);
        return film_to_modify;
    }

    /**
     * Deletes the Film corresponding to the film argument
     *
     * @param film the film to delete
     */
    @Override
    public void delete(Film film) {
        Film managedFilm = entityManager.find(Film.class, film.getId());
        if (managedFilm != null) {
            entityManager.remove(managedFilm);
        }
    }

}