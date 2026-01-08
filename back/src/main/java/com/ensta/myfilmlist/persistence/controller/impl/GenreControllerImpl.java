package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.GenreDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.mapper.GenreMapper;
import com.ensta.myfilmlist.persistence.controller.GenreController;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
@CrossOrigin
public class GenreControllerImpl implements GenreController {

    @Autowired
    private MyFilmsService myFilmsService;


    @Override
    @GetMapping("")
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(GenreMapper.convertGenreToGenreDTOs(myFilmsService.findAllGenres()));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable long id) {
        try {
            GenreDTO genreDTO = GenreMapper.convertGenreToGenreDTO(myFilmsService.findGenreById(id));
            return ResponseEntity.status(HttpStatus.OK).body(genreDTO);
        } catch (ServiceException e) {
            if (e.getMessage().equals("Internal Server Error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable long id, @RequestBody String name) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateGenre(id, name));
        } catch (ServiceException e) {
            if (e.getMessage().equals("Internal Server Error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
