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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class JpaHistoryDAO implements HistoryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    Optional<History> findHistoryByUserIdAndFilmId(long userId, long filmId) {
        List<History> histories = entityManager
                .createQuery("SELECT h FROM History h WHERE h.user.id = :user_id AND  h.film.id = :film_id", History.class)
                .setParameter("user_id", userId)
                .setParameter("film_id", filmId)
                .getResultList();
        History history = histories.get(0);
        return Optional.ofNullable(history);
    }

    /**
     * Returns the list of watched films associated to a user
     *
     * @param  userId   the id of the User
     * @return          the corresponding Films list (of watched films)
     */
    @Override
    public List<Film> getWatchList(long userId) {
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
    public History addFilmToWatchList(long userId, long filmId) throws ServiceException {
        List<Film> watched_films = getWatchList(userId);
        for (Film film : watched_films) {
            if (film.getId() == filmId) {
                throw new ServiceException("Film already in watched list");
            }
        }
        User user = entityManager.find(User.class, userId);
        Film film = entityManager.find(Film.class, filmId);
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
            entityManager.remove(history);
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
        throw new ServiceException("Can't update history.");
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
    public Optional<Integer> getUserRating(long userId, long filmId) throws ServiceException {
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        if (history.isPresent()) {
            return Optional.of(history.get().getRating());
        }
        throw new ServiceException("Can't get rating.");
    }

    @Override
    public Optional<Double> getMeanRating(long filmId) {
        List<Double> ratings = entityManager.createQuery("SELECT h.rating FROM History h WHERE h.film.id = :film_id", Integer.class)
                .setParameter("film_id", filmId)
                .getResultList()
                .stream()
                .map(Double::valueOf)
                .toList();
        return getMeanRating(ratings);
    }

    @Override
    public int getFullWatchTime(List<Film> filmsProduced) {
        return filmsProduced
                .stream()
                .map(Film::getDuration)
                .reduce(0, Integer::sum);
    }

    @Override
    public Optional<Double> getMeanRating(List<Double> notes) {
        if (notes.isEmpty()) {
            return Optional.empty();
        }
        double noteMoyenne = notes.stream()
                .reduce(0.0, Double::sum);
        return Optional.of(BigDecimal.valueOf(noteMoyenne / notes.size()).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}