package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dao.impl.*;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.dao.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.form.*;
import com.ensta.myfilmlist.mapper.FilmMapper;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.Provider.Service;
import java.util.Optional;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class FilmsDAOTests {

    @Autowired
    private FilmDAO filmDAO;
    @Autowired
    private RealisateurDAO realisateurDAO;

    private static final Logger logger = LoggerFactory.getLogger(FilmsDAOTests.class);
    private static int count = 0;

    @Autowired
    private FilmMapper filmMapper;

    @BeforeEach 
    void setUp() {
        System.out.println("\n");
        System.out.println("Debut test n°" + count);
        System.out.println("\n");
    }

    @AfterEach 
    void setDown() {
        System.out.println("\n");
        System.out.println("Fin test n°" + count);
        count ++;
        System.out.println("\n");
    }

    @Test  
    void printDatabaseTest() {
        try {
        filmDAO.findAll().forEach(film -> System.out.println(film));
        System.out.println("\n");
        realisateurDAO.findAll().forEach(realisateur -> System.out.println(realisateur));
        } catch (ServiceException e) {
            System.out.println("Erreur interne");
        }
    }

    @Test 
    void whenCreateFilm_thenShouldHaveNewFilmInDB() {
        FilmForm filmForm = new FilmForm();
        filmForm.setDuree(15);
        filmForm.setRealisateurId(2);
        filmForm.setTitre("titre");
        try {
            filmDAO.save(filmMapper.convertFilmFormToFilm(filmForm));
            assertEquals(5, filmDAO.findAll().size());
            Film newFilm = filmDAO.findById(5).get();
            assertEquals(15, newFilm.getDuree());
            assertEquals(5, newFilm.getId());
            assertEquals(2, newFilm.getRealisateur().getId());
            assertEquals("titre", newFilm.getTitre());
        } catch (ServiceException serviceException) {
            throw new RuntimeErrorException(null);
        }
    }

    @Test 
    void whenDeleteFilm_thenShouldDeleteFilmInDB() {
        Film film = filmDAO.findById(1).get();
        filmDAO.delete(film);
        try {
        assertEquals(3, filmDAO.findAll().size());
        } catch(ServiceException e) {
            throw new RuntimeErrorException(null);
        }
        Optional<Film> filmDeleted = filmDAO.findById(1);
        assertEquals(Optional.empty(), filmDeleted);
    }

    @Test
    void whenFindByRealisateurId_thenShouldHaveTheGoodList() {
        List<Film> filmsDeCameron = filmDAO.findByRealisateurId(1);
        assertEquals(1, filmsDeCameron.size());
        List<Film> filmsDeJackson = filmDAO.findByRealisateurId(2);
        assertEquals(3, filmsDeJackson.size());
        List<Film> filmsSansRealisateurs = filmDAO.findByRealisateurId(3);
        assertEquals(0, filmsSansRealisateurs.size());
    }

    @Test 
    void whenFindById_thenShouldHaveTheGoodFilm() {
        Optional<Film> filmInexistant = filmDAO.findById(100); 
        assertEquals(Boolean.TRUE, filmInexistant.isEmpty() );
        Optional<Film> filmExistant = filmDAO.findById(1);
        assertEquals(Boolean.FALSE, filmExistant.isEmpty() );
        Film film = filmExistant.get();
        System.out.println(film);
        assertEquals("avatar", film.getTitre());
        assertEquals(162, film.getDuree());
        assertEquals(1, film.getRealisateur().getId());
    }
}