package com.ensta.myfilmlist;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Genre;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.exception.ControllerException;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmsControllerTests {

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
    jamesCameron.setfilmsProduced(jamesCameronFilms);
    
    List<Film> peterJacksonFilms = new ArrayList<>();
    peterJacksonFilms.add(deBonMatin);
    peterJackson.setfilmsProduced(peterJacksonFilms);

    erreurInterne.setId(Long.valueOf(1000L));
  }

  @AfterEach
  void setDown() {
    System.out.println("\n");
    System.out.println("Fin test n°" + count);
    count ++;
    System.out.println("\n");
  }

  private List<Film> mockMyFilmsServiceFindAll() {
    List<Film> films = new ArrayList<>();

    films.add(hihihi1);
    films.add(hihihi2);
    films.add(hihihi3);
    films.add(deBonMatin);
    return films;
  }

  private Film mockMyFilmsServiceFindFilmById(long id) throws ServiceException {
    switch((int) id) {
      case 1:
        return hihihi1;
      case 2:
        return hihihi2;
      case 3:
        return hihihi3;
      case 4:
        return deBonMatin;
      default:
        throw new ServiceException("Le film demandé n'existe pas");
    }
  } 

  private Film mockMyFilmsServiceFindFilmByTitle(String title) throws ServiceException {
    switch(title) {
      case "hihihi1":
        return hihihi1;
      case "hihihi2":
        return hihihi2;
      case "hihihi3":
        return hihihi3;
      case "de bon matin":
        return deBonMatin;
      default:
        throw new ServiceException("Le film demandé n'existe pas");
    }
  }

  private List<Film> mockMyFilmsServiceFindFilmByDirectorId(long id) throws ServiceException {
    List<Film> films = new ArrayList<>();

    switch ((int) id) {
      case 1:
        films.add(hihihi1);
        films.add(hihihi2);
        films.add(hihihi3);
        return films;
      case 2:
        films.add(deBonMatin);
        return films;
      default:
        throw new ServiceException("Le réalistauer n'a réalisé aucun film");
    }
  }

  @Test 
  void whenGetAllFilms_thenShouldHaveAllFilms() throws Exception {
    when(myFilmsService.findAll()).thenReturn(mockMyFilmsServiceFindAll());

    mockMvc.perform(get("/film"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$[0].title").value("hihihi1"))
    .andExpect(jsonPath("$[1].title").value("hihihi2"))
    .andExpect(jsonPath("$[2].title").value("hihihi3"))
    .andExpect(jsonPath("$[3].title").value("de bon matin"));
  }

  @Test
  void whenGetFilmById_thenShouldHaveFilm() throws Exception {
    when(myFilmsService.findFilmById(anyLong())).thenAnswer(invocation -> {
      System.out.println(mockMyFilmsServiceFindFilmById(invocation.getArgument(0)));
      return mockMyFilmsServiceFindFilmById(invocation.getArgument(0));
    });

    mockMvc.perform(get("/film/1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id").value(1))
    .andExpect(jsonPath("$.title").value("hihihi1"))
    .andExpect(jsonPath("$.duration").value(60))
    .andExpect(jsonPath("$.directorDTO.name").value("James"))
    .andExpect(jsonPath("$.directorDTO.surname").value("Cameron"))
    .andExpect(jsonPath("$.genreDTO.name").value("comédie")); 

    mockMvc.perform(get("/film/100"))
    .andExpect(status().isNotFound());
  }

  @Test
  void whenGetFilmByTitle_thenShouldHaveFilm() throws Exception {
    when(myFilmsService.findFilmByTitle(anyString())).thenAnswer(invocation -> {
      return mockMyFilmsServiceFindFilmByTitle(invocation.getArgument(0));
    });

    mockMvc.perform(get("/film/title?title=hihihi1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id").value(1))
    .andExpect(jsonPath("$.title").value("hihihi1"))
    .andExpect(jsonPath("$.duration").value(60))
    .andExpect(jsonPath("$.directorDTO.name").value("James"))
    .andExpect(jsonPath("$.directorDTO.surname").value("Cameron"))
    .andExpect(jsonPath("$.genreDTO.name").value("comédie")); 

    mockMvc.perform(get("/film/title?title=xxxxxxxxxx"))
    .andExpect(status().isNotFound());
  }

  @Test
  void whenGetFilmByDirectorId_thenShouldHaveFilm() throws Exception {
    when(myFilmsService.findFilmByDirectorId(anyLong())).thenAnswer(invocation -> {
      return mockMyFilmsServiceFindFilmByDirectorId(invocation.getArgument(0));
    });

    mockMvc.perform(get("/film/director/1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$[0].id").value(1))
    .andExpect(jsonPath("$[0].title").value("hihihi1"))
    .andExpect(jsonPath("$[0].duration").value(60))
    .andExpect(jsonPath("$[0].directorDTO.name").value("James"))
    .andExpect(jsonPath("$[0].directorDTO.surname").value("Cameron"))
    .andExpect(jsonPath("$[0].genreDTO.name").value("comédie"))
    .andExpect(jsonPath("$[1].id").value(2))
    .andExpect(jsonPath("$[1].title").value("hihihi2"))
    .andExpect(jsonPath("$[1].duration").value(61))
    .andExpect(jsonPath("$[1].directorDTO.name").value("James"))
    .andExpect(jsonPath("$[1].directorDTO.surname").value("Cameron"))
    .andExpect(jsonPath("$[1].genreDTO.name").value("comédie"))
    .andExpect(jsonPath("$[2].id").value(3))
    .andExpect(jsonPath("$[2].title").value("hihihi3"))
    .andExpect(jsonPath("$[2].duration").value(62))
    .andExpect(jsonPath("$[2].directorDTO.name").value("James"))
    .andExpect(jsonPath("$[2].directorDTO.surname").value("Cameron"))
    .andExpect(jsonPath("$[2].genreDTO.name").value("comédie"));

    mockMvc.perform(get("/film/director/100"))
    .andExpect(status().isNotFound())
    .andExpect(content().string(""));

  }
}