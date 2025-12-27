package com.ensta.myfilmlist.model;

import javax.persistence.*;

@Entity
@Table
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Film film;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    private int rating;

    public History() {
        this.id = 0L;
        this.film = new Film();
        this.user = new User();
        this.rating = 0;
    }

    public History(User user, Film film) {
        this.user = user;
        this.film = film;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
