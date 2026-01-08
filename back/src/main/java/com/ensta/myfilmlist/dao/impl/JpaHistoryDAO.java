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

    @Override
    public List<Film> getWatchList(long userId) throws ServiceException {
        try {
            return entityManager
                    .createQuery("SELECT h.film FROM History h WHERE h.user.id = :user_id", Film.class)
                    .setParameter("user_id", userId)
                    .getResultList();
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    @Override
    public History addFilmToWatchList(long userId, long filmId) throws ServiceException {
        List<Film> watched_films = getWatchList(userId);
        for (Film film : watched_films) {
            if (film.getId() == filmId) {
                throw new ServiceException("Film already in watched list");
            }
        }
        try {
            User user = entityManager.find(User.class, userId);
            Film film = entityManager.find(Film.class, filmId);
            History history = new History(user, film);
            entityManager.persist(history);
            return history;
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    @Override
    public void deleteFilm(long userId, long filmId) throws ServiceException {
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        try {
            if (history.isPresent()) {
                entityManager.remove(history);
            }
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
    }

    @Override
    public History rateFilm(long userId, long filmId, int rating) throws ServiceException {
        if (rating < 0) {
            throw new ServiceException("Film's rating should be positive");
        }
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        if (history.isPresent()) {
            History anhistory = history.get();
            anhistory.setRating(rating);
            try {
                entityManager.merge(anhistory);
            } catch (Exception e) {
                throw new ServiceException("Internal Server Error");
            }
            return anhistory;
        } else {
            throw new ServiceException("Film not in watched list");
        }
    }

    @Override
    public int getUserRating(long userId, long filmId) throws ServiceException {
        Optional<History> history = findHistoryByUserIdAndFilmId(userId, filmId);
        if (history.isPresent()) {
            return history.get().getRating();
        }
        throw new ServiceException("Can't get Film's rating");
    }

    @Override
    public Optional<Double> getMeanRating(long filmId) throws ServiceException {
        try {
            List<Double> ratings = entityManager.createQuery("SELECT h.rating FROM History h WHERE h.film.id = :film_id", Integer.class)
                    .setParameter("film_id", filmId)
                    .getResultList()
                    .stream()
                    .filter(rating -> rating > 0)
                    .map(Double::valueOf)
                    .toList();
            return getMeanRating(ratings);
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error");
        }
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