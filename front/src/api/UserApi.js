import axios from "axios";
import { doRequest } from "../utils";

const USERS_URI = import.meta.env.VITE_API_URL + "/user";

export function register(name, surname, username, password) {
  console.log("REGISTER", name, surname, username, password);
  return doRequest(
    axios.post(USERS_URI, {
      surname,
      name,
      username,
      password,
    }),
    {
      200: "Compte créé !",
      409: "Ce nom d'utilisateur existe déjà.",
    },
  );
}

export function login(username, password) {
  console.log("LOGIN", username, password);
  return doRequest(
    axios.post(USERS_URI + "/login", {
      username,
      password,
    }),
    {
      403: "Nom d'utilisateur ou mot de passe incorrect.",
    },
  );
}
