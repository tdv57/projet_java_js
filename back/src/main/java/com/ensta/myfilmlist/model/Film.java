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

	public Film() {
		this.id = 0;
		this.titre = "";
		this.duree = 0;
		this.realisateur = null;
	}

	public Film(Film film) {
		this.id = film.id;
		this.titre = film.titre;
		this.duree = film.duree;
		this.realisateur = film.realisateur;
	}

	public Film(Long id, String titre, int duree, Realisateur realisateur) {
		this.id = id;
		this.titre = titre;
		this.duree = duree;
		this.realisateur = new Realisateur(realisateur);
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

	public void setRealisateur(Realisateur realisateur) {
		this.realisateur = realisateur;
	}

	public Realisateur getRealisateur() {
		return this.realisateur;
	}

	@Override
	public String toString() {
		String realisateurPrenomEtNom = "";
		if (!(this.realisateur == null)) {
			realisateurPrenomEtNom = ", realisateur=" + this.realisateur.getPrenom() + " " + this.realisateur.getNom();
		}
		return "Film [id=" + id + ", titre=" + titre + ", duree=" + duree + realisateurPrenomEtNom + "]";
	}
}
