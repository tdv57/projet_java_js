package com.ensta.myfilmlist;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.dao.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.form.*;
import com.ensta.myfilmlist.mapper.FilmMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest
@Sql(
    scripts = {"/schema.sql", "/data.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Transactional
class FilmsDAOTests {

    @Autowired
    private FilmDAO filmDAO;
    @Autowired
    private DirectorDAO directorDAO;

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

    private Genre getAction() {
        Genre action = new Genre();
        action.setId(1);
        action.setName("action");
        return action;
    }

    private Genre getBiopic() {
        Genre biopic = new Genre();
        biopic.setId(2);
        biopic.setName("biopic");
        return biopic;
    }

    private Genre getComedie() {
        Genre comedie = new Genre();
        comedie.setId(3);
        comedie.setName("comédie");
        return comedie;
    }

    private Genre getDrame() {
        Genre drame = new Genre();
        drame.setId(4);
        drame.setName("drame");
        return drame;
    }

    private Genre getFantaisie() {
        Genre fantaisie = new Genre();
        fantaisie.setId(5);
        fantaisie.setName("fantaisie");
        return fantaisie;
    }

    private Genre getHorreur() {
        Genre horreur = new Genre();
        horreur.setId(6);
        horreur.setName("horreur");
        return horreur;
    }

    private Genre getPolicier() {
        Genre policier = new Genre();
        policier.setId(7);
        policier.setName("policier");
        return policier;
    }

    private Genre getSF() {
        Genre sf = new Genre();
        sf.setId(8);
        sf.setName("SF");
        return sf;
    }

    private Genre getThriller() {
        Genre thriller = new Genre();
        thriller.setId(9);
        thriller.setName("thriller");
        return thriller;
    }

    private Director getJamesCameron() {
        Director jamesCameron = new Director();
        jamesCameron.setBirthdate(LocalDate.of(1954, 8, 16));    
        jamesCameron.setFamous(false);
        jamesCameron.setId(1L);
        jamesCameron.setName("James");
        jamesCameron.setSurname("Cameron");
        jamesCameron.setFilmsProduced(new ArrayList<>());
        return jamesCameron;
    }

    private Director getPeterJackson() {
        Director peterJackson = new Director();
        peterJackson.setBirthdate(LocalDate.of(1961, 10, 31));        
        peterJackson.setFamous(true);
        peterJackson.setId(2L);
        peterJackson.setName("Peter");
        peterJackson.setSurname("Jackson");
        peterJackson.setFilmsProduced(new ArrayList<>());
        return peterJackson;
    }

    private Film getAvatar() {
        Film avatar = new Film();
        avatar.setDirector(getJamesCameron());
        avatar.setDuration(162);
        avatar.setId(1);
        avatar.setTitle("avatar");
        avatar.setGenre(getAction());
        return avatar;
    }

    private Film getLaCommunauteDeLAnneau() {
        Film laCommunauteDeLAnneau = new Film();
        laCommunauteDeLAnneau.setDirector(getPeterJackson());
        laCommunauteDeLAnneau.setDuration(178);
        laCommunauteDeLAnneau.setId(2);
        laCommunauteDeLAnneau.setTitle("La communauté de l'anneau");
        laCommunauteDeLAnneau.setGenre(getFantaisie());
        return laCommunauteDeLAnneau;
    }

    private Film getLesDeuxTours() {
        Film lesDeuxTours = new Film();
        lesDeuxTours.setDirector(getPeterJackson());
        lesDeuxTours.setDuration(179);
        lesDeuxTours.setId(3);
        lesDeuxTours.setTitle("Les deux tours");
        lesDeuxTours.setGenre(getFantaisie());
        return lesDeuxTours;      
    }

    private Film getLeRetourDuRoi() {
        Film leRetourDuRoi = new Film();
        leRetourDuRoi.setDirector(getPeterJackson());
        leRetourDuRoi.setDuration(201);
        leRetourDuRoi.setId(4);
        leRetourDuRoi.setTitle("Le retour du roi");
        leRetourDuRoi.setGenre(getFantaisie());
        return leRetourDuRoi;      
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
    void whenFindAll_thenShouldHaveAllFilms() throws ServiceException {
        List<Film> films = this.filmDAO.findAll();
        assertEquals(films.get(0), getAvatar());
        assertEquals(films.get(1), getLaCommunauteDeLAnneau());
        assertEquals(films.get(2), getLesDeuxTours());
        assertEquals(films.get(3), getLeRetourDuRoi());
    }


    @Test 
    void whenSave_thenShouldHaveSaveFilm() {
        FilmForm filmForm = new FilmForm();
        filmForm.setDuration(15);
        filmForm.setDirectorId(2);
        filmForm.setTitle("title");
        filmForm.setGenreId(1);
        try {
            filmDAO.save(filmMapper.convertFilmFormToFilm(filmForm));
            assertEquals(5, filmDAO.findAll().size());
            Film newFilm = filmDAO.findById(5).get();
            assertEquals(15, newFilm.getDuration());
            assertEquals(5, newFilm.getId());
            assertEquals(2, newFilm.getDirector().getId());
            assertEquals("title", newFilm.getTitle());
            assertEquals("action", newFilm.getGenre().getName());

        } catch (ServiceException serviceException) {
            throw new RuntimeErrorException(null);
        }
    }





    @Test 
    void whenFindById_thenShouldHaveFilm() {
        Optional<Film> filmInexistant = filmDAO.findById(100); 
        assertEquals(Boolean.TRUE, filmInexistant.isEmpty() );
        Optional<Film> filmExistant = filmDAO.findById(1);
        assertEquals(Boolean.FALSE, filmExistant.isEmpty() );
        Film film = filmExistant.get();
        System.out.println(film);
        assertEquals("avatar", film.getTitle());
        assertEquals(162, film.getDuration());
        assertEquals(1, film.getDirector().getId());
        assertEquals("action", film.getGenre().getName());
    }

    @Test 
    void whenFindByTitle_thenShouldHaveTitle() {
        Optional<Film> avatar = filmDAO.findByTitle("avatar");
        assertEquals(Optional.of(getAvatar()), avatar);

        Optional<Film> notFound = filmDAO.findByTitle("xxxxx");
        assertEquals(Optional.empty(), notFound);
    }
    
    @Test
    void whenFindByDirectorId_thenShouldHaveFilms() {
        List<Film> filmsDeCameron = filmDAO.findByDirectorId(1);
        assertEquals(1, filmsDeCameron.size());
        List<Film> filmsDeJackson = filmDAO.findByDirectorId(2);
        assertEquals(3, filmsDeJackson.size());
        List<Film> filmsSansDirectors = filmDAO.findByDirectorId(3);
        assertEquals(0, filmsSansDirectors.size());
    }

    @Test 
    void whenUpdateFilm_thenShouldHaveUpdatedFilm() throws ServiceException{
        Film newAvatar = new Film();
        newAvatar.setDirector(getJamesCameron());
        newAvatar.setDuration(30);
        newAvatar.setTitle("avatar2");

        Film result = filmDAO.update(1, newAvatar);
        assertEquals(result, filmDAO.findById(1L).get());

        ServiceException error = assertThrows(ServiceException.class, () -> {
            filmDAO.update(10, newAvatar);
        });

        assertEquals("Can't update Film.", error.getMessage());
    }

    @Test 
    void whenDeleteFilm_thenShouldDeleteFilm() {
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
}