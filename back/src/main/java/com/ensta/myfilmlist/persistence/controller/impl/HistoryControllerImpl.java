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
            throw new ControllerException("Can't get all Films from History", e);
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
    public ResponseEntity<HistoryDTO> addToWatchList(long userId, long filmId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(HistoryMapper.convertHistoryToHistoryDTO(myFilmsService.addFilmToWatchList(userId, filmId)));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
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
            throw new ControllerException("Can't delete Film from History", e);
        }
    }

    @Override
    @PutMapping("")
    //@PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<HistoryDTO> rateFilm(long userId, long filmId, int rating) throws ControllerException{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(HistoryMapper.convertHistoryToHistoryDTO(myFilmsService.rateFilm(userId, filmId, rating)));
        } catch (ServiceException e) {
            throw new ControllerException("Can't rate given Film", e);
        }
    }

    @Override
    @GetMapping("/rate/{userId}")
    public ResponseEntity<Optional<Integer>> getUserRating(@PathVariable long userId, long filmId) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body((myFilmsService.getUserRating(userId, filmId)));
        } catch (ServiceException e) {
            throw new ControllerException("Can't get Film's rating", e);
        }
    }

    @GetMapping("/rate")
    public ResponseEntity<Optional<Double>> getMeanRating(long filmId) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body((myFilmsService.getMeanRating(filmId)));
        } catch (ServiceException e) {
            throw new ControllerException("Can't get Film's rating", e);
        }
    }
}
