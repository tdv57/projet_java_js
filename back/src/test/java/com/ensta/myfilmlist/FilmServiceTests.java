package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dao.impl.*;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.dao.*;
import com.ensta.myfilmlist.model.*;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.service.impl.MyFilmsServiceImpl;
import com.ensta.myfilmlist.form.*;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.exception.ServiceException;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations; // si tu initialises les mocks manuellement
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTests {
  @Mock 
  private JdbcFilmDAO jdbcFilmDAO;
  
  @Mock 
  private JdbcRealisateurDAO jdbcRealisateurDAO;

  @InjectMocks 
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

  private Optional<Realisateur> mockJdbcRealisateurDAOFindById(Long id) {
      if (id == Long.valueOf(1)) {
        return Optional.of(jamesCameron);
      } else if (id == Long.valueOf(2)) {
        return Optional.of(peterJackson);
      } else {
        return Optional.empty();
      }
  }

  private List<Film> mockJdbcFilmDAOFindByRealisateurId(Long id) throws ServiceException {
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

  private Film mockJdbcFilmDAOSave(Film film) {
    film.setId(filmId++);
    return film;
  }

  @BeforeEach 
  void setUp() {
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
    when(jdbcFilmDAO.findByRealisateurId(any(Long.class))).thenAnswer(invocation -> {
      return mockJdbcFilmDAOFindByRealisateurId(invocation.getArgument(0));
    });
    when(jdbcRealisateurDAO.update(any(Realisateur.class))).thenAnswer(invocation -> {
        return invocation.getArgument(0);
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
      when(jdbcFilmDAO.findByRealisateurId(any(Long.class))).thenAnswer(invocation -> {
        try {
          return mockJdbcFilmDAOFindByRealisateurId(invocation.getArgument(0));
        } catch(ServiceException e) {
            return new ArrayList<>();
        }
      });
    
    when(jdbcRealisateurDAO.update(any(Realisateur.class))).thenAnswer(invocation -> {
        return invocation.getArgument(0);
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
    when(jdbcFilmDAO.findAll()).thenAnswer(invocation -> {
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
  void whenCreateFilm_thenShouldHaveCreatFilm() {
    when(jdbcRealisateurDAO.findById(Long.class)).thenAnswer(invocation -> {
      return mockJdbcRealisateurDAOFindById(invocation.getArgument(0));
    });

    when(jdbcFilmDAO.save(any(Film.class))).thenAnswer(invocation -> {
      return mockJdbcFilmDAOSave(invocation.getArgument(0));
    });

    when(jdbcFilmDAO.findByRealisateurId(any(Long.class))).thenAnswer(invocation -> {
      return mockJdbcFilmDAOFindByRealisateurId(invocation.getArgument(0));
    });
    
    when(jdbcRealisateurDAO.update(any(Realisateur.class))).thenAnswer(invocation -> {
        return invocation.getArgument(0);
    });

    FilmForm onAttendPasPatrick1 = new FilmForm();
    filmForm.setDuree(90);
    filmForm.setRealisateurId(2);
    filmForm.setTitre("on attend pas Patrick 1");

    FilmForm onAttendPasPatrick2 = new FilmForm();
    filmForm.setDuree(90);
    filmForm.setRealisateurId(2);
    filmForm.setTitre("on attend pas Patrick 2");

    FilmForm onAttendPasPatrick3 = new FilmForm();
    filmForm.setDuree(90);
    filmForm.setRealisateurId(2);
    filmForm.setTitre("on attend pas Patrick 3");

    peterJackson = myFilmsServiceImpl.updateRealisateurCelebre(peterJackson);
    assertEquals(Boolean.FALSE, peterJackson.isCelebre());    
    Film onAttendPasPatrick_1 = FilmMapper.convertFilmDTOToFilm(myFilmsServiceImpl.createFilm(onAttendPasPatrick1));
    Film onAttendPasPatrick_2 = FilmMapper.convertFilmDTOToFilm(myFilmsServiceImpl.createFilm(onAttendPasPatrick2));
    Film onAttendPasPatrick_3 = FilmMapper.convertFilmDTOToFilm(myFilmsServiceImpl.createFilm(onAttendPasPatrick3));
    peterJackson = myFilmsServiceImpl.updateRealisateurCelebre(peterJackson);
    assertEquals(Boolean.TRUE, peterJackson.isCelebre());  
  }
} 