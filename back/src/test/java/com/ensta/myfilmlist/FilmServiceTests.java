package com.ensta.myfilmlist;

import com.ensta.myfilmlist.dao.impl.JpaDirectorDAO;
import com.ensta.myfilmlist.dao.impl.JpaFilmDAO;
import com.ensta.myfilmlist.dao.impl.JpaGenreDAO;
import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Director;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Genre;
import com.ensta.myfilmlist.service.impl.MyFilmsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@Sql(
        scripts = "/data_test.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class FilmServiceTests {

    private static int count = 0;
    private static Long filmId = 2L;
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
    private List<Genre> genres = new ArrayList<>();
    private int genreId = 1;

    private Director mockJpaDirectorDAOUpdate(long id, Director director) throws ServiceException {
        switch ((int) id) {
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
        return switch ((int) id) {
            case 1 -> Optional.of(jamesCameron);
            case 2 -> Optional.of(peterJackson);
            default -> Optional.empty();
        };
    }

    private Director mockJpaDirectorDAOSave(Director director) {
        return director;
    }

    private Optional<Director> mockJpaDirectorFindBySurnameAndName(String name, String surname) {
        if (Objects.equals(name, jamesCameron.getName()) && Objects.equals(surname, jamesCameron.getSurname())) {
            return Optional.of(jamesCameron);
        } else if (Objects.equals(name, peterJackson.getName()) && Objects.equals(surname, peterJackson.getSurname())) {
            return Optional.of(peterJackson);
        } else {
            return Optional.empty();
        }
    }

    private List<Film> mockJpaFilmDAOFindByDirectorId(Long id) throws ServiceException {
        if (id == 1L) {
            System.out.println("Cameron");
            return jamesCameron.getFilmsProduced();
        } else if (id == 2L) {
            System.out.println("Jackson");
            return peterJackson.getFilmsProduced();
        } else {
            throw new ServiceException("Director doesn't exist");
        }
    }

    private Film mockJpaFilmDAOSave(Film film) {
        film.setId(filmId++);
        return film;
    }

    private Optional<Film> mockJpaFilmDAOFindById(long id) {
        return switch ((int) id) {
            case 1 -> Optional.of(hihihi1);
            case 2 -> Optional.of(hihihi2);
            case 3 -> Optional.of(hihihi3);
            case 4 -> Optional.of(deBonMatin);
            default -> Optional.empty();
        };
    }

    private Optional<Film> mockJpaFilmDAOFindByTitle(String title) {
        return switch (title) {
            case "hihihi1" -> Optional.of(hihihi1);
            case "hihihi2" -> Optional.of(hihihi2);
            case "hihihi3" -> Optional.of(hihihi3);
            case "de bon matin" -> Optional.of(deBonMatin);
            default -> Optional.empty();
        };
    }

    private Film mockJpaFilmDAOUpdate(long id, Film film) throws ServiceException {
        Film filmFound = switch ((int) id) {
            case 1 -> hihihi1;
            case 2 -> hihihi2;
            case 3 -> hihihi3;
            case 4 -> deBonMatin;
            default -> throw new ServiceException("Impossible de mettre à jour le film");
        };

        filmFound.setDirector(film.getDirector());
        filmFound.setDuration(film.getDuration());
        filmFound.setGenre(film.getGenre());
        filmFound.setTitle(film.getTitle());
        return filmFound;
    }

    private void mockJpaFilmDAODelete(Film film) {

        Director director;

        if (film.getDirector().getId() == 1) {
            director = jamesCameron;
        } else if (film.getDirector().getId() == 2) {
            director = peterJackson;
        } else {
            return;
        }

        List<Film> films = director.getFilmsProduced();
        System.out.println(films);
        if (Objects.equals(film, hihihi1)) {
            films.remove(hihihi1);
            jamesCameron.setFilmsProduced(films);
            hihihi1 = null;
        } else if (Objects.equals(film, hihihi2)) {
            films.remove(hihihi2);
            jamesCameron.setFilmsProduced(films);
            hihihi2 = null;
        } else if (Objects.equals(film, hihihi3)) {
            films.remove(hihihi3);
            jamesCameron.setFilmsProduced(films);
            hihihi3 = null;
        } else if (Objects.equals(film, deBonMatin)) {
            films.remove(deBonMatin);
            peterJackson.setFilmsProduced(films);
            deBonMatin = null;
        }

        System.out.println(jamesCameron.getFilmsProduced());
        System.out.println(films);
    }

    private void mockJpaDirectorDAODelete(long id) {
        switch ((int) id) {
            case 1:
                jamesCameron = null;
                hihihi1 = null;
                hihihi2 = null;
                hihihi3 = null;
                break;
            case 2:
                peterJackson = null;
                deBonMatin = null;
                break;
            default:
        }
    }

    private List<Genre> mockJpaGenreDAOFindAll() {
        List<Genre> allGenres = new ArrayList<>();
        allGenres.addAll(genres);
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
            Genre genre = genres.get(intId - 1);
            genre.setName(name);
            return genre;
        } else {
            throw new ServiceException("Impossible de mettre à jour le genre");
        }
    }

    @BeforeEach
    void setUp() {
        when(jpaDirectorDAO.findById(anyLong())).thenAnswer(invocation -> mockJpaDirectorDAOFindById(invocation.getArgument(0)));

        when(jpaGenreDAO.findById(anyLong())).thenAnswer(invocation -> mockJpaGenreDAOFindById(invocation.getArgument(0)));

        System.out.println("\n");
        System.out.println("Debut test n°" + count);
        System.out.println("\n");
        jamesCameron.setFamous(false);
        jamesCameron.setBirthdate(LocalDate.of(1950, 3, 20));
        jamesCameron.setId(1L);
        jamesCameron.setSurname("Cameron");
        jamesCameron.setName("James");

        peterJackson.setFamous(false);
        peterJackson.setBirthdate(LocalDate.of(1980, 2, 2));
        peterJackson.setId(2L);
        peterJackson.setSurname("Jackson");
        peterJackson.setName("Peter");

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
        hihihi1.setId(1L);
        hihihi1.setGenre(genres.get(2));

        hihihi2.setDuration(61);
        hihihi2.setDirector(jamesCameron);
        hihihi2.setTitle("hihihi2");
        hihihi2.setId(2L);
        hihihi2.setGenre(genres.get(2));

        hihihi3.setDuration(62);
        hihihi3.setDirector(jamesCameron);
        hihihi3.setTitle("hihihi3");
        hihihi3.setId(3L);
        hihihi3.setGenre(genres.get(2));

        deBonMatin.setDuration(90);
        deBonMatin.setId(4L);
        deBonMatin.setDirector(peterJackson);
        deBonMatin.setTitle("de bon matin");
        deBonMatin.setGenre(genres.get(4));

        filmId = 5L;

        List<Film> jamesCameronFilms = new ArrayList<>();
        jamesCameronFilms.add(hihihi1);
        jamesCameronFilms.add(hihihi2);
        jamesCameronFilms.add(hihihi3);
        jamesCameron.setFilmsProduced(jamesCameronFilms);

        List<Film> peterJacksonFilms = new ArrayList<>();
        peterJacksonFilms.add(deBonMatin);
        peterJackson.setFilmsProduced(peterJacksonFilms);

        erreurInterne.setId(1000L);

    }

    @AfterEach
    void setDown() {
        System.out.println("\n");
        System.out.println("Fin test n°" + count);
        count++;
        System.out.println("\n");
    }

    @Test
    void whenDirectorHasAtLeast3Movies_thenShouldBeFamous() throws ServiceException {
        when(jpaFilmDAO.findByDirectorId(any(Long.class))).thenAnswer(invocation -> mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0)));
        when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> mockJpaDirectorDAOUpdate(invocation.getArgument(0), invocation.getArgument(1)));
        jamesCameron = myFilmsServiceImpl.updateDirectorFamous(jamesCameron);
        assertEquals(Boolean.TRUE, jamesCameron.isFamous());
        peterJackson = myFilmsServiceImpl.updateDirectorFamous(peterJackson);
        assertEquals(Boolean.FALSE, peterJackson.isFamous());
        Exception exception = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.updateDirectorFamous(erreurInterne));
        assertEquals("Réalisateur inexistant", exception.getMessage());
    }

    @Test
    void whenDirectorAmongDirectorsHasAtLeast3Movies_thenShouldBeFamous() throws ServiceException {
        when(jpaFilmDAO.findByDirectorId(any(Long.class))).thenAnswer(invocation -> mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0)));

        when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> invocation.getArgument(1));

        List<Director> directors = new ArrayList<>();
        directors.add(jamesCameron);
        directors.add(peterJackson);

        List<Director> directorsFamouss = myFilmsServiceImpl.updateDirectorsFamous(directors);
        assertEquals(1, directorsFamouss.size());
        assertEquals(Boolean.TRUE, jamesCameron.isFamous());
        assertEquals(Boolean.FALSE, peterJackson.isFamous());
    }

    @Test
    void whenFindAll_thenShouldHaveAllFilms() throws ServiceException {
        when(jpaFilmDAO.findAll()).thenAnswer(invocation -> {
            List<Film> allFilms = new ArrayList<>();
            allFilms.addAll(jamesCameron.getFilmsProduced());
            allFilms.addAll(peterJackson.getFilmsProduced());
            return allFilms;
        });

        List<Film> allFilmsExpected = new ArrayList<>();
        allFilmsExpected.addAll(jamesCameron.getFilmsProduced());
        allFilmsExpected.addAll(peterJackson.getFilmsProduced());
        List<Film> allFilms = myFilmsServiceImpl.findAll();
        assertEquals(allFilmsExpected, allFilms);
    }

    @Test
    void whenCreateFilm_thenShouldHaveCreateFilm() throws ServiceException {
        when(jpaDirectorDAO.findById(anyLong())).thenAnswer(invocation -> mockJpaDirectorDAOFindById(invocation.getArgument(0)));

        when(jpaFilmDAO.save(any(Film.class))).thenAnswer(invocation -> mockJpaFilmDAOSave(invocation.getArgument(0)));

        when(jpaFilmDAO.findByDirectorId(any(Long.class))).thenAnswer(invocation -> mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0)));

        when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> invocation.getArgument(1));

        FilmForm onAttendPasPatrick1 = new FilmForm();
        onAttendPasPatrick1.setDuration(90);
        onAttendPasPatrick1.setDirectorId(2);
        onAttendPasPatrick1.setGenreId(1);
        onAttendPasPatrick1.setTitle("on attend pas Patrick 1");

        FilmForm onAttendPasPatrick2 = new FilmForm();
        onAttendPasPatrick2.setDuration(90);
        onAttendPasPatrick2.setDirectorId(2);
        onAttendPasPatrick2.setGenreId(1);
        onAttendPasPatrick2.setTitle("on attend pas Patrick 2");

        FilmForm onAttendPasPatrick3 = new FilmForm();
        onAttendPasPatrick3.setDuration(90);
        onAttendPasPatrick3.setDirectorId(2);
        onAttendPasPatrick3.setGenreId(1);
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
            peterJackson.setFilmsProduced(onAttendPasPatrick);
            peterJackson = myFilmsServiceImpl.updateDirectorFamous(peterJackson);
        } catch (ServiceException e) {
            System.out.println("whenCreateFilm_thenShouldHaveCreatFilm error");
        }

        assertEquals(Boolean.TRUE, peterJackson.isFamous());
    }

    @Test
    void whenFindFilmById_thenShouldHaveFilm() {
        when(jpaFilmDAO.findById(anyLong())).thenAnswer(invocation -> mockJpaFilmDAOFindById(invocation.getArgument(0)));

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
        when(jpaFilmDAO.findByTitle(anyString())).thenAnswer(invocation -> mockJpaFilmDAOFindByTitle(invocation.getArgument(0)));

        try {
            Film filmFound = myFilmsServiceImpl.findFilmByTitle("hihihi1");
            ServiceException filmNotFoundError = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.findFilmByTitle("not existing"));
            assertEquals(hihihi1, filmFound);
            assertEquals("Le film demandé n'existe pas", filmNotFoundError.getMessage());
        } catch (ServiceException e) {
            System.out.println("whenFilmByTitle_thenShouldHaveFilm error");
        }

    }

    @Test
    void whenFindFilmByDirectorId_thenShouldHaveFilm() {
        try {
            when(jpaFilmDAO.findByDirectorId(anyLong())).thenAnswer(invocation -> mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0)));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        try {
            assertEquals(jamesCameron.getFilmsProduced(), myFilmsServiceImpl.findFilmByDirectorId(1));
        } catch (ServiceException e) {
            System.out.println("whenFindFilmByDirectorId_thenShouldHaveFilm error");
        }
        ServiceException exception = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.findFilmByDirectorId(20));
        assertEquals("Director doesn't exist", exception.getMessage());
    }

    @Test
    void whenUpdateFilm_thenShouldUpdateFilm() throws ServiceException {
        when(jpaFilmDAO.update(anyLong(), any(Film.class))).thenAnswer(invocation -> mockJpaFilmDAOUpdate(invocation.getArgument(0), invocation.getArgument(1)));

        FilmForm hahaha1 = new FilmForm();
        hahaha1.setDirectorId(1);
        hahaha1.setDuration(100);
        hahaha1.setGenreId(1);
        hahaha1.setTitle("hahaha1");
        myFilmsServiceImpl.updateFilm(1L, hahaha1);
        Film newHahaha = filmMapper.convertFilmFormToFilm(hahaha1);
        newHahaha.setId(1);
        assertEquals(newHahaha, hihihi1);

        ServiceException serviceException = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.updateFilm(100, hahaha1));

        assertEquals("Impossible de mettre à jour le film", serviceException.getMessage());

        hahaha1.setDirectorId(100);
        ServiceException serviceException2 = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.updateFilm(1, hahaha1));

        assertEquals("Director doesn't exist", serviceException2.getMessage());
    }

    @Test
    void whenDeleteFilm_thenShouldDeleteFilm() throws ServiceException {
        doAnswer(invocation -> {
            mockJpaFilmDAODelete(invocation.getArgument(0));
            return null;
        }).when(jpaFilmDAO).delete(any(Film.class));

        when(jpaFilmDAO.findById(anyLong())).thenAnswer(invocation -> mockJpaFilmDAOFindById(invocation.getArgument(0)));

        when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> mockJpaDirectorDAOUpdate(invocation.getArgument(0), invocation.getArgument(1)));

        when(jpaFilmDAO.findByDirectorId(anyLong())).thenAnswer(invocation -> mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0)));

        System.out.println(jamesCameron.getFilmsProduced());
        jamesCameron.setFamous(true);
        assertTrue(jamesCameron.isFamous());
        myFilmsServiceImpl.deleteFilm(1L);
        System.out.println(jamesCameron.getFilmsProduced());
        assertNull(hihihi1);
        assertFalse(jamesCameron.isFamous());
        assertEquals(2, jamesCameron.getFilmsProduced().size());

        ServiceException serviceException = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.deleteFilm(100L));

        assertEquals("Le film demandé n'existe pas", serviceException.getMessage());
    }


    @Test
    void whenUpdateDirector_thenShouldHaveUpdatedDirector() throws ServiceException {
        when(jpaDirectorDAO.update(anyLong(), any(Director.class))).thenAnswer(invocation -> mockJpaDirectorDAOUpdate(invocation.getArgument(0), invocation.getArgument(1)));

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
    void whenCreateDirector_thenShouldCreateDirector() {
        when(jpaDirectorDAO.save(any(Director.class))).thenAnswer(invocation -> mockJpaDirectorDAOSave(invocation.getArgument(0)));

        DirectorForm directorForm = new DirectorForm();
        directorForm.setBirthdate(LocalDate.of(2000, 3, 20));
        directorForm.setName("director");
        directorForm.setSurname("form");

        DirectorDTO directorDTO = myFilmsServiceImpl.createDirector(directorForm);
        directorDTO.setId(3L);
        DirectorDTO directorExpected = new DirectorDTO();
        directorExpected.setBirthdate(LocalDate.of(2000, 3, 20));
        directorExpected.setFamous(false);
        directorExpected.setId(3L);
        directorExpected.setName("director");
        directorExpected.setSurname("form");
        assertEquals(DirectorMapper.convertDirectorDTOToDirector(directorExpected), DirectorMapper.convertDirectorDTOToDirector(directorDTO));
    }

    @Test
    void whenFindDirectorById_thenShouldHaveDirector() throws ServiceException {
        when(jpaDirectorDAO.findById(anyLong())).thenAnswer(invocation -> mockJpaDirectorDAOFindById(invocation.getArgument(0)));

        assertEquals(jamesCameron, myFilmsServiceImpl.findDirectorById(1L));
        ServiceException serviceException = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.findDirectorById(100L));
        assertEquals("Le réalisateur demandé n'existe pas", serviceException.getMessage());
    }

    @Test
    void whenFindDirectorBySurnameAndName_thenShouldHaveDirector() throws ServiceException {
        when(jpaDirectorDAO.findBySurnameAndName(anyString(), anyString())).thenAnswer(invocation -> mockJpaDirectorFindBySurnameAndName(invocation.getArgument(0), invocation.getArgument(1)));
        String JAMES = "James";
        String CAMERON = "Cameron";
        String PETER = "Peter";
        String JACKSON = "Jackson";
        String UNKNOWN = "unknown";
        assertEquals(jamesCameron, myFilmsServiceImpl.findDirectorBySurnameAndName(JAMES, CAMERON));
        assertEquals(peterJackson, myFilmsServiceImpl.findDirectorBySurnameAndName(PETER, JACKSON));
        ServiceException serviceException = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.findDirectorBySurnameAndName(UNKNOWN, UNKNOWN));
        assertEquals("Le réalisateur demandé n'existe pas", serviceException.getMessage());
    }

    @Test
    void whenDeleteDirector_thenShouldDeleteDirector() throws ServiceException {


        when(jpaFilmDAO.findByDirectorId(anyLong())).thenAnswer(invocation -> mockJpaFilmDAOFindByDirectorId(invocation.getArgument(0)));

        doAnswer(invocation -> {
            mockJpaDirectorDAODelete(invocation.getArgument(0));
            return null;
        }).when(jpaDirectorDAO).delete(anyLong());


        myFilmsServiceImpl.deleteDirector(1L);
        assertNull(jamesCameron);
    }

    @Test
    void whenFindAllGenres_thenShouldFindAllGenres() throws ServiceException {
        when(jpaGenreDAO.findAll()).thenAnswer(invocation -> mockJpaGenreDAOFindAll());

        List<Genre> allGenres = myFilmsServiceImpl.findAllGenres();
        assertEquals(genres, allGenres);
    }

    @Test
    void whenFindGenreById_thenShouldHaveGenre() throws ServiceException {
        when(jpaGenreDAO.findById(anyLong())).thenAnswer(invocation -> mockJpaGenreDAOFindById(invocation.getArgument(0)));

        Genre genre = myFilmsServiceImpl.findGenreById(1L);
        assertEquals(genres.get(0), genre);

        ServiceException serviceException = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.findGenreById(100L));
        assertEquals("Le genre demandé n'existe pas", serviceException.getMessage());
    }

    @Test
    void whenUpdateGenre_thenShouldUpdateGenre() throws ServiceException {
        when(jpaGenreDAO.update(anyLong(), anyString())).thenAnswer(invocation -> mockJpaGenreDAOUpdate(invocation.getArgument(0), invocation.getArgument(1)));

        Genre newAction = new Genre();
        newAction.setId(1);
        newAction.setName("truc");

        myFilmsServiceImpl.updateGenre(1L, "truc");
        assertEquals(newAction, genres.get(0));

        ServiceException serviceException = assertThrows(ServiceException.class, () -> myFilmsServiceImpl.updateGenre(100L, "truc"));

        assertEquals("Impossible de mettre à jour le genre", serviceException.getMessage());
    }

    @Test
    void whenCalculerDurationTotale_thenShouldHaveDurationTotale() {
        List<Double> pasDeNote = new ArrayList<>();
        List<Double> troisNotes = new ArrayList<>();
        troisNotes.add(11.0);
        troisNotes.add(19.5);
        troisNotes.add(14.5);
        List<Double> notesComplexes = new ArrayList<>(troisNotes);
        notesComplexes.add(7.123213);
        notesComplexes.add(1.123213);
        Double moyenne = 15.0;
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