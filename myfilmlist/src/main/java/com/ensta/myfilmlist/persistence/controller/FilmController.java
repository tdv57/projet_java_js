package com.ensta.myfilmlist.persistence.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.dto.*;
import com.ensta.myfilmlist.exception.*;

public interface FilmController {
    @GetMapping("/")
    public ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException;
}