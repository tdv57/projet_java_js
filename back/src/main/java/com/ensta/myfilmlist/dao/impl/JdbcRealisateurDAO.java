package com.ensta.myfilmlist.dao.impl;

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
import java.util.*;

import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.persistence.ConnectionManager;

@Repository
public class JdbcRealisateurDAO implements RealisateurDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.getDataSource());

    private final RowMapper<Realisateur> rowMapper = (ResultSet resultSet, int rowNum) -> {
        Realisateur realisateur = new Realisateur();
        realisateur.setId(resultSet.getLong("id"));
        realisateur.setNom(resultSet.getString("nom"));
        realisateur.setPrenom(resultSet.getString("prenom"));
        realisateur.setDateNaissance(resultSet.getTimestamp("date_naissance").toLocalDateTime().toLocalDate());
        realisateur.setCelebre(resultSet.getBoolean("celebre"));
        return realisateur;
    };


    @Override
    public List<Realisateur> findAll() {
        String query = "SELECT * FROM Realisateur;";
        return jdbcTemplate.query(query, this.rowMapper);
    }

    @Override
    public Realisateur findByNomAndPrenom(String nom, String prenom) {
        String query = "SELECT * FROM Realisateur WHERE prenom LIKE ? AND nom LIKE ?";
        try {
            return jdbcTemplate.queryForObject(query,this.rowMapper,prenom, nom);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return null;
        }
    }

    @Override
    public Optional<Realisateur> findById(long id) {
        String query = "SELECT * FROM Realisateur WHERE id=?";
        try {
            Realisateur realisateur = jdbcTemplate.queryForObject(query, this.rowMapper, id);
            return Optional.of(realisateur);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public Realisateur update(Realisateur realisateur) {
        String query = "UPDATE Realisateur SET prenom=?, nom=?, date_naissance=?, celebre=? WHERE id=?";
        this.jdbcTemplate.update(query,
                                realisateur.getPrenom(), 
                                realisateur.getNom(), 
                                realisateur.getDateNaissance(), 
                                realisateur.isCelebre(), 
                                realisateur.getId()
                            );
        return realisateur;
    }

    @Override
    public Realisateur save(Realisateur realisateur) {
        String query = "INSERT INTO Realisateur(nom, prenom, date_naissance, celebre) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, realisateur.getNom());
            statement.setString(2, realisateur.getPrenom());
            statement.setTimestamp(3, Timestamp.valueOf(realisateur.getDateNaissance().atStartOfDay()));
            statement.setBoolean(4, realisateur.isCelebre());
            return statement;
        };
        jdbcTemplate.update(creator, keyHolder);
        realisateur.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return realisateur;
    }
}