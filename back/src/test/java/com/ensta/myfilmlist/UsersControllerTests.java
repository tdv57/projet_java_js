package com.ensta.myfilmlist;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
public class UsersControllerTests {

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

    private List<User> mockMyFilmsServiceFindAllUsers() {
        return getAllUsers();
    }

    private User mockMyFilmsServiceFindUserById(long id) throws ServiceException {
        switch((int) id) {
            case 1:
                return getAxelRichard();
            case 2:
                return getBenoitBoero();
            case 3:
                return getElfieMolinaBonnefoy();
            case 4:
                return getFerdinandAlain();
            default:
                throw new ServiceException("User can't be found.");
        }
    }

    private User mockMyFilmsServiceFindUserBySurnameAndName(String surname, String name) throws ServiceException {
        if (surname.equals("Axel") && name.equals("Richard")) {return getAxelRichard();}
        else if (surname.equals("Benoit") && name.equals("Boero")) {return getBenoitBoero();}
        else if (surname.equals("Elfie") && name.equals("Molina--Bonnefoy")) {return getElfieMolinaBonnefoy();}
        else if (surname.equals("Ferdinand") && name.equals("Alain")) {return getFerdinandAlain();}
        else throw new ServiceException("User can't be found.");
    }

    private UserDTO mockMyFilmsServiceCreateUser(UserForm userForm) {
        User user = UserMapper.convertUserFormToUser(userForm);
        user.setId(userId++);
        return UserMapper.convertUserToUserDTO(user);
    }

    private UserDTO mockMyFilmsServiceUpdateUser(long id, UserForm userForm) throws ServiceException {
        switch((int) id) {
            case 1:
                User axelRichard = getAxelRichard();
                axelRichard.setName(userForm.getName());
                axelRichard.setSurname(userForm.getSurname());
                axelRichard.setPassword(userForm.getPassword());
                return UserMapper.convertUserToUserDTO(axelRichard);
            case 2:
                User benoitBoero = getBenoitBoero();
                benoitBoero.setName(userForm.getName());
                benoitBoero.setSurname(userForm.getSurname());
                benoitBoero.setPassword(userForm.getPassword());
                return UserMapper.convertUserToUserDTO(benoitBoero);
            case 3:
                User elfieMolinaBonnefoy = getElfieMolinaBonnefoy();
                elfieMolinaBonnefoy.setName(userForm.getName());
                elfieMolinaBonnefoy.setSurname(userForm.getSurname());
                elfieMolinaBonnefoy.setPassword(userForm.getPassword());
                return UserMapper.convertUserToUserDTO(elfieMolinaBonnefoy);
            case 4:
                User ferdinandAlain = getFerdinandAlain();
                ferdinandAlain.setName(userForm.getName());
                ferdinandAlain.setSurname(userForm.getSurname());
                ferdinandAlain.setPassword(userForm.getPassword());
                return UserMapper.convertUserToUserDTO(ferdinandAlain);
            default:
                throw new ServiceException("Can't update User.");
        }
    }

    private UserDTO mockMyFilmsServiceSetUserAsAdmin(long id) throws ServiceException {
        User user;
        switch((int) id) {
            case 1:
                user = getAxelRichard();
                break;
            case 2:
                user = getBenoitBoero();
                break;
            case 3:
                user = getElfieMolinaBonnefoy();
                break;
            case 4:
                user = getFerdinandAlain();
                break;
            default:
                throw new ServiceException("User can't be found.");
        }
        user.setRoles("USER, ADMIN");
        return UserMapper.convertUserToUserDTO(user);
    }
    
    @Test 
    void whenGetAllUsers_thenShouldHaveAllUsers() throws Exception{
        when(myFilmsService.findAllUsers()).thenAnswer(invocation -> {
            return mockMyFilmsServiceFindAllUsers();
        });

        mockMvc.perform(get("/user"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Richard"))
        .andExpect(jsonPath("$[0].surname").value("Axel"))
        .andExpect(jsonPath("$[0].password").value("axel"))
        .andExpect(jsonPath("$[0].roles").value("USER"))
        .andExpect(jsonPath("$[2].id").value(3))
        .andExpect(jsonPath("$[2].name").value("Molina--Bonnefoy"))
        .andExpect(jsonPath("$[2].surname").value("Elfie"))
        .andExpect(jsonPath("$[2].password").value("elfie"))
        .andExpect(jsonPath("$[2].roles").value("ADMIN"));
    }

    @Test
    void whenGetUserById_thenShouldHaveUser() throws Exception {
        when(myFilmsService.findUserById(anyLong())).thenAnswer(invocation -> {
            return mockMyFilmsServiceFindUserById(invocation.getArgument(0));
        });

        mockMvc.perform(get("/user/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Richard"))
        .andExpect(jsonPath("$.surname").value("Axel"))
        .andExpect(jsonPath("$.password").value("axel"))
        .andExpect(jsonPath("$.roles").value("USER"));

        mockMvc.perform(get("/user/100"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(""));
    }

    @Test 
    void whenGetUserByNameAndSurname_thenShouldHaveUser() throws Exception {
        when(myFilmsService.findUserBySurnameAndName(anyString(), anyString())).thenAnswer(invocation -> {
            return mockMyFilmsServiceFindUserBySurnameAndName(invocation.getArgument(0), invocation.getArgument(1));
        });

        mockMvc.perform(get("/user/")        
                .param("name", "Richard")
                .param("surname", "Axel"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Richard"))
        .andExpect(jsonPath("$.surname").value("Axel"))
        .andExpect(jsonPath("$.password").value("axel"))
        .andExpect(jsonPath("$.roles").value("USER"));

        mockMvc.perform(get("/user/?name=user&surname=user"))
        .andExpect(status().isNotFound());
    }

    @Test 
    void whenCreateUser_thenShouldCreateUser() throws Exception {
        when(myFilmsService.createUser(any(UserForm.class))).thenAnswer(invocation -> {
            return mockMyFilmsServiceCreateUser(invocation.getArgument(0));
        });

        String userJson =
            "{"
        + "\"name\": \"User\","
        + "\"surname\": \"User\","
        + "\"password\": \"user\""
        + "}";

        UserForm userForm = new UserForm();
        userForm.setName("User");
        userForm.setSurname("User");
        userForm.setPassword("user");
        User user = UserMapper.convertUserFormToUser(userForm);

        mockMvc.perform(post("/user/").content(userJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(5))
        .andExpect(jsonPath("$.name").value("User"))
        .andExpect(jsonPath("$.surname").value("User"))
        .andExpect(jsonPath("$.roles").value("USER"));
    }

    @Test 
    void whenUpdateUser_thenShouldHaveUpdatedUser() throws Exception{
        when(myFilmsService.updateUser(anyLong(), any(UserForm.class))).thenAnswer(invocation -> {
            return mockMyFilmsServiceUpdateUser(invocation.getArgument(0), invocation.getArgument(1));
        }); 

        String userJson =
            "{"
        + "\"name\": \"User\","
        + "\"surname\": \"User\","
        + "\"password\": \"user\""
        + "}";

        UserForm userForm = new UserForm();
        userForm.setName("User");
        userForm.setSurname("User");
        userForm.setPassword("user");
        User user = UserMapper.convertUserFormToUser(userForm);

        mockMvc.perform(put("/user/1").content(userJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("User"))
        .andExpect(jsonPath("$.surname").value("User"))
        .andExpect(jsonPath("$.roles").value("USER"));

        mockMvc.perform(put("/user/100").content(userJson).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }
    
    @Test
    void whenUpdateUserAsAdmin_thenShouldHaveAdminUser() throws Exception {
        when(myFilmsService.setUserAsAdmin(anyLong())).thenAnswer(invocation -> {
            return mockMyFilmsServiceSetUserAsAdmin(invocation.getArgument(0));
        });

        mockMvc.perform(put("/user/admin/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Richard"))
        .andExpect(jsonPath("$.surname").value("Axel"))
        .andExpect(jsonPath("$.password").value("axel"))
        .andExpect(jsonPath("$.roles").value("USER, ADMIN"));

        mockMvc.perform(put("/user/admin/100"))
        .andExpect(status().isNotFound());
    }

    @Test
    void whenDeleteUser_thenShouldDeleteUser() throws Exception {
        doNothing().when(myFilmsService).deleteUser(anyLong());

        mockMvc.perform(delete("/user/1"))
        .andExpect(status().isNoContent());

        mockMvc.perform(delete("/user/100"))
        .andExpect(status().isNoContent());
    }
}