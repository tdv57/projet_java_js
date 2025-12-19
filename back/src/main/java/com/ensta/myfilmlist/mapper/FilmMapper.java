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
 * Effectue les conversions des Films entre les couches de l'application.
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
	 * Convertit une liste de films en liste de DTO.
	 * 
	 * @param films la liste des films
	 * @return Une liste non nulle de dtos construite a partir de la liste des films.
	 */
	public static List<FilmDTO> convertFilmToFilmDTOs(List<Film> films) {
		return films.stream()
				.map(FilmMapper::convertFilmToFilmDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Convertit un film en DTO.
	 * 
	 * @param film le film a convertir
	 * @return Un DTO construit a partir des donnees du film.
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
	 * Convertit un DTO en film.
	 * 
	 * @param filmDTO le DTO a convertir
	 * @return Un Film construit a partir des donnes du DTO.
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
	 * Convertit un formulaire de film (FilmForm) en film.
	 * 
	 * @param filmForm le Form à convertir
	 * @return Un Film construit à partir des données du Form.
	 */
	public Film convertFilmFormToFilm(FilmForm filmForm) {
		Film film = new Film();
		film.setTitle(filmForm.getTitle());
		film.setDuration(filmForm.getDuration());
        // TODO: again, jbdc usage but we should be in jpa?
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
