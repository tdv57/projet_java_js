package com.ensta.myfilmlist;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.dao.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.mapper.FilmMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
@Transactional
class DirectorsDAOTests {
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
    void whenFindAll_thenShouldHaveAllDirectors() {
        List<Director> directors = directorDAO.findAll();
        assertEquals(getJamesCameron(), directors.get(0));
        assertEquals(getPeterJackson(), directors.get(1));
    }

    @Test 
    void whenFindBySurnameAndName_thenShouldHaveDirector() {
        Optional<Director> jamesCameron = directorDAO.findByNameAndSurname("James", "Cameron");
        assertEquals(Optional.of(getJamesCameron()), jamesCameron);

        Optional<Director> error = directorDAO.findByNameAndSurname("unknown", "unknown");
        assertEquals(Optional.empty(), error);
    }

    @Test 
    void whenFindById_thenShouldHaveDirector() {
        Optional<Director> jamesCameron = directorDAO.findById(1L);
        assertEquals(jamesCameron, Optional.of(getJamesCameron()));

        Optional<Director> empty = directorDAO.findById(100L);
        assertEquals(Optional.empty(), empty);
    }

    @Test
    void whenUpdate_thenShouldHaveUpdatedDirector() throws ServiceException{
        Director newJamesCameron = new Director();
        newJamesCameron.setBirthdate((LocalDate.of(2000, 8, 16)));
        newJamesCameron.setFamous(false);
        newJamesCameron.setName("J");
        newJamesCameron.setSurname("C");
        newJamesCameron.setId(1L);
        Director updatedJamesCameron = directorDAO.update(1L, newJamesCameron);
        assertEquals(Optional.of(newJamesCameron), directorDAO.findById(1));

        ServiceException e = assertThrows(ServiceException.class, () -> {
            directorDAO.update(100L, newJamesCameron);
        });

        assertEquals("Can't update Director.", e.getMessage());
    }

    @Test 
    void whenSave_thenShouldHaveCreatedDirector() {
        Director newDirector = new Director();
        newDirector.setBirthdate(LocalDate.of(2002,03, 20));
        newDirector.setFamous(false);
        newDirector.setName("name");
        newDirector.setSurname("surname");
        Director savedDirector = directorDAO.save(newDirector);
        newDirector.setId(3L);
        assertEquals(Optional.of(savedDirector), directorDAO.findById(3L));
    }

    @Test 
    void whenDelete_thenShouldHaveDeleteDirector() {
        System.out.println("whenDelete_thenShouldHaveDeleteDirector");
        assertEquals(2, directorDAO.findAll().size());
        directorDAO.delete(1L);
        assertEquals(1, directorDAO.findAll().size());
        directorDAO.delete(3L);
        assertEquals(1, directorDAO.findAll().size());
        directorDAO.delete(2L);
        assertEquals(0, directorDAO.findAll().size());
        assertEquals(filmDAO.findById(1), Optional.empty());
    
    }
}