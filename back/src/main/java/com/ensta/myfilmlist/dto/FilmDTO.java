package com.ensta.myfilmlist.dto;

/**
 * Class representing data of a Film.
 * DTO: transferred data between layers.
 */
public class FilmDTO {

	private long id;

	private String title;

	private int duration;

	private DirectorDTO directorDTO;

    private GenreDTO genreDTO;

	public FilmDTO() {
		this.id = 0L;
		this.title = "";
		this.duration = 0;
		this.directorDTO = new DirectorDTO();
        this.genreDTO = new GenreDTO();
	}

	public FilmDTO(FilmDTO filmDTO) {
		this.id = filmDTO.id;
		this.title = filmDTO.title;
		this.duration = filmDTO.duration;
		this.directorDTO = filmDTO.directorDTO;
        this.genreDTO = filmDTO.genreDTO;
	}

	public FilmDTO(long id, String title, int duration, DirectorDTO directorDTO) {
		this.id = id;
		this.title = title;
		this.duration = duration;
		this.directorDTO = new DirectorDTO(directorDTO);
        this.genreDTO = new GenreDTO();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public void setDirectorDTO(DirectorDTO directorDTO) {
		this.directorDTO = directorDTO;
	}

	public DirectorDTO getDirectorDTO() {
		return this.directorDTO;
	}

    public void setGenreDTO(GenreDTO genreDTO) {this.genreDTO = genreDTO;}

    public GenreDTO getGenreDTO() {return this.genreDTO;}

	@Override
	public String toString() {
		return "FilmDTO [id=" + id + ", title=" + title + ", duration=" + duration + "]";
	}

}
