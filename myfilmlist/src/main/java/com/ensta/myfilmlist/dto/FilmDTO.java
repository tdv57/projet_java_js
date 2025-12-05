package com.ensta.myfilmlist.dto;

import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;

/**
 * Contient les donnees d'un Film.
 */
public class FilmDTO {

	private long id;

	private String titre;

	private int duree;

	private RealisateurDTO realisateurDTO;

	public FilmDTO() {
		this.id = 0;
		this.titre = new String();
		this.duree = 0;
		this.realisateurDTO = new RealisateurDTO();
	}

	public FilmDTO(FilmDTO filmDTO) {
		this.id = filmDTO.id;
		this.titre = filmDTO.titre;
		this.duree = filmDTO.duree;
		this.realisateurDTO = filmDTO.realisateurDTO;
	}

	public FilmDTO(Long id, String titre, int duree, RealisateurDTO realisateurDTO) {
		this.id = id;
		this.titre = titre;
		this.duree = duree;
		this.realisateurDTO = new RealisateurDTO(realisateurDTO);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public void setRealisateurDTO(RealisateurDTO realisateurDTO) {
		this.realisateurDTO = realisateurDTO;
	}

	public RealisateurDTO getRealisateurDTO() {
		return this.realisateurDTO;
	}

	@Override
	public String toString() {
		return "FilmDTO [id=" + id + ", titre=" + titre + ", duree=" + duree + "]";
	}

}
