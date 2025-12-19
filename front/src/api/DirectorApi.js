import axios from "axios"

const DIRECTORS_URI = import.meta.env.VITE_API_URL + '/realisateur'

export function getAllDirectors() {
    return axios.get(DIRECTORS_URI)
}

export function getDirectorByName(firstName, lastName) {
    return axios.get(DIRECTORS_URI, {
        params: {
            nom: lastName,
            prenom: firstName
        }
    })
}

export function addDirector(firstName, lastName, birthDate) {
    return axios.post(DIRECTORS_URI, {
        nom: lastName,
        prenom: firstName,
        dateNaissance: birthDate,
    })
}

export function getDirectorById(id) {
    return axios.get(DIRECTORS_URI + `/${id}`);
}

export function editDirector(id, firstName, lastName, birthDate) {
    return axios.put(DIRECTORS_URI + `/${id}`, { nom: firstName, prenom: lastName, dateNaissance: birthDate });
}

export function deleteDirector(id) {
    return axios.delete(DIRECTORS_URI + `/${id}`);
}
