package com.ensta.myfilmlist;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import com.ensta.myfilmlist.dto.*;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.UserMapper;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.form.UserForm;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.form.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class HistorysControllerTests {
  @MockBean 
  private MyFilmsService myFilmsService;

  @Autowired  
  private FilmMapper filmMapper; 

  @Autowired
  private MockMvc mockMvc;
  
  private Director jamesCameron = new Director();
  private Director peterJackson = new Director();
  private Film hihihi1 = new Film();
  private Film hihihi2 = new Film();
  private Film hihihi3 = new Film();
  private Film deBonMatin = new Film();
  private Director erreurInterne = new Director();
  private static int count = 0;
  private static Long directorId = Long.valueOf(1L);
  private static Long filmId = Long.valueOf(2L);
  private static Long userId = Long.valueOf(5L);
  private List<Genre> genres = new ArrayList<>();
  private int genreId = 1;

  @BeforeEach 
  void setUp() {

    System.out.println("\n");
    System.out.println("Debut test n°" + count);
    System.out.println("\n");
    jamesCameron.setFamous(false);
    jamesCameron.setBirthdate(LocalDate.of(1950, 03, 20));
    jamesCameron.setId(Long.valueOf(1L));
    jamesCameron.setSurname("Cameron");
    jamesCameron.setName("James");

    peterJackson.setFamous(false);
    peterJackson.setBirthdate(LocalDate.of(1980,02,02));
    peterJackson.setId(Long.valueOf(2L));
    peterJackson.setSurname("Jackson");
    peterJackson.setName("Peter");
    
    directorId = Long.valueOf(3L);

    Genre action = new Genre();
    action.setId(genreId++);
    action.setName("action");
    genres.add(action);

    Genre biopic = new Genre();
    biopic.setId(genreId++);
    biopic.setName("biopic");
    genres.add(biopic);

    Genre comedie = new Genre();
    comedie.setId(genreId++);
    comedie.setName("comédie");
    genres.add(comedie);

    Genre drame = new Genre();
    drame.setId(genreId++);
    drame.setName("drame");
    genres.add(drame);

    Genre fantaisie = new Genre();
    fantaisie.setId(genreId++);
    fantaisie.setName("fantaisie");
    genres.add(fantaisie);

    Genre horreur = new Genre();
    horreur.setId(genreId++);
    horreur.setName("horreur");
    genres.add(horreur);
    
    Genre policier = new Genre();
    policier.setId(genreId++);
    policier.setName("policier");
    genres.add(policier);

    Genre SF = new Genre();
    SF.setId(genreId++);
    SF.setName("SF");
    genres.add(SF);

    Genre thriller = new Genre();
    thriller.setId(genreId++);
    thriller.setName("thriller");
    genres.add(thriller);


    hihihi1.setDuration(60);
    hihihi1.setDirector(jamesCameron);
    hihihi1.setTitle("hihihi1");
    hihihi1.setId(Long.valueOf(1L));
    hihihi1.setGenre(genres.get(2));

    hihihi2.setDuration(61);
    hihihi2.setDirector(jamesCameron);
    hihihi2.setTitle("hihihi2");
    hihihi2.setId(Long.valueOf(2L));
    hihihi2.setGenre(genres.get(2));

    hihihi3.setDuration(62);
    hihihi3.setDirector(jamesCameron);
    hihihi3.setTitle("hihihi3");
    hihihi3.setId(Long.valueOf(3L));
    hihihi3.setGenre(genres.get(2));

    deBonMatin.setDuration(90);
    deBonMatin.setId(Long.valueOf(4L));
    deBonMatin.setDirector(peterJackson);
    deBonMatin.setTitle("de bon matin");
    deBonMatin.setGenre(genres.get(4));

    filmId = Long.valueOf(5L);

    List<Film> jamesCameronFilms = new ArrayList<>();
    jamesCameronFilms.add(hihihi1);
    jamesCameronFilms.add(hihihi2);
    jamesCameronFilms.add(hihihi3);
    jamesCameron.setFilmsProduced(jamesCameronFilms);
    
    List<Film> peterJacksonFilms = new ArrayList<>();
    peterJacksonFilms.add(deBonMatin);
    peterJackson.setFilmsProduced(peterJacksonFilms);

    erreurInterne.setId(Long.valueOf(1000L));
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

    private List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        films.add(getAvatar());
        films.add(getLaCommunauteDeLAnneau());
        films.add(getLeRetourDuRoi());
        films.add(getLesDeuxTours());
        return films;
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

    private History getHistoryFilm1User1() {
        History history = new History();
        history.setFilm(getAvatar());
        history.setId(1);
        history.setRating(20);
        history.setUser(getAxelRichard());
        return history;
    }

    private History getHistoryFilm2User2() {
        History history = new History();
        history.setFilm(getLaCommunauteDeLAnneau());
        history.setId(1);
        history.setRating(18);
        history.setUser(getBenoitBoero());
        return history;
    }

    private List<Film> mockMyFilmsServiceFindWatchList(long userId) throws ServiceException {
        List<Film> films = new ArrayList<>();

        switch((int) userId) {
            case 1:
                films.add(getAvatar());
                films.add(getLaCommunauteDeLAnneau());
                break;
            case 2:
                films.add(getLaCommunauteDeLAnneau());
                break;
            case 3:
                break;
            case 4:
                films.add(getLeRetourDuRoi());
                break;
            default:
                throw new ServiceException("Utilisateur introuvable");
        }
        return films;
    }

    private History mockMyFilmsServiceAddFilmToWatchList(long userId, long filmId) throws ServiceException {
        if (userId >=1 && userId <=4) {
            if (filmId >=1 && filmId<=4) {
                return new History(getAllUsers().get((int) userId - 1), getAllFilms().get((int) filmId - 1));
            }
            throw new ServiceException("Film introuvable");
        }
        throw new ServiceException("Utilisateur introuvable");

    }

    private History mockMyFilmsServiceRateFilm(long userId, long filmId, int rating) throws ServiceException {
        if (userId == 4 && filmId == 4 ) {
            History history = getHistoryFilm4User4();
            history.setRating(rating);
            return history;
        } else if (userId == 1 && filmId == 1) {
            History history = getHistoryFilm1User1();
            history.setRating(rating);
            return history;
        } else if (userId == 1 && filmId == 2) {
            History history = getHistoryFilm2User1();
            history.setRating(rating);
            return history;
        } else if (userId == 2 && filmId == 2) {
            History history = getHistoryFilm2User2();
            history.setRating(rating);
            return history;
        } else {
            throw new ServiceException("Historique introuvable");
        }
    }

    private Optional<Integer> mockMyFilmsServiceGetRate(long userId, long filmId) throws ServiceException {
        if (userId == 4 && filmId == 4 ) {
            return Optional.of(getHistoryFilm4User4().getRating());
        } else if (userId == 1 && filmId == 1) {
            return Optional.of(getHistoryFilm1User1().getRating());
        } else if (userId == 1 && filmId == 2) {
            return Optional.of(getHistoryFilm2User1().getRating());
        } else if (userId == 2 && filmId == 2) {
            return Optional.of(getHistoryFilm2User2().getRating());
        } else {
            throw new ServiceException("Note introuvable");
        }
    }

    private Optional<Double> mockMyFilmsServiceGetFilmMeanRating(long filmId) throws ServiceException {
        switch((int) filmId) {
            case 1:
                return Optional.of((double)getHistoryFilm1User1().getRating());
            case 2:
                return Optional.of(((double)getHistoryFilm2User1().getRating() + (double)getHistoryFilm2User2().getRating())/2);
            case 3:
                return Optional.empty();
            case 4:
                return Optional.of((double) getHistoryFilm4User4().getRating());
            default:
                throw new ServiceException("Film inexistant");
        }
    }
    
    @Test 
    void whenGetWatchList_thenShouldHaveWatchList() throws Exception {
        when(myFilmsService.findWatchList(anyLong())).thenAnswer(invocation -> {
            return mockMyFilmsServiceFindWatchList(invocation.getArgument(0));
        });

        mockMvc.perform(get("/history/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title").value("avatar"))
        .andExpect(jsonPath("$[1].title").value("La communauté de l'anneau"));

        mockMvc.perform(get("/history/100"))
        .andExpect(status().isNotFound());        
    }

    @Test 
    void whenAddToWatchList_thenShouldAddToWatchList() throws Exception {
        when(myFilmsService.addFilmToWatchList(anyLong(), anyLong())).thenAnswer(invocation -> {
            return mockMyFilmsServiceAddFilmToWatchList(invocation.getArgument(0), invocation.getArgument(1));
        });

        mockMvc.perform(post("/history/")
                .param("userId", "4")
                .param("filmId", "3"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.userDTO.id").value(4))
        .andExpect(jsonPath("$.filmDTO.title").value("Le retour du roi"));

        mockMvc.perform(post("/history/")
                .param("userId", "100")
                .param("filmId", "3"))
        .andExpect(status().isNotFound());

        mockMvc.perform(post("/history/")
                .param("userId", "4")
                .param("filmId", "300"))
        .andExpect(status().isNotFound());
    }

    @Test 
    void whenRemoveFromWatchList_thenShouldRemoveFromWatchList() throws Exception {
        doNothing().when(myFilmsService).removeFilmFromWatchList(anyLong(), anyLong());

        mockMvc.perform(delete("/history/")
                .param("userId", "1")
                .param("filmId", "1"))
        .andExpect(status().isNoContent());
        mockMvc.perform(delete("/history/")
                .param("userId", "100")
                .param("filmId", "1"))
        .andExpect(status().isNoContent());
    }

    @Test 
    void whenRateFilm_thenShouldRateFilm() throws Exception {
        when(myFilmsService.rateFilm(anyLong(), anyLong(), anyInt())).thenAnswer(invocation -> {
            return mockMyFilmsServiceRateFilm(invocation.getArgument(0), invocation.getArgument(1), invocation.getArgument(2));
        });

        mockMvc.perform(put("/history/")
                .param("userId", "1")
                .param("filmId", "1")
                .param("rating", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.filmDTO.id").value(1))
        .andExpect(jsonPath("$.userDTO.id").value(1))
        .andExpect(jsonPath("$.rating").value(10));

        mockMvc.perform(put("/history/")
                .param("userId", "100")
                .param("filmId", "100")
                .param("rating", "10"))
        .andExpect(status().isNotFound());
    }

    @Test 
    void whenGetRate_thenShouldHaveRate() throws Exception{
        when((myFilmsService.getRate(anyLong(), anyLong()))).thenAnswer(invocation -> {
            return mockMyFilmsServiceGetRate(invocation.getArgument(0), invocation.getArgument(1));
        });

        mockMvc.perform(get("/history/rate")
                .param("userId", "1")
                .param("filmId", "1"))
        .andExpect(status().isOk())
        .andExpect(content().string("20"));

        mockMvc.perform(get("/history/rate")
                .param("userId", "100")
                .param("filmId", "100"))
        .andExpect(status().isNotFound());
    }

    @Test
    void whenGetFilmMeanRating_thenShouldHaveMeanRating() throws Exception {
        when(myFilmsService.getFilmMeanRating(anyLong())).thenAnswer(invocation -> {
            return mockMyFilmsServiceGetFilmMeanRating(invocation.getArgument(0));
        });

        mockMvc.perform(get("/history/mean/2"))
        .andExpect(status().isOk())
        .andExpect(content().string("19.0"));

        mockMvc.perform(get("/history/mean/3"))
        .andExpect(status().isNoContent());

        mockMvc.perform(get("/history/mean/100"))
        .andExpect(status().isNotFound());
    }
}