package com.ensta.myfilmlist.mapper;

import com.ensta.myfilmlist.dto.GenreDTO;
import com.ensta.myfilmlist.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class GenreMapper {

    public static Genre convertGenreDTOToGenre(GenreDTO genreDTO) {
        if (genreDTO == null) return null;
        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setSurname(genreDTO.getSurname());
        return genre;
    }

    public static GenreDTO convertGenreToGenreDTO(Genre genre) {
        if (genre == null) return null;
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(genre.getId());
        genreDTO.setSurname(genre.getSurname());
        return genreDTO;
    }

    public static List<GenreDTO> convertGenreToGenreDTOs(List<Genre> genres) {
        return genres.stream()
                .map(GenreMapper::convertGenreToGenreDTO)
                .collect(Collectors.toList());
    }
}
