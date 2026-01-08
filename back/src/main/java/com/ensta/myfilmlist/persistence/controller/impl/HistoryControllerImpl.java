package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.HistoryDTO;
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
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/history")
@CrossOrigin
public class HistoryControllerImpl implements HistoryController {
    @Autowired
    private MyFilmsService myFilmsService;


    Optional<Integer> checkRating(int rating) {
        if (rating < 0) {
            return Optional.empty();
        }
        return Optional.of(rating);
    }

    /**
     * Returns the list of all films watched by a user in the database.
     *
     * @param userId    id of the user to collect watched films
     * @return          list of watched Film's DTO
     */
    @Override
    @GetMapping("/{userId}")
    //@PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<List<FilmDTO>> getWatchList(@PathVariable long userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(FilmMapper.convertFilmToFilmDTOs(myFilmsService.findWatchList(userId)));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
            if (e.getMessage().equals("Internal Server Error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @Override
    @DeleteMapping("")
    //@PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<?> removeFromWatchList(long userId, long filmId) {
        try {
            myFilmsService.removeFilmFromWatchList(userId, filmId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @PutMapping("")
    //@PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<HistoryDTO> rateFilm(long userId, long filmId, int rating) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(HistoryMapper.convertHistoryToHistoryDTO(myFilmsService.rateFilm(userId, filmId, rating)));
        } catch (ServiceException e) {
            if (Objects.equals(e.getMessage(), "Film's rating should be positive")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }
    }

    @Override
    @GetMapping("/rate/{userId}")
    public ResponseEntity<Optional<Integer>> getUserRating(@PathVariable long userId, long filmId) {
        try {
            Integer rating = myFilmsService.getUserRating(userId, filmId);
            return ResponseEntity.status(HttpStatus.OK).body(checkRating(rating));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/rate")
    public ResponseEntity<Optional<Double>> getMeanRating(long filmId) {
        return ResponseEntity.status(HttpStatus.OK).body((myFilmsService.getMeanRating(filmId)));
    }
}
