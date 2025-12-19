package com.ensta.myfilmlist.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.model.Director;


public class DirectorMapper {
    public static List<DirectorDTO> convertDirectorToDirectorDTOs(List<Director> directors) {
        return directors.stream()
                            .map(DirectorMapper::convertDirectorToDirectorDTO)
                            .collect(Collectors.toList());
    }

    public static DirectorDTO convertDirectorToDirectorDTO(Director director) {
        if (director == null) return null;
        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setFamous(director.isFamous());
        directorDTO.setBirthdate(director.getBirthdate());
        directorDTO.setId(director.getId());
        directorDTO.setSurname(director.getSurname());
        directorDTO.setName(director.getName());
        return directorDTO;
    }

    public static Director convertDirectorDTOToDirector(DirectorDTO directorDTO) {
        if (directorDTO == null) return null;
        Director director = new Director();
        director.setFamous(directorDTO.isFamous());
        director.setBirthdate(directorDTO.getBirthdate());
        director.setId(directorDTO.getId());
        director.setSurname(directorDTO.getSurname());
        director.setName(directorDTO.getName());
        return director;
    }


    /**
     * Convertit un formulaire de réalisateur en film.
     *
     * @param directorForm le Form à convertir
     * @return Un Réalisateur construit à partir des données du Form.
     */
    public static Director convertDirectorFormToDirector(DirectorForm directorForm) {
        Director director = new Director();
        director.setSurname(directorForm.getSurname());
        director.setName(directorForm.getName());
        director.setBirthdate(directorForm.getBirthdate());
        return director;
    }
}