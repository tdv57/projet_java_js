package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.UserDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.UserForm;
import com.ensta.myfilmlist.mapper.UserMapper;
import com.ensta.myfilmlist.persistence.controller.UserController;
import com.ensta.myfilmlist.service.MyFilmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Api routes for a User
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserControllerImpl implements UserController {

    @Autowired
    private MyFilmsService myFilmsService;

    public UserControllerImpl(MyFilmsService myFilmsService) {
        this.myFilmsService = myFilmsService;
    }

    /**
     * Returns the list of all users registered in database.
     *
     * @return  list of existing Users' DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(UserMapper.convertUsersToUserDTOs(myFilmsService.findAllUsers()));
        } catch (ServiceException e) {
            throw new ControllerException("Can't get Users.", e);
        }
    }

    /**
     * Returns a User's DTO based on its id.
     *
     * @param id    id of the User to return
     * @return      the corresponding User's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) throws ControllerException {
        try {
            UserDTO userDTO = UserMapper.convertUserToUserDTO(myFilmsService.findUserById(id));
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } catch (ServiceException e) {
            if (e.getMessage().equals("User can't be found.")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            throw new ControllerException("Can't get User.", e);
        }
    }

    /**
     * Returns a User's DTO based on its name and surname
     *
     * @param surname   surname of the User in the database
     * @param name      name of the User in the database
     * @return          the corresponding User's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("/")
    public ResponseEntity<UserDTO> getUserByNameAndSurname(@RequestParam String name, @RequestParam String surname) throws ControllerException {
        try {
            UserDTO userDTO = UserMapper.convertUserToUserDTO(myFilmsService.findUserBySurnameAndName(surname, name));
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } catch (ServiceException e) {
            if (e.getMessage().equals("User can't be found.")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            throw new ControllerException("Can't get User.", e);
        }
    }

    /**
     * Adds a User into the database and returns the corresponding User's DTO.
     * The User is created based on a form (user entry).
     *
     * @param userForm  form from which the User is created
     * @return              the corresponding User's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @PostMapping(path="/", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserForm userForm) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.createUser(userForm));
        } catch (ServiceException e) {
            throw new ControllerException("Can't create User.", e);
        }
    }

    /**
     * Updates a User based on a form (user entry)
     *
     * @param id            id of the User to set as admin
     * @return              the updated User's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long id, @RequestBody UserForm userForm) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateUser(id, userForm));
        } catch (ServiceException e) {
            throw new ControllerException("Can't update User.", e);
        }
    }

    /**
     * Updates a User based on a form (user entry)
     *
     * @param id            id of the User to set as admin
     * @return              the updated User's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @PutMapping("/admin/{id}")
    public ResponseEntity<UserDTO> updateUserAsAdmin(@PathVariable long id) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.setUserAsAdmin(id));
        } catch (ServiceException e) {
            throw new ControllerException("Can't update User.", e);
        }
    }

    /**
     * Deletes a User based on its id.
     *
     * @param id    id of the User to delete
     * @return      no content
     * @throws ControllerException  in case of any error
     */
    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id || hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable long id) throws ControllerException {
        try {
            myFilmsService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ServiceException e) {
            throw new ControllerException("Can't delete User.", e);
        }
    }
}
