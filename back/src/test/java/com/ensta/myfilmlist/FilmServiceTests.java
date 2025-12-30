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
import java.util.Optional;

import org.junit.jupiter.api.Test;
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

  private Director mockJpaDirectorDAOUpdate(long id, Director director) throws ServiceException {
    switch ((int)id) {
      case 1:
        jamesCameron = director;
        return jamesCameron;
      case 2:
        peterJackson = director;
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

  private Optional<Genre> mockJpaGenreDaoFindById(long id) {
    return Optional.empty();
  }

  private Optional<Director> mockJpaDirectorDAOFindById(Long id) {
      if (id == Long.valueOf(1)) {
        return Optional.of(jamesCameron);
      } else if (id == Long.valueOf(2)) {
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

  @BeforeEach 
  void setUp() {
    when(jpaDirectorDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaDirectorDAOFindById(invocation.getArgument(0));
    });

    when(jpaGenreDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaGenreDaoFindById(invocation.getArgument(0));
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

    hihihi1.setDuration(60);
    hihihi1.setDirector(jamesCameron);
    hihihi1.setTitle("hihihi1");
    hihihi1.setId(Long.valueOf(1L));

    hihihi2.setDuration(61);
    hihihi2.setDirector(jamesCameron);
    hihihi2.setTitle("hihihi2");
    hihihi2.setId(Long.valueOf(2L));

    hihihi3.setDuration(62);
    hihihi3.setDirector(jamesCameron);
    hihihi3.setTitle("hihihi3");
    hihihi3.setId(Long.valueOf(3L));

    deBonMatin.setDuration(90);
    deBonMatin.setId(Long.valueOf(4L));
    deBonMatin.setDirector(peterJackson);
    deBonMatin.setTitle("de bon matin");
    
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
  void whenCalculerDurationTotale_thenShouldHaveLaDurationTotale() {
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
  void whenFilmByTitle_thenShouldHaveFilm() {
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
  void whenUpdateRealisateur_thenShouldHaveUpdatedRealisateur() throws ServiceException{
    when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> {
      return mockJpaDirectorDAOUpdate(invocation.getArgument(0), invocation.getArgument(1));
    });

    DirectorForm francisCameronForm = new DirectorForm();
    francisCameronForm.setBirthdate(jamesCameron.getBirthdate());
    francisCameronForm.setName(jamesCameron.getName());
    francisCameronForm.setSurname("Francis");
    
    Director francisCameron = new Director(jamesCameron);
    francisCameron.setSurname("francis");
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

} 