package com.ensta.myfilmlist;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.dto.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class DirectorsControllerTests {
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

  private List<Director> mockMyFilmsServiceFindAllDirectors() {
    List<Director> directors = new ArrayList<>();
    directors.add(jamesCameron);
    directors.add(peterJackson);
    return directors;
  }

  private Director mockMyFilmsServiceFindDirectorById(long id) throws ServiceException {
    switch((int) id) {
        case 1:
            return jamesCameron;
        case 2:
            return peterJackson;
        default:
            throw new ServiceException("Le réalisateur demandé n'existe pas");
    }
  }

  private Director mockMyFilmsServiceFindDirectorByNameAndSurname(String name, String surname) throws ServiceException {
    if (Objects.equals(surname, jamesCameron.getSurname()) && Objects.equals(name, jamesCameron.getName())) {
        return jamesCameron;
    } else if (Objects.equals(surname, peterJackson.getSurname()) && Objects.equals(name, peterJackson.getName())) {
        return peterJackson;
    } else {
        throw new ServiceException("Le réalisateur demandé n'existe pas");
    }
  }

  private DirectorDTO mockMyFilmsServiceCreateDirector(DirectorForm directorForm) {
    Director director = DirectorMapper.convertDirectorFormToDirector(directorForm);
    director.setId(directorId++);
    return DirectorMapper.convertDirectorToDirectorDTO(director);
  }

  private DirectorDTO mockMyFilmsServiceUpdateDirector(long id, DirectorForm directorForm) throws ServiceException {
    switch ((int) id) {
        case 1:
            jamesCameron.setBirthdate(directorForm.getBirthdate());
            jamesCameron.setName(directorForm.getName());
            jamesCameron.setSurname(directorForm.getSurname());
            return DirectorMapper.convertDirectorToDirectorDTO(jamesCameron);
        case 2:
            peterJackson.setBirthdate(directorForm.getBirthdate());
            peterJackson.setName(directorForm.getName());
            peterJackson.setSurname(directorForm.getSurname());    
            return DirectorMapper.convertDirectorToDirectorDTO(peterJackson);
        default:
            throw new ServiceException("Can't get Director");
    }
  }

  private void mockMyFilmsServiceDeleteDirector(long id) throws ServiceException {
    switch((int) id) {
        case 1:
            jamesCameron = null;
            break;
        case 2:
            peterJackson = null;
            break;
        default: 
            throw new ServiceException("Can't get Director");
    }   
  }

  @Test 
  void whenGetAllDirectors_thenShouldHaveAllDirectors() throws Exception{
    when(myFilmsService.findAllDirectors()).thenAnswer(invocation -> {
        return mockMyFilmsServiceFindAllDirectors();
    });

    mockMvc.perform(get("/director"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$[0].id").value(1))
    .andExpect(jsonPath("$[0].surname").value("Cameron"))
    .andExpect(jsonPath("$[0].name").value("James"))
    .andExpect(jsonPath("$[0].birthdate[0]").value(1950))
    .andExpect(jsonPath("$[0].birthdate[1]").value(3))
    .andExpect(jsonPath("$[0].birthdate[2]").value(20))
    .andExpect(jsonPath("$[0].famous").value(false))
    .andExpect(jsonPath("$[1].id").value(2))
    .andExpect(jsonPath("$[1].surname").value("Jackson"))
    .andExpect(jsonPath("$[1].name").value("Peter"))
    .andExpect(jsonPath("$[1].birthdate[0]").value(1980))
    .andExpect(jsonPath("$[1].birthdate[1]").value(2))
    .andExpect(jsonPath("$[1].birthdate[2]").value(2))
    .andExpect(jsonPath("$[1].famous").value(false));
  }

  @Test 
  void whenGetDirectorById_thenShouldHaveDirectorDTO() throws Exception {
    when(myFilmsService.findDirectorById(anyLong())).thenAnswer(invocation -> {
        return mockMyFilmsServiceFindDirectorById(invocation.getArgument(0));
    });

    mockMvc.perform(get("/director/1"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id").value(1))
    .andExpect(jsonPath("$.surname").value("Cameron"))
    .andExpect(jsonPath("$.name").value("James"))
    .andExpect(jsonPath("$.birthdate[0]").value(1950))
    .andExpect(jsonPath("$.birthdate[1]").value(3))
    .andExpect(jsonPath("$.birthdate[2]").value(20))
    .andExpect(jsonPath("$.famous").value(false));

    mockMvc.perform(get("/director/100"))
    .andExpect(status().isBadRequest())
    .andExpect(content().string("Can't get Director."));
  }

  @Test 
  void whenGetDirectorByNameAndSurname_thenShouldHaveDirector() throws Exception {
    when(myFilmsService.findDirectorByNameAndSurname(anyString(), anyString())).thenAnswer(invocation -> {
        return mockMyFilmsServiceFindDirectorByNameAndSurname(invocation.getArgument(0), invocation.getArgument(1));
    });

    mockMvc.perform(get("/director/?name=James&surname=Cameron"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id").value(1))
    .andExpect(jsonPath("$.surname").value("Cameron"))
    .andExpect(jsonPath("$.name").value("James"))
    .andExpect(jsonPath("$.birthdate[0]").value(1950))
    .andExpect(jsonPath("$.birthdate[1]").value(3))
    .andExpect(jsonPath("$.birthdate[2]").value(20))
    .andExpect(jsonPath("$.famous").value(false));

    mockMvc.perform(get("/director/?name=unknown&surname=unknown"))
    .andExpect(status().isNotFound());

  }

  @Test 
  void whenCreateDirector_thenShouldCreateDirector() throws Exception {
    when(myFilmsService.createDirector(any(DirectorForm.class))).thenAnswer(invocation -> {
        return mockMyFilmsServiceCreateDirector(invocation.getArgument(0));
    });

    System.out.println("whenCreateDirector_thenShouldCreateDirector");
    String newDirector= """
            {
            "birthdate": "2000-03-20",
            "name": "name",
            "surname": "surname"
            }
            """;

    mockMvc.perform(post("/director/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newDirector))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.surname").value("surname"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.birthdate[0]").value(2000))
                .andExpect(jsonPath("$.birthdate[1]").value(3))
                .andExpect(jsonPath("$.birthdate[2]").value(20))
                .andExpect(jsonPath("$.famous").value(false));
  }

  @Test 
  void whenUpdateDirector_thenShouldHaveUpdatedDirector() throws Exception {
    when(myFilmsService.updateDirector(anyLong(), any(DirectorForm.class))).thenAnswer(invocation -> {
        return mockMyFilmsServiceUpdateDirector(invocation.getArgument(0), invocation.getArgument(1));
    });

    String newDirector= """
            {
            "birthdate": "2000-03-20",
            "name": "name",
            "surname": "surname"
            }
            """;

    mockMvc.perform(put("/director/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newDirector))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.surname").value("surname"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.birthdate[0]").value(2000))
                .andExpect(jsonPath("$.birthdate[1]").value(3))
                .andExpect(jsonPath("$.birthdate[2]").value(20))
                .andExpect(jsonPath("$.famous").value(false));

    mockMvc.perform(put("/director/100")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newDirector))
                .andExpect(status().isNotFound());
  }

  @Test 
  void whenDeleteDirector_thenShouldDeleteDirector() throws Exception {
    doAnswer(invocation -> {
        mockMyFilmsServiceDeleteDirector(invocation.getArgument(0));
        return null;
    }).when(myFilmsService).deleteDirector(anyLong());

    mockMvc.perform(delete("/director/1"))
    .andExpect(status().isNoContent());

    assertEquals(jamesCameron, null);

    mockMvc.perform(delete("/director/100"))
    .andExpect(status().isNotFound());
  }
}