package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcFilmDAO implements FilmDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.getDataSource());

    private final RowMapper<Film> rowMapper = (ResultSet resultSet, int rowNum) -> {
        Film film = new Film();
        film.setId(resultSet.getLong("id"));
        film.setTitle(resultSet.getString("title"));
        film.setDuration(resultSet.getInt("duration"));
        Director director = new Director();
        director.setFamous(resultSet.getBoolean("famous"));
        director.setBirthdate(resultSet.getTimestamp("birthdate").toLocalDateTime().toLocalDate());
        director.setId(resultSet.getLong("director_id"));
        director.setSurname(resultSet.getString("surname"));
        director.setName(resultSet.getString("name"));
        film.setDirector(director);
        return film;
    };

    @Override
    public List<Film> findAll() {
        String query = "SELECT * FROM Film JOIN Director ON Director.id = Film.director_id;";
        try {
            return this.jdbcTemplate.query(query, this.rowMapper);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Film save(Film film) {
        String query = "INSERT INTO Film(title, duration, director_id) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, film.getTitle());
            statement.setInt(2, film.getDuration());
            statement.setLong(3, film.getDirector().getId());
            return statement;
        };
        jdbcTemplate.update(creator, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return film;
    }

    @Override
    public Optional<Film> findById(long id) {
        String query = "SELECT * FROM Film JOIN Director ON Film.director_id = Director.id WHERE Film.id=?";
        try {
            Film film = this.jdbcTemplate.queryForObject(query, this.rowMapper, id);
            return Optional.ofNullable(film);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Film> findByTitle(String title) throws ServiceException {
        String query = "SELECT * FROM Film JOIN Director ON Film.director_id = Director.id WHERE Film.title=?";
        try {
            Film film = this.jdbcTemplate.queryForObject(query, this.rowMapper, title);
            return Optional.ofNullable(film);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Film> findByDirectorId(long director_id) {
        String query = "SELECT * FROM Director JOIN Film ON Film.director_id = Director.id WHERE Director.id=?";
        return this.jdbcTemplate.query(query, this.rowMapper, director_id);
    }

    @Override
    public Film update(long id, Film film) throws ServiceException {
        return null;
    }

    @Override
    public void delete(Film film) throws ServiceException {
        String query = "DELETE FROM Film WHERE id=?";
        this.jdbcTemplate.update(query, film.getId());
    }
}