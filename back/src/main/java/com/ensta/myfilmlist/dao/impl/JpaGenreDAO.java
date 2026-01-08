package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.GenreDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Genre;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JpaGenreDAO implements GenreDAO {
    @PersistenceContext
    private EntityManager entityManager;

    void checkDuplicate(String name) throws ServiceException {
        List<String> nameList = entityManager
                .createQuery("SELECT g.name FROM Genre g", String.class)
                .getResultList();
        if (nameList.contains(name)) {
            throw new ServiceException("Genre already exists");
        }
    }

    /**
     * Returns the list of all Genres present in the database.
     * A ServiceException is thrown in case of an error (can't get Genres, list empty)
     *
     * @return the list of Genres
     */
    @Override
    public List<Genre> findAll() throws ServiceException {
        try {
            List<Genre> genres = entityManager
                    .createQuery("SELECT g FROM Genre g", Genre.class)
                    .getResultList();
            return genres;
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    /**
     * Returns the Genre corresponding to the id argument (or an empty option if there is none)
     *
     * @param id the id of the genre to return
     * @return the corresponding genre
     */
    @Override
    public Optional<Genre> findById(long id) throws ServiceException {
        try {
            return Optional.ofNullable(entityManager.find(Genre.class, id));
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    /**
     * Updates the Genre corresponding to the id argument with a new name
     *
     * @param id   the id of the genre to update
     * @param name the new name to give to the genre
     * @return the corresponding genre
     */
    @Override
    public Genre update(long id, String name) throws ServiceException {
        checkDuplicate(name);
        try {
            Optional<Genre> prev_genre = this.findById(id);
            if (prev_genre.isEmpty()) {
                throw new ServiceException("Genre introuvable");
            }
            Genre genre_to_modify = entityManager.merge(prev_genre.get());
            genre_to_modify.setName(name);
            entityManager.merge(genre_to_modify);
            return genre_to_modify;
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }
}
