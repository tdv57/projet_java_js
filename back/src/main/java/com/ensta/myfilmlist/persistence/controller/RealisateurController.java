package com.ensta.myfilmlist.persistence.controller;

import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.model.Realisateur;
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
public interface RealisateurController {
    @ApiOperation(value = "Lister les réalisateurs", notes = "Permet de renvoyer la liste de tous les réalisateurs.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des réalisateurs a été renvoyée correctement")
    })
    ResponseEntity<List<RealisateurDTO>> getAllRealisateurs() throws ControllerException;

    @ApiOperation(value = "Recherche un réalisateur", notes = "Permet de renvoyer les détails d'un réalisateur grâce à son identifiant.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le réalisateur demandé a été trouvé"),
            @ApiResponse(code = 404, message = "Le réalisateur demandé n'existe pas")
    })
    ResponseEntity<RealisateurDTO> getRealisateurById(Long id) throws ControllerException;

    @ApiOperation(value = "Recherche un réalisateur", notes = "Permet de renvoyer les détails d'un réalisateur grâce à son nom et prénom.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le réalisateur demandé a été trouvé"),
            @ApiResponse(code = 404, message = "Le réalisateur demandé n'existe pas")
    })
    ResponseEntity<RealisateurDTO> getRealisateurByNameAndSurname(String name, String surname) throws ControllerException;

    @ApiOperation(value = "Ajouter un réalisateur", notes = "Permet d'ajouter un réalisateur.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le réalisateur a bien été ajouté"),
            @ApiResponse(code = 404, message = "Le réalisateur n'a pas pu être ajouté")
    })
    ResponseEntity<RealisateurDTO> createRealisateur(Realisateur realisateur) throws ControllerException;

}
