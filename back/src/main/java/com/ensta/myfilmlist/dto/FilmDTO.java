package com.ensta.myfilmlist.dto;

/**
 * Contient les donnees d'un Film.
 */
public class FilmDTO {

	private long id;

	private String titre;

	private int duree;

	private RealisateurDTO realisateurDTO;

    private GenreDTO genreDTO;

	public FilmDTO() {
		this.id = 0;
		this.titre = "";
		this.duree = 0;
		this.realisateurDTO = new RealisateurDTO();
        this.genreDTO = new GenreDTO();
	}

	public FilmDTO(FilmDTO filmDTO) {
		this.id = filmDTO.id;
		this.titre = filmDTO.titre;
		this.duree = filmDTO.duree;
		this.realisateurDTO = filmDTO.realisateurDTO;
        this.genreDTO = filmDTO.genreDTO;
	}

	public FilmDTO(Long id, String titre, int duree, RealisateurDTO realisateurDTO) {
		this.id = id;
		this.titre = titre;
		this.duree = duree;
		this.realisateurDTO = new RealisateurDTO(realisateurDTO);
        this.genreDTO = new GenreDTO();
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

    public void setGenreDTO(GenreDTO genreDTO) {this.genreDTO = genreDTO;}

    public GenreDTO getGenreDTO() {return this.genreDTO;}

	@Override
	public String toString() {
		return "FilmDTO [id=" + id + ", titre=" + titre + ", duree=" + duree + "]";
	}

}
