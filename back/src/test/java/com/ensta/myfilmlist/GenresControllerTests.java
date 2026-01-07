package com.ensta.myfilmlist;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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


import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.dto.*;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.GenreMapper;
import com.ensta.myfilmlist.exception.ServiceException;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class GenresControllerTests{
  
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

  private List<Genre> mockMyFilmsServiceFindAllGenres() {
    return genres;
  }

  private Genre mockMyFilmsServiceFindGenreById(long id) throws ServiceException {
    int intId = (int) id;
    if((intId) <= genres.size() && intId > 0) {
        return genres.get(intId - 1);
    }
    throw new ServiceException("Le genre demandé n'existe pas");
  }

  private GenreDTO mockMyFilmsServiceUpdateGenre(long id, String name) throws ServiceException {
    int intId = (int) id;
    if((intId) <= genres.size() && intId > 0) {
        genres.get(intId - 1).setName(name);
        return GenreMapper.convertGenreToGenreDTO(genres.get(intId - 1));
    }
    throw new ServiceException("Le genre demandé n'existe pas");
  }

  @Test 
  void whenGetAllGenres_thenShouldHaveAllGenres() throws Exception {
    when(myFilmsService.findAllGenres()).thenAnswer(invocation -> {
        return mockMyFilmsServiceFindAllGenres();
    });

    mockMvc.perform(get("/genre"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$[0].id").value(1))
    .andExpect(jsonPath("$[0].name").value("action"))
    .andExpect(jsonPath("$[1].id").value(2))
    .andExpect(jsonPath("$[1].name").value("biopic"))
    .andExpect(jsonPath("$[2].id").value(3))
    .andExpect(jsonPath("$[2].name").value("comédie"))
    .andExpect(jsonPath("$[3].id").value(4))
    .andExpect(jsonPath("$[3].name").value("drame"))
    .andExpect(jsonPath("$[4].id").value(5))
    .andExpect(jsonPath("$[4].name").value("fantaisie"))
    .andExpect(jsonPath("$[5].id").value(6))
    .andExpect(jsonPath("$[5].name").value("horreur"))
    .andExpect(jsonPath("$[6].id").value(7))
    .andExpect(jsonPath("$[6].name").value("policier"))
    .andExpect(jsonPath("$[7].id").value(8))
    .andExpect(jsonPath("$[7].name").value("SF"))
    .andExpect(jsonPath("$[8].id").value(9))
    .andExpect(jsonPath("$[8].name").value("thriller"));
  }

  void whenGetGenreById_thenShouldHaveGenre() throws Exception {
    when(myFilmsService.findGenreById(anyLong())).thenAnswer(invocation -> {
        return mockMyFilmsServiceFindGenreById(invocation.getArgument(0));
    });

    mockMvc.perform(get("/genre/1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id").value(1))
    .andExpect(jsonPath("$.name").value("action"));

    mockMvc.perform(get("/genre/100"))
    .andExpect(status().isNotFound());
  }

  void whenUpdateGenre_thenShouldHaveUpdatedGenre() throws Exception {
    when(myFilmsService.updateGenre(anyLong(), anyString())).thenAnswer(invocation -> {
        return mockMyFilmsServiceUpdateGenre(invocation.getArgument(0), invocation.getArgument(1));
    });

    Genre newAction = new Genre();
    newAction.setId(1);
    newAction.setName("newAction");

    mockMvc.perform(put("/genre/1?name=newAction"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id").value(1))
    .andExpect(jsonPath("$.name").value("newAction"));

    assertEquals(newAction, genres.get(0));

    mockMvc.perform(put("/genre/100?name=newAction"))
    .andExpect(status().isNotFound());

  }
}