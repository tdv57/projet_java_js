package com.ensta.myfilmlist.persistence.controller;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.FilmForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Film")
@Tag(name = "Film", description = "Opération sur les films")
public interface FilmController {

    @ApiOperation(value = "Lister les films", notes = "Permet de renvoyer la liste de tous les films.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des films a été renvoyée correctement")
    })
    ResponseEntity<List<FilmDTO>> getAllFilms();

    @ApiOperation(value = "Rechercher un film par son identifiant", notes = "Permet de renvoyer les détails d'un film grâce à son identifiant.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le film demandé a été trouvé"),
            @ApiResponse(code = 404, message = "Le film demandé n'existe pas")
    })
    ResponseEntity<FilmDTO> getFilmById(long id);

    @ApiOperation(value = "Rechercher un film par son title", notes = "Permet de renvoyer les détails d'un film grâce à son title.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le film demandé a été trouvé"),
            @ApiResponse(code = 404, message = "Le film demandé n'existe pas")
    })
    ResponseEntity<FilmDTO> getFilmByTitle(String title);

    @ApiOperation(value = "Rechercher des films par leur réalisateur", notes = "Permet de renvoyer les détails des films grâce à l'identifiant de leur réalisateur.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le(s) film(s) demandé(s) a(ont) été trouvé"),
            @ApiResponse(code = 404, message = "Le réalisateur n'existe pas")
    })
    ResponseEntity<List<FilmDTO>> getFilmByDirectorId(@PathVariable long id);

    @ApiOperation(value = "Ajouter un film", notes = "Permet d'ajouter un film d'après un formulaire.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le film a bien été ajouté"),
            @ApiResponse(code = 404, message = "Le réalisateur du film n'existe pas"),
            @ApiResponse(code = 409, message = "Le film existe déjà")
    })
    ResponseEntity<FilmDTO> createFilm(FilmForm filmForm);

    @ApiOperation(value = "Éditer un film", notes = "Permet d'éditer un film d'après un formulaire.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le film a bien été édité"),
            @ApiResponse(code = 404, message = "Le film ou le réalisateur n'existe pas"),
    })
    ResponseEntity<FilmDTO> updateFilm(long id, FilmForm filmForm);

    @ApiOperation(value = "Supprimer un film", notes = "Permet de supprimer un film d'après son identifiant.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Le film a bien été supprimé"),
            @ApiResponse(code = 404, message = "Le film n'existe pas"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la mise à jour du réalisateur")
    })
    ResponseEntity<?> deleteFilm(long id) throws ControllerException;
}