package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dao.DirectorDAO;
import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.model.Film;
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
class DirectorsDAOTests {
    private static int count = 0;
    @Autowired
    private FilmDAO filmDAO;
    @Autowired
    private DirectorDAO directorDAO;
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
        avatar.setGenre(null);
        return avatar;
    }

    private Film getLaCommunauteDeLAnneau() {
        Film laCommunauteDeLAnneau = new Film();
        laCommunauteDeLAnneau.setDirector(getPeterJackson());
        laCommunauteDeLAnneau.setDuration(178);
        laCommunauteDeLAnneau.setId(2);
        laCommunauteDeLAnneau.setTitle("La communauté de l'anneau");
        laCommunauteDeLAnneau.setGenre(null);
        return laCommunauteDeLAnneau;
    }

    private Film getLesDeuxTours() {
        Film lesDeuxTours = new Film();
        lesDeuxTours.setDirector(getPeterJackson());
        lesDeuxTours.setDuration(179);
        lesDeuxTours.setId(3);
        lesDeuxTours.setTitle("Les deux tours");
        lesDeuxTours.setGenre(null);
        return lesDeuxTours;
    }

    private Film getLeRetourDuRoi() {
        Film leRetourDuRoi = new Film();
        leRetourDuRoi.setDirector(getPeterJackson());
        leRetourDuRoi.setDuration(201);
        leRetourDuRoi.setId(4);
        leRetourDuRoi.setTitle("Le retour du roi");
        leRetourDuRoi.setGenre(null);
        return leRetourDuRoi;
    }


    @Test
    void printDatabaseTest() throws ServiceException {
        filmDAO.findAll().forEach(System.out::println);
        System.out.println("\n");
        try {
            directorDAO.findAll().forEach(System.out::println);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenFindAll_thenShouldHaveAllDirectors() {
        List<Director> directors;
        try {
            directors = directorDAO.findAll();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(getJamesCameron(), directors.get(0));
        assertEquals(getPeterJackson(), directors.get(1));
    }

    @Test
    void whenFindBySurnameAndName_thenShouldHaveDirector() {
        Optional<Director> jamesCameron;
        try {
            jamesCameron = directorDAO.findByNameAndSurname("James", "Cameron");
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(Optional.of(getJamesCameron()), jamesCameron);

        Optional<Director> error;
        try {
            error = directorDAO.findByNameAndSurname("unknown", "unknown");
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(Optional.empty(), error);
    }

    @Test
    void whenFindById_thenShouldHaveDirector() throws ServiceException {
        Optional<Director> jamesCameron = directorDAO.findById(1L);
        assertEquals(jamesCameron, Optional.of(getJamesCameron()));

        Optional<Director> empty = directorDAO.findById(100L);
        assertEquals(Optional.empty(), empty);
    }

    @Test
    void whenUpdate_thenShouldHaveUpdatedDirector() throws ServiceException {
        Director newJamesCameron = new Director();
        newJamesCameron.setBirthdate((LocalDate.of(2000, 8, 16)));
        newJamesCameron.setFamous(false);
        newJamesCameron.setName("J");
        newJamesCameron.setSurname("C");
        newJamesCameron.setId(1L);
        Director updatedJamesCameron = directorDAO.update(1L, newJamesCameron);
        assertEquals(Optional.of(newJamesCameron), directorDAO.findById(1));

        ServiceException e = assertThrows(ServiceException.class, () -> directorDAO.update(100L, newJamesCameron));

        assertEquals("Director already exists", e.getMessage());
    }

    @Test
    void whenSave_thenShouldHaveCreatedDirector() throws ServiceException {
        Director newDirector = new Director();
        newDirector.setBirthdate(LocalDate.of(2002, 3, 20));
        newDirector.setFamous(false);
        newDirector.setName("name");
        newDirector.setSurname("surname");
        Director savedDirector;
        try {
            savedDirector = directorDAO.save(newDirector);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        newDirector.setId(3L);
        assertEquals(Optional.of(savedDirector), directorDAO.findById(3L));
    }

    @Test
    void whenDelete_thenShouldHaveDeleteDirector() throws ServiceException {
        try {
            assertEquals(2, directorDAO.findAll().size());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            directorDAO.delete(1L);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(1, directorDAO.findAll().size());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            directorDAO.delete(3L);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(1, directorDAO.findAll().size());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            directorDAO.delete(2L);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        try {
            assertEquals(0, directorDAO.findAll().size());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        assertEquals(filmDAO.findById(1), Optional.empty());
    }
}