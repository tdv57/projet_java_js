package com.ensta.myfilmlist.persistence.controller.impl;

import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.form.RealisateurForm;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.persistence.controller.RealisateurController;
import com.ensta.myfilmlist.service.MyFilmsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/realisateur")
@CrossOrigin
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
    @GetMapping("/")
    public ResponseEntity<RealisateurDTO> getRealisateurByNameAndSurname(@RequestParam String nom, @RequestParam String prenom) throws ControllerException {
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
    @PostMapping(path="/add", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RealisateurDTO> createRealisateur(@Valid @RequestBody RealisateurForm realisateurForm) throws ControllerException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(myFilmsService.createRealisateur(realisateurForm));
        } catch (ServiceException e) {
            throw new ControllerException("RealisateurControllerImpl::createRealisateur", e);
        }
    }
}
