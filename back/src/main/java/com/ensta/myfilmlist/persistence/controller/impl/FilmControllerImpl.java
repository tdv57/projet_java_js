package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.persistence.controller.FilmController;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.dto.*;
import com.ensta.myfilmlist.exception.*;
import com.ensta.myfilmlist.mapper.FilmMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * Api routes for Film
 */
@RestController
@RequestMapping("/film")
@CrossOrigin
public class FilmControllerImpl implements FilmController {

    @Autowired
    private MyFilmsService myFilmsService;

    /**
     * Returns the list of all films registered in the database.
     *
     * @return  list of existing Films' DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("")
    public ResponseEntity<List<FilmDTO>> getAllFilms() {
        return ResponseEntity.status(HttpStatus.OK).body(FilmMapper.convertFilmToFilmDTOs(myFilmsService.findAll()));
    }

    /**
     * Returns a Film's DTO based on its id.
     *
     * @param id    id of the film to return
     * @return      the corresponding Film
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable long id) {
        try {
            FilmDTO filmDTO = FilmMapper.convertFilmToFilmDTO(myFilmsService.findFilmById(id));
            return ResponseEntity.status(HttpStatus.OK).body(filmDTO);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Returns a Film's DTO based on its title
     *
     * @param title     title of the Film in the database
     * @return          the corresponding Film's DTO
     */
    @Override
    @GetMapping("/title")
    public ResponseEntity<FilmDTO> getFilmByTitle(@RequestParam String title) {
        try {
            FilmDTO filmDTO = FilmMapper.convertFilmToFilmDTO(myFilmsService.findFilmByTitle(title));
            return ResponseEntity.status(HttpStatus.OK).body(filmDTO);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Returns a list of Film's DTO based on the id of a Director
     *
     * @param id    id of the Director in the database
     * @return      the corresponding list of films' DTO
     */
    @Override
    @GetMapping("/director/{id}")
    public ResponseEntity<List<FilmDTO>> getFilmByDirectorId(@PathVariable long id) {
        try {
            List<Film> filmList = myFilmsService.findFilmByDirectorId(id);
            List<FilmDTO> returnList = FilmMapper.convertFilmToFilmDTOs(filmList);
            return ResponseEntity.status(HttpStatus.OK).body(returnList);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Adds a Film into the database and returns the corresponding Film's DTO.
     * The Film is created based on a form (user entry).
     *
     * @param filmForm      form from which the Film is created
     * @return              the corresponding Film's DTO
     */
    @Override
    @PostMapping("")
    public ResponseEntity<FilmDTO> createFilm(@Valid @RequestBody FilmForm filmForm) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.createFilm(filmForm));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Updates a Film based on a form (user entry)
     *
     * @param id            id of the Film to update
     * @param filmForm      form with value updated
     * @return              the updated Film's DTO
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FilmDTO> updateFilm(@PathVariable long id, @RequestBody FilmForm filmForm) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateFilm(id, filmForm));
        } catch (ServiceException e) {
            if (Objects.equals(e.getMessage(), "Réalisateur inexistant")) throw new ControllerException(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Deletes a Film based on its id.
     *
     * @param id    id of the Film to delete
     * @return      no content
     * @throws ControllerException  in case of any error
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable long id) {
        try {
            myFilmsService.deleteFilm(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ServiceException e) {
            if (Objects.equals(e.getMessage(), "Erreur lors de la mise à jour de la célébrité")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}