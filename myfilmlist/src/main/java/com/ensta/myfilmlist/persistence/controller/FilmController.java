package com.ensta.myfilmlist.persistence.controller;

import java.util.List;

import com.ensta.myfilmlist.form.FilmForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.dto.*;
import com.ensta.myfilmlist.exception.*;

public interface FilmController {

     @GetMapping("")
     ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException;
     ResponseEntity<FilmDTO> getFilmById(Long id) throws ControllerException;
     ResponseEntity<FilmDTO> createFilm(FilmForm filmForm) throws ControllerException;

}