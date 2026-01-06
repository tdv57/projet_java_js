package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.mapper.DirectorMapper;
import com.ensta.myfilmlist.persistence.controller.DirectorController;
import com.ensta.myfilmlist.service.MyFilmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * Api routes for a Director
 */
@RestController
@RequestMapping("/director")
@CrossOrigin
public class DirectorControllerImpl implements DirectorController {

    @Autowired
    private MyFilmsService myFilmsService;

    public DirectorControllerImpl(MyFilmsService myFilmsService) {
        this.myFilmsService = myFilmsService;
    }

    /**
     * Returns the list of all directors registered in database.
     *
     * @return  list of existing Directors' DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("")
    public ResponseEntity<List<DirectorDTO>> getAllDirectors() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(DirectorMapper.convertDirectorToDirectorDTOs(myFilmsService.findAllDirectors()));
        } catch (ServiceException e) {
            throw new ControllerException("Can't get Directors.", e);
        }
    }

    /**
     * Returns a Director's DTO based on its id.
     *
     * @param id    id of the Director to return
     * @return      the corresponding Director's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DirectorDTO> getDirectorById(@PathVariable long id) throws ControllerException {
        try {
            DirectorDTO directorDTO = DirectorMapper.convertDirectorToDirectorDTO(myFilmsService.findDirectorById(id));
            return ResponseEntity.status(HttpStatus.OK).body(directorDTO);
        } catch (ServiceException e) {
            throw new ControllerException("Can't get Director.", e);
        }
    }

    /**
     * Returns a Director's DTO based on its name and surname
     *
     * @param surname   surname of the Director in the database
     * @param name      name of the Director in the database
     * @return          the corresponding Director's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("/names")
    public ResponseEntity<DirectorDTO> getDirectorByNameAndSurname(@RequestParam String surname, @RequestParam String name) throws ControllerException {
        try {
            DirectorDTO directorDTO = DirectorMapper.convertDirectorToDirectorDTO(myFilmsService.findDirectorByNameAndSurname(name, surname));
            return ResponseEntity.status(HttpStatus.OK).body(directorDTO);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Adds a Director into the database and returns the corresponding Director's DTO.
     * The Director is created based on a form (user entry).
     *
     * @param directorForm  form from which the Director is created
     * @return              the corresponding Director's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @PostMapping(path="", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorDTO> createDirector(@Valid @RequestBody DirectorForm directorForm) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.createDirector(directorForm));
        } catch (ServiceException e) {
            throw new ControllerException("Can't create Director.", e);
        }
    }

    /**
     * Updates a Director based on a form (user entry)
     *
     * @param id            id of the Director to update
     * @param directorForm  form with value updated
     * @return              the updated Director's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DirectorDTO> updateDirector(@PathVariable long id, @RequestBody DirectorForm directorForm) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateDirector(id, directorForm));
        } catch (ServiceException e) {
            if (Objects.equals(e.getMessage(), "Can't get Director")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            throw new ControllerException("Can't edit Director", e);
        }
    }

    /**
     * Deletes a Director based on its id.
     *
     * @param id    id of the Director to delete
     * @return      no content
     * @throws ControllerException  in case of any error
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDirector(@PathVariable long id) throws ControllerException {
        try {
            myFilmsService.deleteDirector(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ServiceException e) {
            if (Objects.equals(e.getMessage(), "Can't get Director")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            throw new ControllerException("Can't delete Director.", e);
        }
    }
}
