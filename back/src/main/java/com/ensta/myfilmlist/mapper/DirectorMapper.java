package com.ensta.myfilmlist.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.ensta.myfilmlist.dto.DirectorDTO;
import com.ensta.myfilmlist.form.DirectorForm;
import com.ensta.myfilmlist.model.Director;

/**
 * Functions to cast Directors into and from DTO and Form.
 */
public class DirectorMapper {

    /**
     * Convert a list of directors into a list of directors' DTO.
     *
     * @param directors     list of directors to be converted
     * @return              list of DirectorDTO created from the parameter
     */
    public static List<DirectorDTO> convertDirectorToDirectorDTOs(List<Director> directors) {
        return directors.stream()
                            .map(DirectorMapper::convertDirectorToDirectorDTO)
                            .collect(Collectors.toList());
    }

    /**
     * Convert a director into a director's DTO.
     *
     * @param director  director to be converted
     * @return          DirectorDTO created from the parameter
     */
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


    /**
     * Convert a director's DTO into a director.
     *
     * @param directorDTO   DTO entity to be converted
     * @return              Director created from the parameter
     */
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
     * Convert a director's form into a director.
     *
     * @param directorForm  form to be converted
     * @return              Director created from the parameter
     */
    public static Director convertDirectorFormToDirector(DirectorForm directorForm) {
        Director director = new Director();
        director.setSurname(directorForm.getSurname());
        director.setName(directorForm.getName());
        director.setBirthdate(directorForm.getBirthdate());
        return director;
    }
}