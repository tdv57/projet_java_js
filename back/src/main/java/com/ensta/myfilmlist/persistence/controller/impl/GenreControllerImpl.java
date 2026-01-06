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
import java.util.Objects;

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
     * @return  list of existing Genre' DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("")
    public ResponseEntity<List<GenreDTO>> getAllGenres() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(GenreMapper.convertGenreToGenreDTOs(myFilmsService.findAllGenres()));
        } catch (ServiceException e) {
            throw new ControllerException("Impossible de trouver tous les films", e);
        }
    }

    /**
     * Returns a Genre's DTO based on its id.
     *
     * @param id    id of the genre to return
     * @return      the corresponding Genre
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable long id) throws ControllerException {
        try {
            GenreDTO genreDTO = GenreMapper.convertGenreToGenreDTO(myFilmsService.findGenreById(id));
            return ResponseEntity.status(HttpStatus.OK).body(genreDTO);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);        
        }
    }

    /**
     * Updates a Genre based on a form (user entry)
     *
     * @param id            id of the Genre to update
     * @param name          name updated
     * @return              the updated Genre's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable long id, @RequestBody String name) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.updateGenre(id, name));
        } catch (ServiceException e) {
            if (Objects.equals(e.getMessage(), "Genre introuvable")) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);     
            throw new ControllerException("Impossible d'éditer le genre demandé", e);
        }
    }
    
}
