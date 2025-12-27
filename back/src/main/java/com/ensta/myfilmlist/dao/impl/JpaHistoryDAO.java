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

    Optional<History> findHistoryByUserIdAndFilmId(long userId, long filmId) {
        List<History> histories = entityManager
                    .createQuery("SELECT h FROM History h WHERE h.user.id = :user_id AND  h.film.id = :film_id")
                    .setParameter("user_id", userId)
                    .setParameter("film_id", filmId)
                    .getResultList();
        History history = histories.get(0);
        return Optional.ofNullable(history);
    }

    @Override
    public List<Film> getWatchList(long userId) throws ServiceException {
        List<Film> filmIds = entityManager
                .createQuery("SELECT h.film FROM History h WHERE h.user.id = :user_id")
                .setParameter("user_id", userId)
                .getResultList();
        return filmIds;

    }

    @Override
    public History addFilmToWatchList(long userId, long filmId) throws ServiceException {
        User user = entityManager.find(User.class, userId);
        Film film = entityManager.find(Film.class, filmId);
        History history = new History(user, film);
        entityManager.persist(history);
        return history;
    }

    @Override
    public void deleteFilm(long userId, long filmId) throws ServiceException {
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        if (history.isPresent()) {
            entityManager.remove(history);
        }
    }

    @Override
    public History rateFilm(long userId, long filmId, int rating) throws ServiceException {
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        if (history.isPresent()) {
            History anhistory = history.get();
            anhistory.setRating(rating);
            entityManager.merge(anhistory);
            return anhistory;
        }
        throw new ServiceException("L'historique n'a pas pu être mis à jour.");
    }

    @Override
    public Optional<Integer> getNote(long userId, long filmId) throws ServiceException {
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        if (history.isPresent()) {
            return Optional.ofNullable(history.get().getRating());
        }
        throw new ServiceException("La note du film n'a pas pu être trouvée.");
    }
}