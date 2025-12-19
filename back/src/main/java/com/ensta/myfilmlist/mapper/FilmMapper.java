package com.ensta.myfilmlist.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.ensta.myfilmlist.dao.impl.JpaGenreDAO;
import com.ensta.myfilmlist.dao.impl.JpaRealisateurDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Genre;
import com.ensta.myfilmlist.model.Realisateur;
/**
 * Effectue les conversions des Films entre les couches de l'application.
 */
@Repository
public class FilmMapper {

	@Autowired
	private  JpaRealisateurDAO jpaRealisateurDAO;

	@Autowired
	private JpaGenreDAO jpaGenreDAO;
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
		filmDTO.setTitre(film.getTitre());
		filmDTO.setDuree(film.getDuree());
		filmDTO.setRealisateurDTO(RealisateurMapper.convertRealisateurToRealisateurDTO(film.getRealisateur()));
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
		film.setTitre(filmDTO.getTitre());
		film.setDuree(filmDTO.getDuree());
		film.setRealisateur(RealisateurMapper.convertRealisateurDTOToRealisateur(filmDTO.getRealisateurDTO()));
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
		film.setTitre(filmForm.getTitre());
		film.setDuree(filmForm.getDuree());
        // TODO: again, jbdc usage but we should be in jpa?
		Optional<Realisateur> optionalRealisateur = this.jpaRealisateurDAO.findById(filmForm.getRealisateurId());
		if (optionalRealisateur.isPresent()) {
			film.setRealisateur(optionalRealisateur.get());
		} else {
			film.setRealisateur(null);
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
