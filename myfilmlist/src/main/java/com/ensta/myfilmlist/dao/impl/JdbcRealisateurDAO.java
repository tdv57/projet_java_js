package com.ensta.myfilmlist.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;

import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.service.impl.MyFilmsServiceImpl;

@Repository
public class JdbcRealisateurDAO implements RealisateurDAO {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.getDataSource());
 
    @Override
    public List<Realisateur> findAll() {
        String query = "SELECT * FROM Realisateur;";
        List<Realisateur> realisateurs = jdbcTemplate.query(query, (resultSet, rownum) -> {
            Realisateur realisateur = new Realisateur();
            realisateur.setCelebre(resultSet.getBoolean("celebre"));
            realisateur.setDateNaissance(resultSet.getTimestamp("date_naissance").toLocalDateTime().toLocalDate());            
            realisateur.setId(resultSet.getLong("id"));
            realisateur.setNom(resultSet.getString("nom"));
            realisateur.setPrenom(resultSet.getString("prenom"));
            return realisateur;
        });
        return realisateurs;
    }

    @Override
    public Realisateur findByNomAndPrenom(String nom, String prenom) {
        String query = "SELECT * FROM Realisateur WHERE prenom LIKE ? AND nom LIKE ?";
        Realisateur realisateur = null;
        try {
            realisateur =jdbcTemplate.queryForObject(query,(resultSet, rownum) -> {
                Realisateur temp_realisateur = new Realisateur();
                temp_realisateur.setCelebre(resultSet.getBoolean("celebre"));
                temp_realisateur.setDateNaissance(resultSet.getTimestamp("date_naissance").toLocalDateTime().toLocalDate());
                temp_realisateur.setId(resultSet.getLong("id"));
                temp_realisateur.setNom(resultSet.getString("nom"));
                temp_realisateur.setPrenom(resultSet.getString("prenom"));
                return temp_realisateur;
            },prenom, nom);
            return realisateur;
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return null;
        }
    }

    @Override
    public Optional<Realisateur> findById(long id) {
        String query = "SELECT * FROM Realisateur WHERE id=?";
        Realisateur realisateur = null;
        try {
            realisateur = jdbcTemplate.queryForObject(query, (rs, rownum) -> {
                Realisateur temp_realisateur = new Realisateur();
                temp_realisateur.setCelebre(rs.getBoolean("celebre"));
                temp_realisateur.setDateNaissance(rs.getTimestamp("date_naissance").toLocalDateTime().toLocalDate());
                temp_realisateur.setId(rs.getLong("id"));
                temp_realisateur.setNom(rs.getString("nom"));
                temp_realisateur.setPrenom(rs.getString("prenom"));
                return temp_realisateur;
            }, id);
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
        realisateur.setId(keyHolder.getKey().longValue());
        return realisateur;
    }
}