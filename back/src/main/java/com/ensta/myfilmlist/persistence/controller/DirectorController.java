package com.ensta.myfilmlist.persistence.controller;

import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.DirectorForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Réalisateur")
@Tag(name = "Réalisateur", description = "Opération sur les réalisateurs")
public interface DirectorController {
    @ApiOperation(value = "Lister les réalisateurs", notes = "Permet de renvoyer la liste de tous les réalisateurs.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des réalisateurs a été renvoyée avec succès"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<List<DirectorDTO>> getAllDirectors() throws ControllerException;

    @ApiOperation(value = "Recherche un réalisateur par son identifiant", notes = "Permet de renvoyer les détails d'un réalisateur grâce à son identifiant.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le réalisateur demandé a été trouvé avec succès"),
            @ApiResponse(code = 404, message = "Le réalisateur demandé n'existe pas"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<DirectorDTO> getDirectorById(long id) throws ControllerException;

    @ApiOperation(value = "Recherche un réalisateur par son surname et présurname", notes = "Permet de renvoyer les détails d'un réalisateur grâce à son surname et présurname.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le réalisateur demandé a été trouvé avec succès"),
            @ApiResponse(code = 404, message = "Le réalisateur demandé n'existe pas"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<DirectorDTO> getDirectorByNameAndSurname(String name, String surname) throws ControllerException;

    @ApiOperation(value = "Ajouter un réalisateur", notes = "Permet d'ajouter un réalisateur.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le réalisateur a été ajouté avec succès"),
            @ApiResponse(code = 404, message = "Le réalisateur n'a pas pu être ajouté"),
            @ApiResponse(code = 409, message = "Le réalisateur existe déjà")
    })
    ResponseEntity<DirectorDTO> createDirector(DirectorForm directorForm);

    @ApiOperation(value = "Éditer un réalisateur", notes = "Permet d'éditer un réalisateur d'après un formulaire.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le réalisateur a été édité avec succès"),
            @ApiResponse(code = 404, message = "Le réalisateur n'a pas pu être édité"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<DirectorDTO> updateDirector(long id, DirectorForm directorForm) throws ControllerException;


    @ApiOperation(value = "Supprimer un réalisateur", notes = "Permet de supprimer un réalisateur d'après son identifiant.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Le réalisateur a été supprimé avec succès"),
            @ApiResponse(code = 404, message = "Le réalisateur n'a pas pu être supprimé"),
            @ApiResponse(code = 500, message = "Erreur interne lors de la requête")
    })
    ResponseEntity<?> deleteDirector(long id) throws ControllerException;

}
