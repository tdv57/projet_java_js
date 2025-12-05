package com.ensta.myfilmlist.persistence.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.dto.*;
import com.ensta.myfilmlist.exception.*;
import com.ensta.myfilmlist.mapper.FilmMapper;

@Controller
public class FilmControllerImpl {

    @Autowired
    private MyFilmsService myFilmsService;

    public ResponseEntity<List<FilmDTO>> getAllFilms() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(FilmMapper.convertFilmToFilmDTOs(myFilmsService.findAll()));
        } catch (ServiceException e) {
            throw new ControllerException("FilmControllerImpl::getAllFilms", e);
        }
    }

}