package com.ensta.myfilmlist.model;

import java.util.Objects;

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
	private String title;

	private int duration;

    @ManyToOne
    @JoinColumn(nullable = false)
	private Director director;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public Film() {
		this.id = 0;
		this.title = "";
		this.duration = 0;
		this.director = null;
        this.genre = null;
	}

	public Film(Film film) {
		this.id = film.id;
		this.title = film.title;
		this.duration = film.duration;
		this.director = film.director;
        this.genre = film.genre;
	}

	public Film(Long id, String title, int duration, Director director, Genre genre) {
		this.id = id;
		this.title = title;
		this.duration = duration;
		this.director = new Director(director);
        this.genre = new Genre(genre);
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

    public Director getDirector() {
        return this.director;
    }
    public void setDirector(Director director) {
		this.director = director;
	}

    public Genre getGenre() { return this.genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

	@Override
	public String toString() {
		String directorNameEtSurname = "";
		if (!(this.director == null)) {
			directorNameEtSurname = ", director=" + this.director.getName() + " " + this.director.getSurname();
		}
		return "Film [id=" + id + ", title=" + title + ", duration=" + duration + directorNameEtSurname + "]";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Film film = (Film) o;
        if (Objects.equals(this.getDirector(), film.getDirector())
            && Objects.equals(this.getTitle(), film.getTitle())
            && Objects.equals(this.getDuration(), film.getDuration())
            && this.getId() == film.getId()) {
                return true;
            }
        return false;
    }
}
