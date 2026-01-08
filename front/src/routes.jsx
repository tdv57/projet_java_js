import FilmContainer from "./FilmContainer.jsx";
import DirectorContainer from "./DirectorContainer.jsx";

import LocalMoviesIcon from "@mui/icons-material/LocalMovies";
import PhotoCameraFrontIcon from "@mui/icons-material/PhotoCameraFront";
import Register from "./auth/Register.jsx";
import Login from "./auth/Login.jsx";

export const routes = [
  {
    path: "/",
    element: <FilmContainer />,
    title: "Films",
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
    title: "",
  },
  {
    path: "/login",
    element: <Login />,
    title: "",
  },
];
