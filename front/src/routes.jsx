import FilmContainer from "./FilmContainer.jsx";
import DirectorContainer from "./DirectorContainer.jsx";

import LocalMoviesIcon from "@mui/icons-material/LocalMovies";
import PhotoCameraFrontIcon from "@mui/icons-material/PhotoCameraFront";
import Register from "./Register.jsx";

export const routes = [
  {
    path: "/",
    element: <FilmContainer />,
    title: "Mes films",
    icon: <LocalMoviesIcon />,
  },
  {
    path: "/directors",
    element: <DirectorContainer />,
    title: "Réalisateurs",
    icon: <PhotoCameraFrontIcon />,
  },
  {
    path: "/register",
    element: <Register />,
    title: "Créer un compte",
  },
];
