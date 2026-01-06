package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.HistoryDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.HistoryMapper;
import com.ensta.myfilmlist.persistence.controller.HistoryController;
import com.ensta.myfilmlist.service.MyFilmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/history")
@CrossOrigin
public class HistoryControllerImpl implements HistoryController {
    @Autowired
    private MyFilmsService myFilmsService;

    /**
     * Returns the list of all films watched by a user in the database.
     *
     * @param userId    id of the user to collect watched films
     * @return          list of watched Film's DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @GetMapping("/{userId}")
    //@PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<FilmDTO>> getWatchList(@PathVariable long userId) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(FilmMapper.convertFilmToFilmDTOs(myFilmsService.findWatchList(userId)));
        } catch (ServiceException e) {
            throw new ControllerException("Impossible de trouver tous les films de l'historique", e);
        }
    }

    /**
     * Returns the list of all directors registered in database.
     *
     * @return  list of existing Directors' DTO
     * @throws ControllerException  in case of any error
     */
    @Override
    @PostMapping("")
    //@PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<HistoryDTO> addToWatchList(long userId, long filmId) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(HistoryMapper.convertHistoryToHistoryDTO(myFilmsService.addFilmToWatchList(userId, filmId)));
        } catch (ServiceException e) {
            throw new ControllerException("Impossible d'ajouter un film à l'historique", e);
        }
    }

    @Override
    @DeleteMapping("")
    //@PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<?> removeFromWatchList(long userId, long filmId) throws ControllerException {
        try {
            myFilmsService.removeFilmFromWatchList(userId, filmId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ServiceException e) {
            throw new ControllerException("Impossible de supprimer le film de l'historique", e);
        }
    }

    @Override
    @PutMapping("")
    //@PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<HistoryDTO> rateFilm(long userId, long filmId, int rating) throws ControllerException{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(HistoryMapper.convertHistoryToHistoryDTO(myFilmsService.rateFilm(userId, filmId, rating)));
        } catch (ServiceException e) {
            throw new ControllerException("Impossible de noter ce film", e);
        }
    }

    @Override
    @GetMapping("/rate")
    public ResponseEntity<Optional<Integer>> getRate(long userId, long filmId) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body((myFilmsService.getNote(userId, filmId)));
        } catch (ServiceException e) {
            throw new ControllerException("Impossible de trouver la note de ce film", e);
        }
    }
}
