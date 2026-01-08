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

    @Override
    @GetMapping("/{userId}")
    public ResponseEntity<List<FilmDTO>> getWatchList(@PathVariable long userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(FilmMapper.convertFilmToFilmDTOs(myFilmsService.findWatchList(userId)));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @PostMapping("")
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
