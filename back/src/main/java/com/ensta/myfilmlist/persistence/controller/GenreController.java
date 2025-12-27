package com.ensta.myfilmlist.persistence.controller;

import com.ensta.myfilmlist.dto.GenreDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Genre")
@Tag(name = "Genre", description = "Opération sur les genres")
public interface GenreController {

    @ApiOperation(value = "Lister les genres", notes = "Permet de renvoyer la liste de tous les genres.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des genres a été renvoyée correctement")
    })
    ResponseEntity<List<GenreDTO>> getAllGenres() throws ControllerException;

    @ApiOperation(value = "Rechercher un genre par son identifiant", notes = "Permet de renvoyer les détails d'un genre grâce à son identifiant.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le genre demandé a été trouvé"),
            @ApiResponse(code = 404, message = "Le genre demandé n'existe pas")
    })
    ResponseEntity<GenreDTO> getGenreById(long id) throws ControllerException;

    @ApiOperation(value = "Éditer un genre", notes = "Permet d'éditer le surname d'un genre.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le genre a bien été édité"),
            @ApiResponse(code = 404, message = "Le genre n'a pas pu être édité")
    })
    ResponseEntity<GenreDTO> updateGenre(long id, String surname) throws ControllerException;
}