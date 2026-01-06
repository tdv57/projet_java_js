package com.ensta.myfilmlist.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import java.util.Optional;

import com.ensta.myfilmlist.dao.impl.JpaGenreDAO;
import com.ensta.myfilmlist.dao.impl.JpaDirectorDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Genre;
import com.ensta.myfilmlist.model.Director;

/**
 * Functions to cast Films into and from DTO and Form.
 */
@Component
public class FilmMapper {

	private final JpaDirectorDAO jpaDirectorDAO;

	private final JpaGenreDAO jpaGenreDAO;
    
	public FilmMapper(JpaDirectorDAO jpaDirectorDAO,
                      JpaGenreDAO jpaGenreDAO) {
        this.jpaDirectorDAO = jpaDirectorDAO;
        this.jpaGenreDAO = jpaGenreDAO;
    }

    /**
     * Convert a list of films into a list of films' DTO.
     *
     * @param films     list of films to be converted
     * @return          list of films' DTO created from the parameter
     */
	public static List<FilmDTO> convertFilmToFilmDTOs(List<Film> films) {
		return films.stream()
				.map(FilmMapper::convertFilmToFilmDTO)
				.collect(Collectors.toList());
	}

    /**
     * Convert a film into a film's DTO.
     *
     * @param film      film to be converted
     * @return          film's DTO created from the parameter
     */
    public static FilmDTO convertFilmToFilmDTO(Film film) {
		if (film == null) return null;
		FilmDTO filmDTO = new FilmDTO();
		filmDTO.setId(film.getId());
		filmDTO.setTitle(film.getTitle());
		filmDTO.setDuration(film.getDuration());
		filmDTO.setDirectorDTO(DirectorMapper.convertDirectorToDirectorDTO(film.getDirector()));
        filmDTO.setGenreDTO(GenreMapper.convertGenreToGenreDTO(film.getGenre()));
		return filmDTO;
	}

    /**
     * Convert a film's DTO into a film.
     *
     * @param filmDTO   film's DTO to be converted
     * @return          film created from the parameter
     */
    public static Film convertFilmDTOToFilm(FilmDTO filmDTO) {
		if (filmDTO == null) return null;
		Film film = new Film();
		film.setId(filmDTO.getId());
		film.setTitle(filmDTO.getTitle());
		film.setDuration(filmDTO.getDuration());
		film.setDirector(DirectorMapper.convertDirectorDTOToDirector(filmDTO.getDirectorDTO()));
        film.setGenre(GenreMapper.convertGenreDTOToGenre(filmDTO.getGenreDTO()));
		return film;
	}

    /**
     * Convert a film's form into a film.
     *
     * @param filmForm  film's form to be converted
     * @return          film created from the parameter
     */
    public Film convertFilmFormToFilm(FilmForm filmForm) {
		Film film = new Film();
		film.setTitle(filmForm.getTitle());
		film.setDuration(filmForm.getDuration());
		Optional<Director> optionalDirector = this.jpaDirectorDAO.findById(filmForm.getDirectorId());
		if (optionalDirector.isPresent()) {
			film.setDirector(optionalDirector.get());
		} else {
			film.setDirector(null);
		}
        Optional<Genre> optionalGenre = this.jpaGenreDAO.findById(filmForm.getGenreId());
        if (optionalGenre.isPresent()) {
            film.setGenre(optionalGenre.get());
        } else {
            film.setGenre(null);
        }
		return film;
	}
}
