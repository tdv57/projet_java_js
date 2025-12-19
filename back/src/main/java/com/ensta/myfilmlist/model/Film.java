package com.ensta.myfilmlist.model;

import javax.persistence.*;

/**
 * Represente un Film.
 */
@Entity
@Table
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @Column
	private String titre;

	private int duree;

    @ManyToOne
    @JoinColumn(nullable = false)
	private Realisateur realisateur;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Film() {
		this.id = 0;
		this.titre = "";
		this.duree = 0;
		this.realisateur = null;
        this.genre = null;
	}

	public Film(Film film) {
		this.id = film.id;
		this.titre = film.titre;
		this.duree = film.duree;
		this.realisateur = film.realisateur;
        this.genre = film.genre;
	}

	public Film(Long id, String titre, int duree, Realisateur realisateur, Genre genre) {
		this.id = id;
		this.titre = titre;
		this.duree = duree;
		this.realisateur = new Realisateur(realisateur);
        this.genre = new Genre(genre);
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

    public Realisateur getRealisateur() {
        return this.realisateur;
    }
    public void setRealisateur(Realisateur realisateur) {
		this.realisateur = realisateur;
	}

    public Genre getGenre() { return this.genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

	@Override
	public String toString() {
		String realisateurPrenomEtNom = "";
		if (!(this.realisateur == null)) {
			realisateurPrenomEtNom = ", realisateur=" + this.realisateur.getPrenom() + " " + this.realisateur.getNom();
		}
		return "Film [id=" + id + ", titre=" + titre + ", duree=" + duree + realisateurPrenomEtNom + "]";
	}
}
