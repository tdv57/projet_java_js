package com.ensta.myfilmlist;

import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.UserForm;
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
public class HistorysDAOTests {
    @Autowired
    private FilmDAO filmDAO;

    @Autowired
    private DirectorDAO directorDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HistoryDAO historyDAO;
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

    private History getHistoryFilm4User4() {
        History history = new History();
        history.setFilm(getLeRetourDuRoi());
        history.setId(2);
        history.setRating(18);
        history.setUser(getFerdinandAlain());
        return history;
    }

    private History getHistoryFilm2User1() {
        History history = new History();
        history.setFilm(getLaCommunauteDeLAnneau());
        history.setId(1);
        history.setRating(20);
        history.setUser(getAxelRichard());
        return history;
    }

    @Test 
    void whenFindHistoryByUserAndFilmId_thenShouldHaveHistory() {
        Optional<History> history21 = historyDAO.findHistoryByUserIdAndFilmId(1, 2);
        assertEquals(getHistoryFilm2User1(), history21.get());

        Optional<History> history44 = historyDAO.findHistoryByUserIdAndFilmId(4, 4);
        assertEquals(getHistoryFilm4User4(), history44.get());

        Optional<History> error = historyDAO.findHistoryByUserIdAndFilmId(4, 1);
        assertEquals(Optional.empty(), error);
    }

    @Test 
    void whenGetWatchList_thenShouldHaveFilmSeenByUser() throws ServiceException{
        List<Film> filmsSeenByAxel = historyDAO.getWatchList(1);
        assertEquals(2, filmsSeenByAxel.size());
        List<Film> filmsExpected = new ArrayList<>();
        filmsExpected.add(getLaCommunauteDeLAnneau());
        filmsExpected.add(getAvatar());
        assertEquals(filmsExpected, filmsSeenByAxel);

        assertEquals(new ArrayList<>(), historyDAO.getWatchList(3));

        ServiceException error = assertThrows(ServiceException.class, () -> {
            historyDAO.getWatchList(100);
        });
        assertEquals("Utilisateur introuvable", error.getMessage());
    }

    @Test
    void whenAddFilmToWatchList_thenShouldCreateHistory() throws ServiceException {
        History newHistory = historyDAO.addFilmToWatchList(4, 3);
        assertEquals(newHistory.getFilm(), getLesDeuxTours());
        assertEquals(newHistory.getUser(), getFerdinandAlain());

        ServiceException errorUserId = assertThrows(ServiceException.class, () -> {
            historyDAO.addFilmToWatchList(100, 1);
        }); 

        assertEquals("Utilisateur introuvable", errorUserId.getMessage());

        ServiceException errorFilmId = assertThrows(ServiceException.class, () -> {
            historyDAO.addFilmToWatchList(1, 100);
        });

        assertEquals("Film introuvable", errorFilmId.getMessage());
    }

    @Test 
    void deleteFilm() {
        assertEquals(getHistoryFilm4User4(), historyDAO.findHistoryByUserIdAndFilmId(4, 4).get());
        historyDAO.deleteFilm(4, 4);
        assertEquals(Optional.empty(), historyDAO.findHistoryByUserIdAndFilmId(4, 4));
    }

    @Test
    void whenRateFilm_thenShouldHaveRatedFilm() throws ServiceException{
        History history = historyDAO.addFilmToWatchList(4, 1);
        History _history = historyDAO.rateFilm(4, 1, 20);
        history.setRating(20);
        assertEquals(history, _history);
        assertEquals(history, historyDAO.findHistoryByUserIdAndFilmId(4, 1).get());
        ServiceException error = assertThrows(ServiceException.class, () -> {
            historyDAO.rateFilm(100, 1, 20);
        });
        assertEquals("Historique introuvable", error.getMessage());
    }

    void whenGetRate_thenShouldHaveRate() throws ServiceException {
        History history = historyDAO.addFilmToWatchList(4, 1);
        Optional<Integer> emptyRate = historyDAO.getRate(4, 1);
        assertEquals(Optional.empty(), emptyRate);
        Optional<Integer> rate = historyDAO.getRate(4, 4);
        assertEquals(18, rate.get());
    }

    @Test
    void whenGetRatesByFilms_thenShouldHaveRatesForFilm() throws ServiceException{
        List<Integer> rates = historyDAO.getRatesByFilmId(2);
        List<Integer> ratesExpected = new ArrayList<>();
        ratesExpected.add(20); 
        ratesExpected.add(18);
        assertEquals(ratesExpected, rates);

        ServiceException error = assertThrows(ServiceException.class, () -> {
            historyDAO.getRatesByFilmId(100);
        });
        assertEquals("Film inexistant", error.getMessage());
    }
}