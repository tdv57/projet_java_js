package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dao.impl.*;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.dao.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.service.impl.MyFilmsServiceImpl;
import com.ensta.myfilmlist.form.*;
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.*;
import com.ensta.myfilmlist.dao.impl.*;
import com.ensta.myfilmlist.dto.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.dom4j.util.PerThreadSingleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations; // si tu initialises les mocks manuellement

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FilmServiceTests {

  @MockBean 
  private JpaDirectorDAO jpaDirectorDAO;

  @MockBean 
  private JpaGenreDAO jpaGenreDAO;

  @MockBean 
  private JpaFilmDAO jpaFilmDAO;

  @Autowired
  private FilmMapper filmMapper; 

  @Autowired 
  private MyFilmsServiceImpl myFilmsServiceImpl;

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

  private Director mockJpaDirectorDAOUpdate(long id, Director director) throws ServiceException {
    switch ((int)id) {
      case 1:
        jamesCameron = director;
        jamesCameron.setId(id);
        return jamesCameron;
      case 2:
        peterJackson = director;
        peterJackson.setId(id);
        return peterJackson;
      default:
        throw new ServiceException("Impossible de mettre à jour le réalisateur");
    }
  }

  private Optional<Director> mockJpaDirectorDAOFindById(long id) {
    switch((int) id) {
      case 1:
        return Optional.of(jamesCameron);
      case 2:
        return Optional.of(peterJackson);
      default:
        return Optional.empty();
    }
  }

  private Director mockJpaDirectorDAOSave(Director director) {
    return director;
  }

  private Optional<Director> mockJpaDirectorFindBySurnameAndName(String name, String surname) throws ServiceException{
    if (Objects.equals(name, jamesCameron.getName()) && Objects.equals(surname, jamesCameron.getSurname())) {
      return Optional.of(jamesCameron);
    } else if (Objects.equals(name, peterJackson.getName()) && Objects.equals(surname, peterJackson.getSurname())) {
      return Optional.of(peterJackson);
    } else {
      return Optional.empty();
    }
  }




  private List<Film> mockJpaFilmDAOFindByDirectorId(Long id) {
      if (id == 1L) {
        System.out.println("Cameron");
        return jamesCameron.getfilmsProduced();
      } else if (id == 2L) {
        System.out.println("Jackson");
        return peterJackson.getfilmsProduced();
      } else {
        return new ArrayList<>();
      }
  }

  private Film mockJpaFilmDAOSave(Film film) {
    film.setId(filmId++);
    return film;
  }

  private Optional<Film> mockJpaFilmDAOFindById(long id) {
    switch ((int)id) {
      case 1:
        return Optional.of(hihihi1);
      case 2:
        return Optional.of(hihihi2);
      case 3:
        return Optional.of(hihihi3);
      case 4:
        return Optional.of(deBonMatin);
      default: 
        return Optional.empty();
    }
  }

  private Optional<Film> mockJpaFilmDAOFindByTitle(String title) {
    switch (title) {
      case "hihihi1":
        return Optional.of(hihihi1);
      case "hihihi2":
        return Optional.of(hihihi2);
      case "hihihi3":
        return Optional.of(hihihi3);
      case "de bon matin":
        return Optional.of(deBonMatin);
      default:
        return Optional.empty();
    }
  }

  private Film mockJpaFilmDAOUpdate(long id, Film film) throws ServiceException {
    Film filmFound = null;
    switch ((int) id) {
      case 1:
        filmFound = hihihi1;
        break;
      case 2:
        filmFound = hihihi2;
        break;
      case 3:
        filmFound = hihihi3;
        break;
      case 4: 
        filmFound = deBonMatin;
        break;
      default:
        throw new ServiceException("Impossible de mettre à jour le film");
    }

    filmFound.setDirector(film.getDirector());
    filmFound.setDuration(film.getDuration());
    filmFound.setGenre(film.getGenre());
    filmFound.setTitle(film.getTitle());
    return filmFound;
  }

  private void mockJpaFilmDAODelete(Film film) throws ServiceException {
    
    Director director;

    if (film.getDirector().getId() == 1) {
      director = jamesCameron;
    } else if (film.getDirector().getId() == 2) {
      director = peterJackson;
    } else {
      return;
    }

    List<Film> films = director.getfilmsProduced();
    System.out.println(films);
    if (Objects.equals(film, hihihi1)) {
      films.remove(hihihi1);
      jamesCameron.setfilmsProduced(films);
      hihihi1 = null;
    } else if (Objects.equals(film, hihihi2)) {
      films.remove(hihihi2);
      jamesCameron.setfilmsProduced(films);
      hihihi2 = null;
    } else if (Objects.equals(film, hihihi3)) {
      films.remove(hihihi3);
      jamesCameron.setfilmsProduced(films);
      hihihi3 = null;
    } else if (Objects.equals(film, deBonMatin)) {
      films.remove(deBonMatin);
      peterJackson.setfilmsProduced(films);
      deBonMatin = null;
    }

    System.out.println(jamesCameron.getfilmsProduced());
    System.out.println(films);
  }

  private void mockJpaDirectorDAODelete(long id) {
    switch((int) id) {
      case 1:
        jamesCameron = null;
        break;
      case 2:
        peterJackson = null;
        break;
      default:
        return;
    }
  }

  private List<Genre> mockJpaGenreDAOFindAll() throws ServiceException {
      List<Genre> allGenres = new ArrayList<>();
      for (Genre genre : genres) {
          allGenres.add(genre);
      }
      return allGenres;
  }

  private Optional<Genre> mockJpaGenreDAOFindById(long id) {
    int intId = (int) id;
    if (id >= 1 && id <= genres.size()) {
      return Optional.of(genres.get(intId - 1));
    } else {
      return Optional.empty();
    }
  }

  private Genre mockJpaGenreDAOUpdate(long id, String name) throws ServiceException {
    int intId = (int) id;
    if (intId >= 1 && intId <= genres.size()) {
      Genre genre = genres.get(intId-1);
      genre.setName(name);
      return genre;
    } else {
      throw new ServiceException("Impossible de mettre à jour le genre");
    }
  }

  @BeforeEach 
  void setUp() {
    when(jpaDirectorDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaDirectorDAOFindById(invocation.getArgument(0));
    });

    when(jpaGenreDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaGenreDAOFindById(invocation.getArgument(0));
    });

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
  @Test 
  void whenDirectorHasAtLeast3Movies_thenShouldBeFamous() throws ServiceException{
    when(jpaFilmDAO.findByDirectorId(any(Long.class))).thenAnswer(invocation -> {
      return mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0));
    });
    when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> {
        return mockJpaDirectorDAOUpdate(invocation.getArgument(0), invocation.getArgument(1));
    });
    jamesCameron = myFilmsServiceImpl.updateDirectorFamous(jamesCameron);
    assertEquals(Boolean.TRUE, jamesCameron.isFamous());
    peterJackson = myFilmsServiceImpl.updateDirectorFamous(peterJackson);
    assertEquals(Boolean.FALSE, peterJackson.isFamous());
    Exception exception = assertThrows(ServiceException.class, () -> {
        myFilmsServiceImpl.updateDirectorFamous(erreurInterne);
    });
    assertEquals("Erreur lors de la mise à jour de la célébrité",exception.getMessage());
  }

  @Test 
  void whenDirectorAmongDirectorsHasAtLeast3Movies_thenShouldBeFamous() throws ServiceException{
      when(jpaFilmDAO.findByDirectorId(any(Long.class))).thenAnswer(invocation -> {
          return mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0));
      });
    
    when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> {
        return invocation.getArgument(1);
    });

    List<Director> directors = new ArrayList<>();
    directors.add(jamesCameron);
    directors.add(peterJackson);

    List <Director> directorsFamouss = myFilmsServiceImpl.updateDirectorFamouss(directors);
    assertEquals(1, directorsFamouss.size());
    assertEquals(Boolean.TRUE, jamesCameron.isFamous());
    assertEquals(Boolean.FALSE, peterJackson.isFamous());
  }

  @Test 
  void whenFindAll_thenShouldHaveAllFilms() throws ServiceException{
    when(jpaFilmDAO.findAll()).thenAnswer(invocation -> {
      List<Film> allFilms = new ArrayList<>();
      allFilms.addAll(jamesCameron.getfilmsProduced());
      allFilms.addAll(peterJackson.getfilmsProduced());
      return allFilms;
    });

    List<Film> allFilmsExpected = new ArrayList<>();
    allFilmsExpected.addAll(jamesCameron.getfilmsProduced());
    allFilmsExpected.addAll(peterJackson.getfilmsProduced());
    List<Film> allFilms = myFilmsServiceImpl.findAll();
    assertEquals(allFilmsExpected, allFilms);
  }

  @Test 
  void whenCreateFilm_thenShouldHaveCreateFilm() throws ServiceException {
    when(jpaDirectorDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaDirectorDAOFindById(invocation.getArgument(0));
    });

    when(jpaFilmDAO.save(any(Film.class))).thenAnswer(invocation -> {
      return mockJpaFilmDAOSave(invocation.getArgument(0));
    });

    when(jpaFilmDAO.findByDirectorId(any(Long.class))).thenAnswer(invocation -> {
      return mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0));
    });
    
    when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> invocation.getArgument(1));

    FilmForm onAttendPasPatrick1 = new FilmForm();
    onAttendPasPatrick1.setDuration(90);
    onAttendPasPatrick1.setDirectorId(2);
    onAttendPasPatrick1.setTitle("on attend pas Patrick 1");

    FilmForm onAttendPasPatrick2 = new FilmForm();
    onAttendPasPatrick2.setDuration(90);
    onAttendPasPatrick2.setDirectorId(2);
    onAttendPasPatrick2.setTitle("on attend pas Patrick 2");

    FilmForm onAttendPasPatrick3 = new FilmForm();
    onAttendPasPatrick3.setDuration(90);
    onAttendPasPatrick3.setDirectorId(2);
    onAttendPasPatrick3.setTitle("on attend pas Patrick 3");

    try {
      peterJackson = myFilmsServiceImpl.updateDirectorFamous(peterJackson);
      assertEquals(Boolean.FALSE, peterJackson.isFamous());
      Film onAttendPasPatrick_1 = filmMapper.convertFilmDTOToFilm(myFilmsServiceImpl.createFilm(onAttendPasPatrick1));
      Film onAttendPasPatrick_2 = filmMapper.convertFilmDTOToFilm(myFilmsServiceImpl.createFilm(onAttendPasPatrick2));
      Film onAttendPasPatrick_3 = filmMapper.convertFilmDTOToFilm(myFilmsServiceImpl.createFilm(onAttendPasPatrick3));
      List<Film> onAttendPasPatrick = new ArrayList<>();
      onAttendPasPatrick.add(onAttendPasPatrick_1);
      onAttendPasPatrick.add(onAttendPasPatrick_2);
      onAttendPasPatrick.add(onAttendPasPatrick_3);
      peterJackson.setfilmsProduced(onAttendPasPatrick);
      peterJackson = myFilmsServiceImpl.updateDirectorFamous(peterJackson);
    } catch (ServiceException e) {
      System.out.println("whenCreateFilm_thenShouldHaveCreatFilm error");
    }

    assertEquals(Boolean.TRUE, peterJackson.isFamous());
  }

  @Test
  void whenFindFilmById_thenShouldHaveFilm() {
    when(jpaFilmDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaFilmDAOFindById(invocation.getArgument(0));
    });

    try {
      Film filmFound = myFilmsServiceImpl.findFilmById(1);
      ServiceException filmNotFoundError = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.findFilmById(100));
      assertEquals(hihihi1, filmFound);
      assertEquals("Le film demandé n'existe pas", filmNotFoundError.getMessage());
    } catch (ServiceException e) {
      System.out.println("whenFindFilmById_thenShouldHaveFilm error");
    }
  }

  @Test 
  void whenFindFilmByTitle_thenShouldHaveFilm() {
    when(jpaFilmDAO.findByTitle(anyString())).thenAnswer(invocation -> {
      return mockJpaFilmDAOFindByTitle(invocation.getArgument(0));
    });

    try {
      Film filmFound = myFilmsServiceImpl.findFilmByTitle("hihihi1");
      ServiceException filmNotFoundError = assertThrows(ServiceException.class,() -> myFilmsServiceImpl.findFilmByTitle("not existing"));
      assertEquals(hihihi1, filmFound);
      assertEquals("Le film demandé n'existe pas", filmNotFoundError.getMessage());
    } catch (ServiceException e) {
      System.out.println("whenFilmByTitle_thenShouldHaveFilm error");
    }

  }

  @Test
  void whenFindFilmByDirectorId_thenShouldHaveFilm() {
    when(jpaFilmDAO.findByDirectorId(anyLong())).thenAnswer(invocation -> {
      return mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0));
    });

    try {
      assertEquals(jamesCameron.getfilmsProduced(), myFilmsServiceImpl.findFilmByDirectorId(1));
    } catch (ServiceException e) {
      System.out.println("whenFindFilmByDirectorId_thenShouldHaveFilm error");
    }

    ServiceException exception = assertThrows(ServiceException.class, () -> {
        myFilmsServiceImpl.findFilmByDirectorId(100L);
    });
    assertEquals("Le réalistauer n'a réalisé aucun film",exception.getMessage());
  }

  @Test 
  void whenUpdateFilm_thenShouldUpdateFilm() throws ServiceException{
    when(jpaFilmDAO.update(anyLong(), any(Film.class))).thenAnswer(invocation -> {
      return mockJpaFilmDAOUpdate(invocation.getArgument(0), invocation.getArgument(1));
    });

    FilmForm hahaha1 = new FilmForm();
    hahaha1.setDirectorId(1);
    hahaha1.setDuration(100);
    hahaha1.setGenreId(1);
    hahaha1.setTitle("hahaha1");
    myFilmsServiceImpl.updateFilm(1L, hahaha1);
    Film newHahaha = filmMapper.convertFilmFormToFilm(hahaha1);
    newHahaha.setId(1);
    assertEquals(newHahaha, hihihi1);

    ServiceException serviceException = assertThrows(ServiceException.class, () -> {
      myFilmsServiceImpl.updateFilm(100, hahaha1);
    });

    assertEquals(serviceException.getMessage(), "Impossible de mettre à jour le film");

  }

  @Test 
  void whenDeleteFilm_thenShouldDeleteFilm() throws ServiceException {
    doAnswer(invocation -> {
      mockJpaFilmDAODelete(invocation.getArgument(0));
      return null;
    }).when(jpaFilmDAO).delete(any(Film.class));
    
    when(jpaFilmDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaFilmDAOFindById(invocation.getArgument(0));
    });

    when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> {
      return mockJpaDirectorDAOUpdate(invocation.getArgument(0), invocation.getArgument(1));
    });

    when(jpaFilmDAO.findByDirectorId(anyLong())).thenAnswer(invocation -> {
      return mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0));
    });

    System.out.println(jamesCameron.getfilmsProduced());
    jamesCameron.setFamous(true);
    assertEquals(true, jamesCameron.isFamous());
    myFilmsServiceImpl.deleteFilm(1L);
    System.out.println(jamesCameron.getfilmsProduced());
    assertEquals(null, hihihi1);
    assertEquals(false, jamesCameron.isFamous());
    assertEquals(2, jamesCameron.getfilmsProduced().size());

    ServiceException serviceException = assertThrows(ServiceException.class, () -> {
      myFilmsServiceImpl.deleteFilm(100L);
    });

    assertEquals("Le film demandé n'existe pas", serviceException.getMessage());
  }


  @Test 
  void whenUpdateDirector_thenShouldHaveUpdatedDirector() throws ServiceException{
    when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> {
      return mockJpaDirectorDAOUpdate(invocation.getArgument(0), invocation.getArgument(1));
    });

    DirectorForm francisCameronForm = new DirectorForm();
    francisCameronForm.setBirthdate(jamesCameron.getBirthdate());
    francisCameronForm.setName(jamesCameron.getName());
    francisCameronForm.setSurname("Francis");
    
    Director francisCameron = new Director(jamesCameron);
    francisCameron.setSurname("Francis");
    francisCameron.setId(1L);
    assertNotEquals(francisCameron, jamesCameron);
    try {
      DirectorDTO temp = myFilmsServiceImpl.updateDirector(jamesCameron.getId(), francisCameronForm);
      jamesCameron = DirectorMapper.convertDirectorDTOToDirector(temp);
      assertEquals(francisCameron, jamesCameron);
    } catch (ServiceException e) {
      System.out.println("whenUpdateRealisateur_thenShouldHaveUpdatedRealisateur error");
    }
  }

  @Test 
  void whenCreateDirector_thenShouldCreateDirector() throws ServiceException {
    when(jpaDirectorDAO.save(any(Director.class))).thenAnswer(invocation -> {
      return mockJpaDirectorDAOSave(invocation.getArgument(0));
    });

    DirectorForm directorForm = new DirectorForm();
    directorForm.setBirthdate(LocalDate.of(2000, 03, 20));
    directorForm.setName("director");
    directorForm.setSurname("form");

    DirectorDTO directorDTO = myFilmsServiceImpl.createDirector(directorForm);
    directorDTO.setId(3L);
    DirectorDTO directorExpected = new DirectorDTO();
    directorExpected.setBirthdate(LocalDate.of(2000, 03, 20));
    directorExpected.setFamous(false);
    directorExpected.setId(3L);
    directorExpected.setName("director");
    directorExpected.setSurname("form");
    assertEquals(DirectorMapper.convertDirectorDTOToDirector(directorExpected), DirectorMapper.convertDirectorDTOToDirector(directorDTO));
  }

  @Test 
  void whenFindDirectorById_thenShouldHaveDirector() throws ServiceException {
    when(jpaDirectorDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaDirectorDAOFindById(invocation.getArgument(0));
    });

    assertEquals(jamesCameron, myFilmsServiceImpl.findDirectorById(1L));
    ServiceException serviceException = assertThrows(ServiceException.class, () -> {
      myFilmsServiceImpl.findDirectorById(100L);
    });
    assertEquals("Le réalisateur demandé n'existe pas", serviceException.getMessage());
  }

  @Test
  void whenFindDirectorBySurnameAndName_thenShouldHaveDirector() throws ServiceException {
    when(jpaDirectorDAO.findBySurnameAndName(anyString(), anyString())).thenAnswer(invocation -> {
      return mockJpaDirectorFindBySurnameAndName(invocation.getArgument(0), invocation.getArgument(1));
    });
    String JAMES = "James";
    String CAMERON = "Cameron";
    String PETER = "Peter";
    String JACKSON = "Jackson";
    String UNKNOWN = "unknown";
    assertEquals(jamesCameron, myFilmsServiceImpl.findDirectorBySurnameAndName(JAMES, CAMERON));
    assertEquals(peterJackson, myFilmsServiceImpl.findDirectorBySurnameAndName(PETER, JACKSON));
    ServiceException serviceException = assertThrows(ServiceException.class, () -> {
      myFilmsServiceImpl.findDirectorBySurnameAndName(UNKNOWN, UNKNOWN);
    });
    assertEquals("Le réalisateur demandé n'existe pas", serviceException.getMessage());
  }

  @Test
  void whenDeleteDirector_thenShouldDeleteDirector() throws ServiceException {
    doAnswer(invocation -> {
      mockJpaDirectorDAODelete(invocation.getArgument(0));
      return null;
    }).when(jpaDirectorDAO).delete(anyLong());

    myFilmsServiceImpl.deleteDirector(1L);
    assertEquals(null, jamesCameron);
  }

  @Test 
  void whenFindAllGenres_thenShouldFindAllGenres() throws ServiceException{
    when(jpaGenreDAO.findAll()).thenAnswer(invocation -> {
      return mockJpaGenreDAOFindAll();
    });

    List<Genre> allGenres = myFilmsServiceImpl.findAllGenres();
    assertEquals(genres, allGenres);
  }

  @Test 
  void whenFindGenreById_thenShouldHaveGenre() throws ServiceException {
    when(jpaGenreDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaGenreDAOFindById(invocation.getArgument(0));
    });

    Genre genre = myFilmsServiceImpl.findGenreById(1L);
    assertEquals(genres.get(0), genre);

    ServiceException serviceException = assertThrows(ServiceException.class, () -> {
      myFilmsServiceImpl.findGenreById(100L);
    });
    assertEquals("Le genre demandé n'existe pas", serviceException.getMessage());
  }

  @Test 
  void whenUpdateGenre_thenShouldUpdateGenre() throws ServiceException {
    when(jpaGenreDAO.update(anyLong(), anyString())).thenAnswer(invocation -> {
      return mockJpaGenreDAOUpdate(invocation.getArgument(0), invocation.getArgument(1));
    });

    Genre newAction = new Genre();
    newAction.setId(1);
    newAction.setName("truc");

    myFilmsServiceImpl.updateGenre(1L, "truc");
    assertEquals(newAction, genres.get(0));

    ServiceException serviceException = assertThrows(ServiceException.class, () -> {
      myFilmsServiceImpl.updateGenre(100L, "truc");
    });

    assertEquals("Impossible de mettre à jour le genre", serviceException.getMessage());
  }

  @Test
  void whenCalculerDurationTotale_thenShouldHaveDurationTotale() {
    List<Double> pasDeNote = new ArrayList<>();
    List<Double> troisNotes=  new ArrayList<>();
    troisNotes.add(Double.valueOf(11f)); 
    troisNotes.add(Double.valueOf(19.5f));
    troisNotes.add(Double.valueOf(14.5f));
    List<Double> notesComplexes = new ArrayList<>();
    notesComplexes.addAll(troisNotes);
    notesComplexes.add(Double.valueOf(7.123213f));
    notesComplexes.add(Double.valueOf(1.123213f));
    Double moyenne = Double.valueOf(15f);
    assertEquals(moyenne, myFilmsServiceImpl.calculerNoteMoyenne(troisNotes).get());
    assertEquals(Boolean.TRUE, myFilmsServiceImpl.calculerNoteMoyenne(pasDeNote).isEmpty());
    assertEquals(Double.valueOf(10.65), myFilmsServiceImpl.calculerNoteMoyenne(notesComplexes).get());
  }

  @Test 
  void whenCalculerNoteMoyenne_thenShouldHaveNoteMoyenne() {
    List<Double> notes = new ArrayList<>();
    notes.add(10.3);
    notes.add(17.4);
    notes.add(14.3);

    Optional<Double> noteMoyenne = myFilmsServiceImpl.calculerNoteMoyenne(notes);
    List<Double> notesVides = new ArrayList<>();
    Optional<Double> noteMoyenneVide = myFilmsServiceImpl.calculerNoteMoyenne(notesVides);
    assertEquals(Optional.of(14.0), noteMoyenne);
    assertEquals(Optional.empty(), noteMoyenneVide);
  }
} 