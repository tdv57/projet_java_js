package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.persistence.controller.RealisateurController;
import com.ensta.myfilmlist.service.MyFilmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/realisateur")
public class RealisateurControllerImpl implements RealisateurController {

    @Autowired
    private MyFilmsService myFilmsService;

    @Override
    @GetMapping("")
    public ResponseEntity<List<RealisateurDTO>> getAllRealisateurs() throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(RealisateurMapper.convertRealisateurToRealidateurDTOs(myFilmsService.findAllRealisateurs()));
        } catch (ServiceException e) {
            throw new ControllerException("RealisateurControllerImpl::getAllRealisateurs", e);
        }
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RealisateurDTO> getRealisateurById(@PathVariable Long id) throws ControllerException {
        try {
            RealisateurDTO realisateurDTO = RealisateurMapper.convertRealisateurToRealisateurDTO(myFilmsService.findRealisateurById(id));
            if (realisateurDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(realisateurDTO);
        } catch (ServiceException e) {
            throw new ControllerException("RealisateurControllerImpl::getRealisateurById", e);
        }
    }

    @Override
    @GetMapping("/{nom}&{prenom}")
    public ResponseEntity<RealisateurDTO> getRealisateurByNameAndSurname(@PathVariable String nom, @PathVariable String prenom) throws ControllerException {
        try {
            RealisateurDTO realisateurDTO = RealisateurMapper.convertRealisateurToRealisateurDTO(myFilmsService.findRealisateurByNomAndPrenom(nom, prenom));
            if (realisateurDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(realisateurDTO);
        } catch (ServiceException e) {
            throw new ControllerException("RealisateurControllerImpl::getRealisateurByNameAndSurname", e);
        }
    }


    @Override
    @PostMapping("/add")
    public ResponseEntity<RealisateurDTO> createRealisateur(@Valid @RequestBody Realisateur realisateur) throws ControllerException {
        //TODO: add a RealisateurFrom as for film
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.createRealisateur(realisateur));
        } catch (ServiceException e) {
            throw new ControllerException("RealisateurControllerImpl::createRealisateur", e);
        }
    }
}
