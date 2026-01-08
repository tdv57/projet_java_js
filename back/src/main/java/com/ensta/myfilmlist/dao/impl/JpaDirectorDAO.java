package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.DirectorDAO;
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
public class JpaDirectorDAO implements DirectorDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public JpaDirectorDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    void checkDuplicate(Long id, Director director) throws ServiceException {
        Optional<Director> another = findByNameAndSurname(director.getName(), director.getSurname());
        if (another.isPresent() && another.get().getId() != id) {
            throw new ServiceException("Director already exists");
        }
    }

    /**
     * Returns the list of all Directors present in the database.
     * A ServiceException is thrown in case of an error (can't get Directors, list empty)
     *
     * @return the list of Directors
     */
    @Override
    public List<Director> findAll() throws ServiceException {
        try {
            return entityManager.createQuery("SELECT r FROM Director r", Director.class).getResultList();
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    /**
     * Returns the Director corresponding to the name and surname arguments (or an empty option if there is none)
     *
     * @param surname the name of the director to return
     * @param name    the surname of the director to return
     * @return the corresponding director
     */
    @Override
    public Optional<Director> findByNameAndSurname(String name, String surname) throws ServiceException {
        try {
            List<Director> directors = entityManager.createQuery("SELECT r FROM Director r WHERE surname = :surname AND name = :name", Director.class)
                    .setParameter("surname", surname)
                    .setParameter("name", name)
                    .getResultList();
            if (directors.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(directors.get(0));
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    /**
     * Returns the Director corresponding to the id argument (or an empty option if there is none)
     *
     * @param id the id of the director to return
     * @return the corresponding director
     */
    @Override
    public Optional<Director> findById(long id) throws ServiceException {
        try {
            return Optional.ofNullable(entityManager.find(Director.class, id));
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    /**
     * Updates the Director corresponding to the id argument with the new director
     *
     * @param id       the id of the director to update
     * @param director the state of the director updated
     * @return the corresponding director
     */
    @Override
    public Director update(long id, Director director) throws ServiceException {
        checkDuplicate(id, director);
        Optional<Director> prev_director = this.findById(id);
        if (prev_director.isEmpty()) {
            throw new ServiceException("Director doesn't exist");
        }
        try {
            Director director_to_modify = entityManager.merge(prev_director.get());
            director_to_modify.setSurname(director.getSurname());
            director_to_modify.setName(director.getName());
            director_to_modify.setBirthdate(director.getBirthdate());
            entityManager.merge(director_to_modify);
            return director_to_modify;
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    /**
     * Creates a Director in the database based on a director argument
     *
     * @param director the director to register
     * @return the director created
     */
    @Override
    public Director save(Director director) throws ServiceException {
        checkDuplicate(0L, director);
        try {
            entityManager.persist(director);
            return director;
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    /**
     * Deletes a Director in the database based on the id argument
     *
     * @param id the id of the Director to delete
     */
    @Override
    public void delete(long id) throws ServiceException {
        try {
            Director managedDirector = entityManager.find(Director.class, id);
            if (managedDirector != null) {
                List<Film> films = entityManager
                        .createQuery("SELECT f FROM Film f WHERE director.id = :director_id", Film.class)
                        .setParameter("director_id", id)
                        .getResultList();
                films.forEach(film -> entityManager.remove(film));
                entityManager.remove(managedDirector);
            }
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }
}