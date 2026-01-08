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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@SpringBootTest
@Sql(
    scripts = {"/schema.sql", "/data.sql"},
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@Transactional
public class UsersDAOTests {
    @Autowired
    private FilmDAO filmDAO;

    @Autowired
    private DirectorDAO directorDAO;

    @Autowired
    private UserDAO userDAO;

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

    private User getAxelRichard() {
        User axelRichard = new User();
        axelRichard.setId(1);
        axelRichard.setName("Richard");
        axelRichard.setSurname("Axel");
        axelRichard.setRoles("USER");
        axelRichard.setPassword("axel");
        return axelRichard;
    }

    private User getBenoitBoero() {
        User benoitBoero = new User();
        benoitBoero.setId(2);
        benoitBoero.setName("Boero");
        benoitBoero.setSurname("Benoit");
        benoitBoero.setRoles("USER");
        benoitBoero.setPassword("benoit");
        return benoitBoero;
    }

    private User getElfieMolinaBonnefoy() {
        User elfieMolinaBonnefoy = new User();
        elfieMolinaBonnefoy.setId(3);
        elfieMolinaBonnefoy.setName("Molina--Bonnefoy");
        elfieMolinaBonnefoy.setSurname("Elfie");
        elfieMolinaBonnefoy.setRoles("ADMIN");
        elfieMolinaBonnefoy.setPassword("elfie");
        return elfieMolinaBonnefoy;
    }

    private User getFerdinandAlain() {
        User ferdinandAlain = new User();
        ferdinandAlain.setId(4);
        ferdinandAlain.setName("Alain");
        ferdinandAlain.setSurname("Ferdinand");
        ferdinandAlain.setRoles("USER");
        ferdinandAlain.setPassword("ferdinand");
        return ferdinandAlain;
    }

    private List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(getAxelRichard());
        users.add(getBenoitBoero());
        users.add(getElfieMolinaBonnefoy());
        users.add(getFerdinandAlain());
        return users;  
    }

    @Test 
    void whenFindAllUsers_thenShouldHaveAllUser() throws ServiceException {
        List<User> users = userDAO.findAll();
        assertEquals(getAllUsers(), users);
    }
}