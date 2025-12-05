package com.ensta.myfilmlist.dao.impl;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.exception.ServiceException;

import java.util.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import javax.sql.DataSource;

import java.lang.StackWalker.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcFilmDAO implements FilmDAO {
    
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.getDataSource());
    
    @Override
    public List<Film> findAll() throws ServiceException {
        String query = "SELECT * FROM Film JOIN Realisateur ON Realisateur.id = Film.realisateur_id;";
        try {
            List<Film> films = this.jdbcTemplate.query(query, (resultSet, rowNum) -> {
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
            });
            if (films.isEmpty()) {
                throw new ServiceException("JdbcFilmDAO::findAll no Film founded");
            }
            return films;
        } catch (DataAccessException e) {
            throw new ServiceException("JdbcFilmDAO::findAll DataAccessException", e);
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
        film.setId(keyHolder.getKey().longValue());
        return film;
    }

    @Override
    public Optional<Film> findById(long id) {
        String query = "SELECT * FROM Film JOIN Realisateur ON Film.realisateur_id = Realisateur.id WHERE Film.id=?";
        try {
            Film film = this.jdbcTemplate.queryForObject(query, (resultSet, rowcolumn) -> {
                Film new_film = new Film();
                new_film.setDuree(resultSet.getInt("duree"));
                new_film.setId(resultSet.getLong("id"));
                new_film.setTitre(resultSet.getString("titre"));
                Realisateur realisateur = new Realisateur();
                realisateur.setCelebre(resultSet.getBoolean("celebre"));
                realisateur.setDateNaissance(resultSet.getTimestamp("date_naissance").toLocalDateTime().toLocalDate());
                realisateur.setId(resultSet.getLong("realisateur_id"));
                realisateur.setNom(resultSet.getString("nom"));
                realisateur.setPrenom(resultSet.getString("prenom"));
                new_film.setRealisateur(realisateur);
                return new_film;
            }, id);
            return Optional.of(film);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public void delete(Film film) {
        String query = "DELETE FROM Film WHERE id=?";
        this.jdbcTemplate.update(query, film.getId());
    }

    @Override
    public List<Film> findByRealisateurId(long realisateur_id) {
        String query = "SELECT * FROM Realisateur JOIN Film ON Film.realisateur_id = Realisateur.id WHERE Realisateur.id=?";
        return this.jdbcTemplate.query(query, (resultSet, rowcolumn) -> {
            Film film = new Film();
            film.setDuree(resultSet.getInt("duree"));
            film.setId(resultSet.getLong("id"));
            film.setTitre(resultSet.getString("titre"));
            Realisateur realisateur = new Realisateur();
            realisateur.setCelebre(resultSet.getBoolean("celebre"));
            realisateur.setDateNaissance(resultSet.getTimestamp("date_naissance").toLocalDateTime().toLocalDate());
            realisateur.setId(resultSet.getLong("realisateur_id"));
            realisateur.setNom(resultSet.getString("nom"));
            realisateur.setPrenom(resultSet.getString("prenom"));
            film.setRealisateur(realisateur);
            return film;
        }, realisateur_id);
    }
}