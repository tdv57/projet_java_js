package com.ensta.myfilmlist.persistence.controller;


import com.ensta.myfilmlist.dto.UserDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.UserForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api(tags = "Utilisateur")
@Tag(name = "Utilisateur", description = "Opération sur les utilisateurs.")
public interface UserController {

    @ApiOperation(value = "Lister les utilisateurs", notes = "Permet de renvoyer la liste de tous les utilisateurs.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des utilisateurs a été renvoyée correctement")
    })
    ResponseEntity<List<UserDTO>> getAllUsers() throws ControllerException;

    @ApiOperation(value = "Recherche un utilisateur par son identifiant", notes = "Permet de renvoyer les détails d'un utilisateur grâce à son identifiant.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur demandé a été trouvé"),
            @ApiResponse(code = 404, message = "L'utilisateur demandé n'existe pas")
    })
    ResponseEntity<UserDTO> getUserById(long id) throws ControllerException;

    @ApiOperation(value = "Recherche un utilisateur par son nom et prénom", notes = "Permet de renvoyer les détails d'un utilisateur grâce à son nom et prénom.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur demandé a été trouvé avec succès"),
            @ApiResponse(code = 404, message = "L'utilisateur demandé n'existe pas")
    })
    ResponseEntity<UserDTO> getUserByUsername(String username) throws ControllerException;

    @ApiOperation(value = "Ajouter un utilisateur", notes = "Permet d'ajouter un utilisateur.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été ajouté avec succès"),
            @ApiResponse(code = 404, message = "L'utilisateur n'a pas pu être ajouté")
    })
    ResponseEntity<UserDTO> createUser(UserForm directorForm) throws ControllerException;

    @ApiOperation(value = "Éditer un utilisateur", notes = "Permet d'éditer un utilisateur d'après un formulaire.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été édité avec succès"),
            @ApiResponse(code = 404, message = "L'utilisateur n'a pas pu être édité")
    })
    ResponseEntity<UserDTO> updateUser(long id, UserForm directorForm) throws ControllerException;

    @ApiOperation(value = "Changer un utilisateur en admin", notes = "Permet de donner les rôles d'administrateurs à un utilisateur.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a été édité avec succès"),
            @ApiResponse(code = 404, message = "L'utilisateur n'a pas pu être édité")
    })
    ResponseEntity<UserDTO> updateUserAsAdmin(@PathVariable long id) throws ControllerException;

    @ApiOperation(value = "Supprimer un utilisateur", notes = "Permet de supprimer un utilisateur d'après son identifiant.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "L'utilisateur a été supprimé avec succès"),
            @ApiResponse(code = 404, message = "L'utilisateur n'a pas pu être supprimé")
    })
    ResponseEntity<?> deleteUser(long id) throws ControllerException;
}

