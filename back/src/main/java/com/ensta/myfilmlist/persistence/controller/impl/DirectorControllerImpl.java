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

@RestController
@RequestMapping("/director")
@CrossOrigin
public class DirectorControllerImpl implements DirectorController {

    @Autowired
    private MyFilmsService myFilmsService;

    public DirectorControllerImpl(MyFilmsService myFilmsService) {
        this.myFilmsService = myFilmsService;
    }

    @Override
    @GetMapping("")
    public ResponseEntity<List<DirectorDTO>> getAllDirectors() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(DirectorMapper.convertDirectorToDirectorDTOs(myFilmsService.findAllDirectors()));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DirectorDTO> getDirectorById(@PathVariable long id) {
        try {
            DirectorDTO directorDTO = DirectorMapper.convertDirectorToDirectorDTO(myFilmsService.findDirectorById(id));
            return ResponseEntity.status(HttpStatus.OK).body(directorDTO);
        } catch (ServiceException e) {
            if (e.getMessage().equals("Internal Server Error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    @GetMapping("/names")
    public ResponseEntity<DirectorDTO> getDirectorByNameAndSurname(@RequestParam String surname, @RequestParam String name) {
        try {
            DirectorDTO directorDTO = DirectorMapper.convertDirectorToDirectorDTO(myFilmsService.findDirectorBySurnameAndName(surname, name));
            return ResponseEntity.status(HttpStatus.OK).body(directorDTO);
        } catch (ServiceException e) {
            if (e.getMessage().equals("Internal Server Error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorDTO> createDirector(@Valid @RequestBody DirectorForm directorForm) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.createDirector(directorForm));
        } catch (ServiceException e) {
            if (e.getMessage().equals("Internal Server Error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DirectorDTO> updateDirector(@PathVariable long id, @RequestBody DirectorForm directorForm) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateDirector(id, directorForm));
        } catch (ServiceException e) {
            if (e.getMessage().equals("Director doesn't exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else if (e.getMessage().equals("Director already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            } else if (e.getMessage().equals("Internal Server Error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            throw new ControllerException("Can't edit Director", e);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDirector(@PathVariable long id) throws ControllerException {
        try {
            myFilmsService.deleteDirector(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ServiceException e) {
            if (Objects.equals(e.getMessage(), "Director doesn't exist")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else if (e.getMessage().equals("Internal Server Error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            throw new ControllerException("Can't delete Director", e);
        }
    }
}
