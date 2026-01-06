package com.ensta.myfilmlist.mapper;

import com.ensta.myfilmlist.dto.GenreDTO;
import com.ensta.myfilmlist.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Functions to cast Genres into and from DTO and Form.
 */
public class GenreMapper {


    /**
     * Convert a genre's DTO into genre.
     *
     * @param genreDTO  genre's DTO to be converted
     * @return          genre created from the parameter
     */
    public static Genre convertGenreDTOToGenre(GenreDTO genreDTO) {
        if (genreDTO == null) return null;
        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setName(genreDTO.getName());
        return genre;
    }

    /**
     * Convert a genre into a genre's DTO.
     *
     * @param genre     genre to be converted
     * @return          genre's DTO created from the parameter
     */
    public static GenreDTO convertGenreToGenreDTO(Genre genre) {
        if (genre == null) return null;
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        return genreDTO;
    }

    /**
     * Convert a list of genres into a list of genres' DTO.
     *
     * @param genres    list of genres to be converted
     * @return          list of genre's DTO created from the parameter
     */
    public static List<GenreDTO> convertGenreToGenreDTOs(List<Genre> genres) {
        return genres.stream()
                .map(GenreMapper::convertGenreToGenreDTO)
                .collect(Collectors.toList());
    }
}
