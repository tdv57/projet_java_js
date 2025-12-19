package com.ensta.myfilmlist.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Min;
/**
 * Contient les donnees pour créer un nouveau film.
 */
public class FilmForm {

	@NotBlank(message = "Le titre ne peut pas être vide")
	private String titre;

	@NotNull(message = "La durée doit être strictement supérieure à 0")
	@Positive(message = "La durée doit être strictement supérieure à 0")
	private int duree;

	@Min(value=1, message="L'id du realisateur doit être strictement positif")
	private long realisateurId;

    @Min(value=1, message="L'id du genre doit être strictement positif")
    private long genreId;

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

	public long getRealisateurId() {
		return realisateurId;
	}

	public void setRealisateurId(long realisateurId) {
		this.realisateurId = realisateurId;
	}

    public long getGenreId() {return genreId;}

    public void setGenreId(long genreId) {this.genreId = genreId;}
}
