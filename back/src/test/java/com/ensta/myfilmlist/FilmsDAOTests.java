package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dao.impl.*;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.dao.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.form.*;
import com.ensta.myfilmlist.mapper.FilmMapper;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class FilmsDAOTests {

    @Autowired
    private FilmDAO filmDAO;
    @Autowired
    private DirectorDAO directorDAO;

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
        filmDAO.findAll().forEach(System.out::println);
        System.out.println("\n");
        directorDAO.findAll().forEach(System.out::println);
        } catch (ServiceException e) {
            System.out.println("Erreur interne");
        }
    }

    @Test 
    void whenCreateFilm_thenShouldHaveNewFilmInDB() {
        FilmForm filmForm = new FilmForm();
        filmForm.setDuration(15);
        filmForm.setDirectorId(2);
        filmForm.setTitle("title");
        try {
            filmDAO.save(filmMapper.convertFilmFormToFilm(filmForm));
            assertEquals(5, filmDAO.findAll().size());
            Film newFilm = filmDAO.findById(5).get();
            assertEquals(15, newFilm.getDuration());
            assertEquals(5, newFilm.getId());
            assertEquals(2, newFilm.getDirector().getId());
            assertEquals("title", newFilm.getTitle());
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
    void whenFindByDirectorId_thenShouldHaveTheGoodList() {
        List<Film> filmsDeCameron = filmDAO.findByDirectorId(1);
        assertEquals(1, filmsDeCameron.size());
        List<Film> filmsDeJackson = filmDAO.findByDirectorId(2);
        assertEquals(3, filmsDeJackson.size());
        List<Film> filmsSansDirectors = filmDAO.findByDirectorId(3);
        assertEquals(0, filmsSansDirectors.size());
    }

    @Test 
    void whenFindById_thenShouldHaveTheGoodFilm() {
        Optional<Film> filmInexistant = filmDAO.findById(100); 
        assertEquals(Boolean.TRUE, filmInexistant.isEmpty() );
        Optional<Film> filmExistant = filmDAO.findById(1);
        assertEquals(Boolean.FALSE, filmExistant.isEmpty() );
        Film film = filmExistant.get();
        System.out.println(film);
        assertEquals("avatar", film.getTitle());
        assertEquals(162, film.getDuration());
        assertEquals(1, film.getDirector().getId());
    }
}