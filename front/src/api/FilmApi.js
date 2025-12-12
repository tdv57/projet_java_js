import axios from 'axios';

const FILM_URI = import.meta.env.VITE_API_URL + '/film'

export function getAllFilms() {
    return axios.get(FILM_URI);
}

export function postFilm(duree, realisateurId, titre) {
    return axios.post(FILM_URI, {
        duree,
        realisateurId,
        titre
    })
}

export function putFilm() {
    return axios.put(FILM_URI, {
        duree,
        realisateurId,
        titre
    })
}

export function deleteFilm(id) {
    return axios.get(FILM_URI + `/delete/${id}`)
}