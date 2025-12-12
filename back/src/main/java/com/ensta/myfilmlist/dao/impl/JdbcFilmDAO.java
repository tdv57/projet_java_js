package com.ensta.myfilmlist.dao.impl;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.exception.ServiceException;

import java.util.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.persistence.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcFilmDAO implements FilmDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.getDataSource());

    private final RowMapper<Film> rowMapper = (ResultSet resultSet, int rowNum) -> {
        Film film = new Film();
        film.setId(resultSet.getLong("id"));
        film.setTitre(resultSet.getString("titre"));
        film.setDuree(resultSet.getInt("duree"));
        Realisateur realisateur = new Realisateur();
        realisateur.setCelebre(resultSet.getBoolean("celebre"));
        realisateur.setDateNaissance(resultSet.getTimestamp("date_naissance").toLocalDateTime().toLocalDate());
        realisateur.setId(resultSet.getLong("realisateur_id"));
        realisateur.setNom(resultSet.getString("nom"));
        realisateur.setPrenom(resultSet.getString("prenom"));
        film.setRealisateur(realisateur);
        return film;
    };

    @Override
    public List<Film> findAll() throws ServiceException {
        String query = "SELECT * FROM Film JOIN Realisateur ON Realisateur.id = Film.realisateur_id;";
        try {
            List<Film> films = this.jdbcTemplate.query(query, this.rowMapper);
            if (films.isEmpty()) {
                throw new ServiceException("JdbcFilmDAO::findAll no Film founded");
            }
            return films;
        } catch (DataAccessException e) {
            throw new ServiceException("Erreur pour trouver tous les films", e);
        }
    }

    @Override
    public Film save(Film film) {
        String query = "INSERT INTO Film(titre, duree, realisateur_id) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, film.getTitre());
            statement.setInt(2, film.getDuree());
            statement.setLong(3, film.getRealisateur().getId());
            return statement;
        };
        jdbcTemplate.update(creator, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return film;
    }

    @Override
    public Optional<Film> findById(long id) {
        String query = "SELECT * FROM Film JOIN Realisateur ON Film.realisateur_id = Realisateur.id WHERE Film.id=?";
        try {
            Film film = this.jdbcTemplate.queryForObject(query, this.rowMapper, id);
            return Optional.of(film);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Film> findByTitle(String title) {
        String query = "SELECT * FROM Film JOIN Realisateur ON Film.realisateur_id = Realisateur.id WHERE Film.titre=?";
        try {
            Film film = this.jdbcTemplate.queryForObject(query, this.rowMapper, title);
            return Optional.of(film);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Film> findByRealisateurId(long realisateur_id) {
        String query = "SELECT * FROM Realisateur JOIN Film ON Film.realisateur_id = Realisateur.id WHERE Realisateur.id=?";
        return this.jdbcTemplate.query(query, this.rowMapper, realisateur_id);
    }

    @Override
    public Film update(long id, Film film) throws ServiceException {
        return null;
    }
    
    @Override
    public void delete(Film film) {
        String query = "DELETE FROM Film WHERE id=?";
        this.jdbcTemplate.update(query, film.getId());
    }
}