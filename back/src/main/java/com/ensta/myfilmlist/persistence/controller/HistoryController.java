package com.ensta.myfilmlist.persistence.controller;


import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.HistoryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Api(tags = "History")
@Tag(name = "History", description = "Opération sur les listes de films des favoris")
public interface HistoryController {

    @ApiOperation(value = "Lister les films vus par un utilisateur", notes = "Permet de renvoyer la liste de tous les films visionné par un utilisateur.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des films a été renvoyée avec succès"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<List<FilmDTO>> getWatchList(long id);

    @ApiOperation(value = "Ajouter un film à la liste d'un utilisateur", notes = "Permet d'ajouter un film à la liste de tous les films visionné par un utilisateur.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des films a été mise à jour avec succès"),
            @ApiResponse(code = 409, message = "Le film est déjà dans la liste de favoris"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<HistoryDTO> addToWatchList(long userId, long filmId);

    @ApiOperation(value = "Supprimer un film à la liste d'un utilisateur", notes = "Permet de supprimer un film de la liste de tous les films visionné par un utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "La liste des films a été mise à jour avec succès"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<?> removeFromWatchList(long userId, long filmId);

    @ApiOperation(value = "Noter un film", notes = "Permet à un utilisateur de donner une note à un film de sa liste de visionnage.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La note a des films a été mise à jour avec succès"),
            @ApiResponse(code = 400, message = "La note du film doit être positive"),
            @ApiResponse(code = 404, message = "Le film doit être dans la liste de favoris"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<HistoryDTO> rateFilm(long userId, long filmId, int rating);

    @ApiOperation(value = "Voir la note d'un film", notes = "Permet de voir la note moyenne d'un film.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La note moyenne du film a été renvoyée avec succès"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<Optional<Integer>> getUserRating(long userId, long filmId);

    @ApiOperation(value = "Voir la note d'un film", notes = "Permet de voir la note donner par un utilisateur à un film.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La note moyenne du film a été renvoyée avec succès"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<Optional<Double>> getMeanRating(long filmId);
}
