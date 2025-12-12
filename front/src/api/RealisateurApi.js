import axios from "axios"

const REALISATEURS_URI = import.meta.env.VITE_API_URL + '/realisateur'

export function getAllRealisateurs() {
    return axios.get(REALISATEURS_URI)
}