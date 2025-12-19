package com.ensta.myfilmlist.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Min;
/**
 * Contient les donnees pour créer un nouveau film.
 */
public class FilmForm {

	@NotBlank(message = "Le title ne peut pas être vide")
	private String title;

	@NotNull(message = "La durée doit être strictement supérieure à 0")
	@Positive(message = "La durée doit être strictement supérieure à 0")
	private int duration;

	@Min(value=1, message="L'id du director doit être strictement positif")
	private long directorId;

    @Min(value=1, message="L'id du genre doit être strictement positif")
    private long genreId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public long getDirectorId() {
		return directorId;
	}

	public void setDirectorId(long directorId) {
		this.directorId = directorId;
	}

    public long getGenreId() {return genreId;}

    public void setGenreId(long genreId) {this.genreId = genreId;}
}
