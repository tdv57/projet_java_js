package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.persistence.controller.FilmController;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/film")
@CrossOrigin
public class FilmControllerImpl implements FilmController {

    @Autowired
    private MyFilmsService myFilmsService;


    @Override
    @GetMapping("")
    public ResponseEntity<List<FilmDTO>> getAllFilms() {
        return ResponseEntity.status(HttpStatus.OK).body(FilmMapper.convertFilmToFilmDTOs(myFilmsService.findAll()));
    }

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

    @Override
    @PostMapping("")
    public ResponseEntity<FilmDTO> createFilm(@Valid @RequestBody FilmForm filmForm) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.createFilm(filmForm));
        } catch (ServiceException e) {
            if (Objects.equals(e.getMessage(), "Film already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<FilmDTO> updateFilm(@PathVariable long id, @RequestBody FilmForm filmForm) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateFilm(id, filmForm));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

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