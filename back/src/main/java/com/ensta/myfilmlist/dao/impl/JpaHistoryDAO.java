package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.HistoryDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.History;
import com.ensta.myfilmlist.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JpaHistoryDAO implements HistoryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<History> findHistoryByUserIdAndFilmId(long userId, long filmId) {
        List<History> histories = entityManager
                    .createQuery("SELECT h FROM History h WHERE h.user.id = :user_id AND  h.film.id = :film_id", History.class)
                    .setParameter("user_id", userId)
                    .setParameter("film_id", filmId)
                    .getResultList();
        if (histories.isEmpty()) return Optional.empty();
        return Optional.ofNullable(histories.get(0));
    }

    /**
     * Returns the list of watched films associated to a user
     *
     * @param  userId   the id of the User
     * @return          the corresponding Films list (of watched films)
     */
    @Override
    public List<Film> getWatchList(long userId) throws ServiceException{
        User user = entityManager.find(User.class, userId);
        if (user == null) throw new ServiceException("Utilisateur introuvable");
        return entityManager
                .createQuery("SELECT h.film FROM History h WHERE h.user.id = :user_id", Film.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    /**
     * Add a film to the watched list of a User.
     * Returns the History related to the pair (Film, User).
     *
     * @param  userId   the id of the User
     * @param  filmId   the id og the Film to add
     * @return          the corresponding History (pair Film-User)
     */
    @Override
    public History addFilmToWatchList(long userId, long filmId) throws ServiceException{
        User user = entityManager.find(User.class, userId);
        if (user == null) throw new ServiceException("Utilisateur introuvable");
        Film film = entityManager.find(Film.class, filmId);
        if (film == null) throw new ServiceException("Film introuvable");
        History history = new History(user, film);
        entityManager.persist(history);
        return history;
    }

    /**
     * Deletes a Film from the watched list of a User
     *
     * @param  userId   the id of the User
     * @param  filmId   the id of the Film to delete
     */
    @Override
    public void deleteFilm(long userId, long filmId) {
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        if (history.isPresent()) {
            entityManager.remove(history.get());
        }
    }

    /**
     * Adds a rating to a Film from a User.
     * The film must be in the watched list of the User to be rated.
     *
     * @param  userId   the id of the User
     * @param  filmId   the id of the Film to be rated
     * @param  rating   the (new) rating to give to the Film
     * @return          the corresponding History (pair Film-User)
     */
    @Override
    public History rateFilm(long userId, long filmId, int rating) throws ServiceException {
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        if (history.isPresent()) {
            History anhistory = history.get();
            anhistory.setRating(rating);
            entityManager.merge(anhistory);
            return anhistory;
        }
        throw new ServiceException("Historique introuvable");
    }

    /**
     * Get the rating of a Film from a User.
     * The film must be in the watched list of the User to have a rating.
     * Rating could not be existing so the function return an Optional
     *
     * @param  userId   the id of the User
     * @param  filmId   the id of the Film
     * @return          the corresponding rating or nothing
     */
    @Override
    public Optional<Integer> getRate(long userId, long filmId) throws ServiceException {
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        if (history.isPresent()) {
            return Optional.of(history.get().getRating());
        }
        throw new ServiceException("Note introuvable");
    }

    @Override
    public List<Integer> getRatesByFilmId(long filmId) throws ServiceException {
        Film film = entityManager.find(Film.class, filmId);
        if (film == null) {
            throw new ServiceException("Film inexistant");
        }
        List<Integer> notes = entityManager.createQuery("SELECT h.rating FROM History h WHERE h.film.id = :film_id AND h.rating IS NOT NULL", Integer.class)
                    .setParameter("film_id", film.getId())
                    .getResultList();
        return notes;
    }
}