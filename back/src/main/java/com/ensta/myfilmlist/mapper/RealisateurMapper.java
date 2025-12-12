package com.ensta.myfilmlist.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.form.RealisateurForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;


public class RealisateurMapper {
    public static List<RealisateurDTO> convertRealisateurToRealidateurDTOs(List<Realisateur> realisateurs) {
        return realisateurs.stream()
                            .map(RealisateurMapper::convertRealisateurToRealisateurDTO)
                            .collect(Collectors.toList());
    }

    public static RealisateurDTO convertRealisateurToRealisateurDTO(Realisateur realisateur) {
        if (realisateur == null) return null;
        RealisateurDTO realisateurDTO = new RealisateurDTO();
        realisateurDTO.setCelebre(realisateur.isCelebre());
        realisateurDTO.setDateNaissance(realisateur.getDateNaissance());
        realisateurDTO.setId(realisateur.getId());
        realisateurDTO.setNom(realisateur.getNom());
        realisateurDTO.setPrenom(realisateur.getPrenom());
        return realisateurDTO;
    }

    public static Realisateur convertRealisateurDTOToRealisateur(RealisateurDTO realisateurDTO) {
        if (realisateurDTO == null) return null;
        Realisateur realisateur = new Realisateur();
        realisateur.setCelebre(realisateurDTO.isCelebre());
        realisateur.setDateNaissance(realisateurDTO.getDateNaissance());
        realisateur.setId(realisateurDTO.getId());
        realisateur.setNom(realisateurDTO.getNom());
        realisateur.setPrenom(realisateurDTO.getPrenom());
        return realisateur;
    }


    /**
     * Convertit un formulaire de réalisateur en film.
     *
     * @param realisateurForm le Form à convertir
     * @return Un Réalisateur construit à partir des données du Form.
     */
    public static Realisateur convertRealisateurFormToRealisateur(RealisateurForm realisateurForm) {
        Realisateur realisateur = new Realisateur();
        realisateur.setNom(realisateurForm.getNom());
        realisateur.setPrenom(realisateurForm.getPrenom());
        realisateur.setDateNaissance(realisateurForm.getDateNaissance());
        return realisateur;
    }
}