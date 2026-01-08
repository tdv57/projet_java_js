package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.DirectorDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcDirectorDAO implements DirectorDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.getDataSource());

    private final RowMapper<Director> rowMapper = (ResultSet resultSet, int rowNum) -> {
        Director director = new Director();
        director.setId(resultSet.getLong("id"));
        director.setSurname(resultSet.getString("surname"));
        director.setName(resultSet.getString("name"));
        director.setBirthdate(resultSet.getTimestamp("birthdate").toLocalDateTime().toLocalDate());
        director.setFamous(resultSet.getBoolean("famous"));
        return director;
    };

    @Override
    public List<Director> findAll() {
        String query = "SELECT * FROM Director;";
        return jdbcTemplate.query(query, this.rowMapper);
    }

    @Override
    public Optional<Director> findBySurnameAndName(String surname, String name) {
        String query = "SELECT * FROM Director WHERE name=? AND surname=?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, this.rowMapper, name, surname));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Director> findById(long id) {
        String query = "SELECT * FROM Director WHERE id=?";
        try {
            Director director = jdbcTemplate.queryForObject(query, this.rowMapper, id);
            return Optional.ofNullable(director);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public Director update(long id, Director director) throws ServiceException {
        String query = "UPDATE Director SET name=?, surname=?, birthdate=?, famous=? WHERE id=?";
        try {
            this.jdbcTemplate.update(query,
                    director.getName(),
                    director.getSurname(),
                    director.getBirthdate(),
                    director.isFamous(),
                    director.getId()
            );
            return director;
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new ServiceException("Can't update Director");
        }
    }

    @Override
    public Director save(Director director) {
        String query = "INSERT INTO Director(surname, name, birthdate, famous) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, director.getSurname());
            statement.setString(2, director.getName());
            statement.setTimestamp(3, Timestamp.valueOf(director.getBirthdate().atStartOfDay()));
            statement.setBoolean(4, director.isFamous());
            return statement;
        };
        jdbcTemplate.update(creator, keyHolder);
        director.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return director;
    }

    @Override
    public void delete(long id) {
        // Not used
    }
}