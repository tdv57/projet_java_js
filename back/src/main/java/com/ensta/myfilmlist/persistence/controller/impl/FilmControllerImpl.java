package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.form.FilmForm;
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

@RestController
@RequestMapping("/film")
@CrossOrigin
public class FilmControllerImpl implements FilmController {

    @Autowired
    private MyFilmsService myFilmsService;


    @Override
    @GetMapping("")
    public ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(FilmMapper.convertFilmToFilmDTOs(myFilmsService.findAll()));
        } catch (ServiceException e) {
            throw new ControllerException("Impossible de trouver tous les films", e);
        }
    }


    @Override
    @PostMapping("/")
    public ResponseEntity<FilmDTO> getFilmByTitle(@RequestParam String titre) throws ControllerException {
        try {
            FilmDTO filmDTO = FilmMapper.convertFilmToFilmDTO(myFilmsService.findFilmByTitle(titre));
            if (filmDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(filmDTO);
        } catch (ServiceException e) {
            throw new ControllerException("Impossible de trouver le film demandé", e);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable Long id) throws ControllerException {
        try {
            FilmDTO filmDTO = FilmMapper.convertFilmToFilmDTO(myFilmsService.findFilmById(id));
            if (filmDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(filmDTO);
        } catch (ServiceException e) {
            throw new ControllerException("Impossible de trouver le film demandé", e);
        }
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<FilmDTO> createFilm(@Valid @RequestBody FilmForm filmForm) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.createFilm(filmForm));
        } catch (ServiceException e) {
            throw new ControllerException("Impossible d'ajouter le film demandé", e);
        }
    }

    @Override
    @PutMapping("/film/{id}")
    public ResponseEntity<FilmDTO> updateFilm(@PathVariable Long id, @RequestBody FilmForm filmForm) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateFilm(id, filmForm));
        } catch (ServiceException e) {
            throw new ControllerException("Impossible d'éditer le film demandé", e);
        }
    }


    @Override
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteFilm(@PathVariable Long id) throws ControllerException {
        try {
            myFilmsService.deleteFilm(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ServiceException e) {
        throw new ControllerException("Impossible de supprimer le film demandé", e);
        }
    }
}