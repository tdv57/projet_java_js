package com.ensta.myfilmlist.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Min;

/**
 * Form to parse create a Film from an entry.
 */
public class FilmForm {

	@NotBlank(message = "Film's title can't be blank/empty.")
	private String title;

	@NotNull(message = "Film's duration must be strictly greater than 0.")
	@Positive(message = "Film's duration must be strictly greater than 0.")
	private int duration;

	@Min(value=1, message="Director's id must be strictly greater than 0.")
	private Long directorId;

    @Min(value=1, message="Genre's id must be strictly greater than 0.")
    private Long genreId;

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
