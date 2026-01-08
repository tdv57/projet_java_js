import axios from "axios";
import { doRequest } from "../utils.js";

const HISTORY_URI = import.meta.env.VITE_API_URL + "/history";

export function getFilmRate(filmId) {
  return doRequest(axios.get(HISTORY_URI + "/rate", { params: { filmId } }), {
    error: "Impossible de récupérer la note du film.",
  });
}
