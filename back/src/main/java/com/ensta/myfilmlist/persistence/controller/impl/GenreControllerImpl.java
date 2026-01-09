package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.GenreDTO;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.mapper.GenreMapper;
import com.ensta.myfilmlist.persistence.controller.GenreController;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Api routes for Genre
 */
@RestController
@RequestMapping("/genre")
@CrossOrigin
public class GenreControllerImpl implements GenreController {

    @Autowired
    private MyFilmsService myFilmsService;

    /**
     * Returns the list of all genres registered in the database.
     *
     * @return list of existing Genre' DTO
     */
    @Override
    @GetMapping("")
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(GenreMapper.convertGenreToGenreDTOs(myFilmsService.findAllGenres()));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Returns a Genre's DTO based on its id.
     *
     * @param id id of the genre to return
     * @return the corresponding Genre
     */
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

    /**
     * Updates a Genre based on a form (user entry)
     *
     * @param id   id of the Genre to update
     * @param name name updated
     * @return the updated Genre's DTO
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable long id, @RequestBody String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateGenre(id, name));
        } catch (ServiceException e) {
            if (e.getMessage().equals("Can't find previous Genre")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else if (e.getMessage().equals("Genre already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
