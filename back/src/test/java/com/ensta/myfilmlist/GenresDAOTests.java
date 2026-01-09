package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dao.DirectorDAO;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.GenreDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Genre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Sql(
        scripts = "/data_test.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class GenresDAOTests {

    private static int count = 0;
    @Autowired
    private FilmDAO filmDAO;
    @Autowired
    private DirectorDAO directorDAO;
    @Autowired
    private GenreDAO genreDAO;
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
        count++;
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
    void whenFindAll_thenShouldHaveAllGenre() throws ServiceException {
        List<Genre> genres = genreDAO.findAll();
        assertEquals(genres.get(0), getAction());
        assertEquals(genres.get(1), getBiopic());
        assertEquals(genres.get(2), getComedie());
        assertEquals(genres.get(3), getDrame());
        assertEquals(genres.get(4), getFantaisie());
        assertEquals(genres.get(5), getHorreur());
        assertEquals(genres.get(6), getPolicier());
        assertEquals(genres.get(7), getSF());
        assertEquals(genres.get(8), getThriller());
    }

    @Test
    void whenFindById_thenShouldHaveGenre() {
        try {
            assertEquals(Optional.of(getAction()), genreDAO.findById(1));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(Optional.of(getThriller()), genreDAO.findById(9));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(Optional.empty(), genreDAO.findById(100));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenUpdate_thenShouldHaveUpdatedGenre() throws ServiceException {
        Genre newAction = new Genre();
        newAction.setId(1);
        newAction.setName("new action");
        Genre _newAction = genreDAO.update(1, "new action");
        assertEquals(newAction, _newAction);
        assertEquals(Optional.of(newAction), genreDAO.findById(1));
        assertThrows(ServiceException.class, () -> genreDAO.update(100, "new action"));
    }
} 