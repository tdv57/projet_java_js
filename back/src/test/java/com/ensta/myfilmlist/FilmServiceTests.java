package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dao.impl.*;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.dao.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.service.impl.MyFilmsServiceImpl;
import com.ensta.myfilmlist.form.*;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.*;
import com.ensta.myfilmlist.dao.impl.*;

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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
  private JpaRealisateurDAO jpaRealisateurDAO;

  @MockBean 
  private JpaGenreDAO jpaGenreDAO;

  @MockBean 
  private JpaFilmDAO jpaFilmDAO;

  @Autowired
  private FilmMapper filmMapper; 

  @Autowired 
  private MyFilmsServiceImpl myFilmsServiceImpl;

  private Realisateur jamesCameron = new Realisateur();
  private Realisateur peterJackson = new Realisateur();
  private Film hihihi1 = new Film();
  private Film hihihi2 = new Film();
  private Film hihihi3 = new Film();
  private Film deBonMatin = new Film();
  private Realisateur erreurInterne = new Realisateur();
  private static int count = 0;
  private static Long realisateurId = Long.valueOf(1L);
  private static Long filmId = Long.valueOf(2L);


  private Optional<Realisateur> mockJpaRealisateurDAOFindById(long id) {
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

  private Optional<Realisateur> mockJpaRealisateurDAOFindById(Long id) {
      if (id == Long.valueOf(1)) {
        return Optional.of(jamesCameron);
      } else if (id == Long.valueOf(2)) {
        return Optional.of(peterJackson);
      } else {
        return Optional.empty();
      }
  }

    private List<Film> mockJpaFilmDAOFindByRealisateurId(Long id) throws ServiceException {
      if (id == 1L) {
        System.out.println("Cameron");
        return jamesCameron.getFilmRealises();
      } else if (id == 2L) {
        System.out.println("Jackson");
        return peterJackson.getFilmRealises();
      } else {
        System.out.println("Erreur");
        throw new ServiceException("Erreur lors de l'update de célébrité du réalisateur");
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
    when(jpaRealisateurDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaRealisateurDAOFindById(invocation.getArgument(0));
    });

    when(jpaGenreDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaGenreDaoFindById(invocation.getArgument(0));
    });

    System.out.println("\n");
    System.out.println("Debut test n°" + count);
    System.out.println("\n");
    jamesCameron.setCelebre(false);
    jamesCameron.setDateNaissance(LocalDate.of(1950, 03, 20));
    jamesCameron.setId(Long.valueOf(1L));
    jamesCameron.setNom("Cameron");
    jamesCameron.setPrenom("James");

    peterJackson.setCelebre(false);
    peterJackson.setDateNaissance(LocalDate.of(1980,02,02));
    peterJackson.setId(Long.valueOf(2L));
    peterJackson.setNom("Jackson");
    peterJackson.setPrenom("Peter");
    
    realisateurId = Long.valueOf(3L);

    hihihi1.setDuree(60);
    hihihi1.setRealisateur(jamesCameron);
    hihihi1.setTitre("hihihi1");
    hihihi1.setId(Long.valueOf(1L));

    hihihi2.setDuree(61);
    hihihi2.setRealisateur(jamesCameron);
    hihihi2.setTitre("hihihi2");
    hihihi2.setId(Long.valueOf(2L));

    hihihi3.setDuree(62);
    hihihi3.setRealisateur(jamesCameron);
    hihihi3.setTitre("hihihi3");
    hihihi3.setId(Long.valueOf(3L));

    deBonMatin.setDuree(90);
    deBonMatin.setId(Long.valueOf(4L));
    deBonMatin.setRealisateur(peterJackson);
    deBonMatin.setTitre("de bon matin");
    
    filmId = Long.valueOf(5L);

    List<Film> jamesCameronFilms = new ArrayList<>();
    jamesCameronFilms.add(hihihi1);
    jamesCameronFilms.add(hihihi2);
    jamesCameronFilms.add(hihihi3);
    jamesCameron.setFilmRealises(jamesCameronFilms);
    
    List<Film> peterJacksonFilms = new ArrayList<>();
    peterJacksonFilms.add(deBonMatin);
    peterJackson.setFilmRealises(peterJacksonFilms);

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
  void whenRealisateurHasAtLeast3Movies_thenShouldBeCelebre() throws ServiceException{
    when(jpaFilmDAO.findByRealisateurId(any(Long.class))).thenAnswer(invocation -> {
      return mockJpaFilmDAOFindByRealisateurId(invocation.getArgument(0));
    });
    when(jpaRealisateurDAO.update(anyLong(), any(Realisateur.class))).thenAnswer(invocation -> {
        return invocation.getArgument(1);
    });
    jamesCameron = myFilmsServiceImpl.updateRealisateurCelebre(jamesCameron);
    assertEquals(Boolean.TRUE, jamesCameron.isCelebre());
    peterJackson = myFilmsServiceImpl.updateRealisateurCelebre(peterJackson);
    assertEquals(Boolean.FALSE, peterJackson.isCelebre());
    Exception exception = assertThrows(ServiceException.class, () -> {
        myFilmsServiceImpl.updateRealisateurCelebre(erreurInterne);
    });
    assertEquals("Erreur lors de la mise à jour de la célébrité",exception.getMessage());
  }

  @Test
  void whenCalculerDureeTotale_thenShouldHaveLaDureeTotale() {
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
  void whenRealisateurAmongRealisateursHasAtLeast3Movies_thenShouldBeCelebre() throws ServiceException{
      when(jpaFilmDAO.findByRealisateurId(any(Long.class))).thenAnswer(invocation -> {
        try {
          return mockJpaFilmDAOFindByRealisateurId(invocation.getArgument(0));
        } catch(ServiceException e) {
            return new ArrayList<>();
        }
      });
    
    when(jpaRealisateurDAO.update(anyLong(), any(Realisateur.class))).thenAnswer(invocation -> {
        return invocation.getArgument(1);
    });

    List<Realisateur> realisateurs = new ArrayList<>();
    realisateurs.add(jamesCameron);
    realisateurs.add(peterJackson);

    List <Realisateur> realisateursCelebres = myFilmsServiceImpl.updateRealisateurCelebres(realisateurs);
    assertEquals(1, realisateursCelebres.size());
    assertEquals(Boolean.TRUE, jamesCameron.isCelebre());
    assertEquals(Boolean.FALSE, peterJackson.isCelebre());
  }

  @Test 
  void whenFindAll_thenShouldHaveAllFilms() throws ServiceException{
    when(jpaFilmDAO.findAll()).thenAnswer(invocation -> {
      List<Film> allFilms = new ArrayList<>();
      allFilms.addAll(jamesCameron.getFilmRealises());
      allFilms.addAll(peterJackson.getFilmRealises());
      return allFilms;
    });

    List<Film> allFilmsExpected = new ArrayList<>();
    allFilmsExpected.addAll(jamesCameron.getFilmRealises());
    allFilmsExpected.addAll(peterJackson.getFilmRealises());
    List<Film> allFilms = myFilmsServiceImpl.findAll();
    assertEquals(allFilmsExpected, allFilms);
  }

  @Test 
  void whenCreateFilm_thenShouldHaveCreateFilm() throws ServiceException {
    when(jpaRealisateurDAO.findById(anyLong())).thenAnswer(invocation -> {
      return mockJpaRealisateurDAOFindById(invocation.getArgument(0));
    });

    when(jpaFilmDAO.save(any(Film.class))).thenAnswer(invocation -> {
      return mockJpaFilmDAOSave(invocation.getArgument(0));
    });

    when(jpaFilmDAO.findByRealisateurId(any(Long.class))).thenAnswer(invocation -> {
      return mockJpaFilmDAOFindByRealisateurId(invocation.getArgument(0));
    });
    
    when(jpaRealisateurDAO.update(anyLong(), any(Realisateur.class))).thenAnswer(invocation -> invocation.getArgument(1));

    FilmForm onAttendPasPatrick1 = new FilmForm();
    onAttendPasPatrick1.setDuree(90);
    onAttendPasPatrick1.setRealisateurId(2);
    onAttendPasPatrick1.setTitre("on attend pas Patrick 1");

    FilmForm onAttendPasPatrick2 = new FilmForm();
    onAttendPasPatrick2.setDuree(90);
    onAttendPasPatrick2.setRealisateurId(2);
    onAttendPasPatrick2.setTitre("on attend pas Patrick 2");

    FilmForm onAttendPasPatrick3 = new FilmForm();
    onAttendPasPatrick3.setDuree(90);
    onAttendPasPatrick3.setRealisateurId(2);
    onAttendPasPatrick3.setTitre("on attend pas Patrick 3");

    try {
      peterJackson = myFilmsServiceImpl.updateRealisateurCelebre(peterJackson);
      assertEquals(Boolean.FALSE, peterJackson.isCelebre());    
      Film onAttendPasPatrick_1 = filmMapper.convertFilmDTOToFilm(myFilmsServiceImpl.createFilm(onAttendPasPatrick1));
      Film onAttendPasPatrick_2 = filmMapper.convertFilmDTOToFilm(myFilmsServiceImpl.createFilm(onAttendPasPatrick2));
      Film onAttendPasPatrick_3 = filmMapper.convertFilmDTOToFilm(myFilmsServiceImpl.createFilm(onAttendPasPatrick3));
      List<Film> onAttendPasPatrick = new ArrayList<>();
      onAttendPasPatrick.add(onAttendPasPatrick_1);
      onAttendPasPatrick.add(onAttendPasPatrick_2);
      onAttendPasPatrick.add(onAttendPasPatrick_3);
      peterJackson.setFilmRealises(onAttendPasPatrick);
      peterJackson = myFilmsServiceImpl.updateRealisateurCelebre(peterJackson);
    } catch (ServiceException e) {
      System.out.println("whenCreateFilm_thenShouldHaveCreatFilm error");
    }

    assertEquals(Boolean.TRUE, peterJackson.isCelebre());  
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

} 