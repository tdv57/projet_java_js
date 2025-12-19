package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.exception.ServiceException;
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
     * @return      the list of Films
     */
    @Override
    public List<Film> findAll() throws ServiceException {
        List<Film> films =  entityManager
                .createQuery("SELECT f FROM Film f")
                .getResultList();
        if (films.isEmpty()) {
            throw new ServiceException("Impossible de trouver les films");
        }
        return films;
    }

    /**
     * Creates a Film in the database based on a film argument
     *
     * @param  film the film to register
     * @return      the film created
     */
    @Override
    public Film save(Film film){
        entityManager.persist(film);
        return film;
    }

    /**
     * Returns the Film corresponding to the id argument (or an empty option if there is none)
     *
     * @param  id   the id of the film to return
     * @return      the corresponding film
     */
    @Override
    public Optional<Film> findById(long id){
        return Optional.ofNullable(entityManager.find(Film.class, id));
    }

    /**
     * Returns the Film corresponding to the title argument (or an empty option if there is none)
     *
     * @param  titre    the title of the film to return
     * @return          the corresponding film
     */
    @Override
    public Optional<Film> findByTitle(String titre){
        List<Film> films = entityManager
                .createQuery("SELECT f FROM Film f WHERE f.titre = :titre")
                .setParameter("titre", titre)
                .getResultList();
        return Optional.ofNullable(films.get(0));
    }

    /**
     * Returns the list of Films that were realised by the Realisateur correpsonding to the realisateur_id argument.
     *
     * @param  realisateur_id   the id of the Realisateur
     * @return                  the corresponding films
     */
    @Override
    public List<Film> findByRealisateurId(long realisateur_id){
      return entityManager
              .createQuery("SELECT f FROM Film f WHERE realisateur.id = :realisateur_id")
              .setParameter("realisateur_id", realisateur_id)
              .getResultList();
    }

    /**
     * Updates the Film corresponding to the id argument with the film argument
     *
     * @param  id   the id of the film to update
     * @param film  the state of the film updated
     * @return      the corresponding film updated
     */
    @Override
    public Film update(long id, Film film)  throws ServiceException {
        Optional<Film> prev_film = this.findById(id);
        if  (prev_film.isEmpty()) {
            throw new ServiceException("Impossible de mettre à jour le film");
        }
        Film film_to_modify = entityManager.merge(prev_film.get());
        film_to_modify.setTitre(film.getTitre());
        film_to_modify.setDuree(film.getDuree());
        film_to_modify.setRealisateur(film.getRealisateur());
        entityManager.merge(film_to_modify);
        return film_to_modify;
    }

    /**
     * Deletes the Film corresponding to the film argument
     *
     * @param  film the film to delete
     */
    @Override
    public void delete(Film film){
        Film managedFilm = entityManager.find(Film.class, film.getId());
        if (managedFilm != null) {
            entityManager.remove(managedFilm);
        }
    }

}