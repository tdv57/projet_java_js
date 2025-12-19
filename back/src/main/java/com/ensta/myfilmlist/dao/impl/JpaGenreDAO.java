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

    /**
     * Returns the list of all Genres present in the database.
     * A ServicException is thrown in case of an error (can't get Genres, list empty)
     *
     * @return      the list of Genres
     */
    @Override
    public List<Genre> findAll() throws ServiceException {
        List<Genre> genres =  entityManager
                .createQuery("SELECT g FROM Genre g")
                .getResultList();
        if (genres.isEmpty()) {
            throw new ServiceException("Impossible de trouver les genres");
        }
        return genres;
    }

    /**
     * Returns the Genre corresponding to the id argument (or an empty option if there is none)
     *
     * @param  id   the id of the genre to return
     * @return      the corresponding genre
     */
    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }

    /**
     * Updates the Genre corresponding to the id argument with a new name
     *
     * @param  id   the id of the genre to update
     * @param  nom  the new name to give to the genre
     * @return      the corresponding genrea
     */
    @Override
    public Genre update(long id, String nom) throws ServiceException{
        Optional<Genre> prev_genre = this.findById(id);
        if  (prev_genre.isEmpty()) {
            throw new ServiceException("Impossible de mettre à jour le genre");
        }
        Genre genre_to_modify = entityManager.merge(prev_genre.get());
        genre_to_modify.setNom(nom);
        entityManager.merge(genre_to_modify);
        return genre_to_modify;
    }
}
