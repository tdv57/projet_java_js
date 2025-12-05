package com.ensta.myfilmlist.persistence.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.dto.*;
import com.ensta.myfilmlist.exception.*;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.persistence.controller.FilmController;

@RestController
@RequestMapping("/film")
public class FilmControllerImpl implements FilmController {

    @Autowired
    private MyFilmsService myFilmsService;


    @Override
    public ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(FilmMapper.convertFilmToFilmDTOs(myFilmsService.findAll()));
        } catch (ServiceException e) {
            throw new ControllerException("FilmControllerImpl::getAllFilms", e);
        }
    }
}