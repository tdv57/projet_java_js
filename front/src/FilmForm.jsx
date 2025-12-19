import TextField from "@mui/material/TextField"
import { useEffect, useState } from "react";
import { getAllDirectors } from "./api/DirectorApi";
import { getAllGenres } from "./api/GenreApi";
import { editFilm, addFilm } from './api/FilmApi'
import Button from "@mui/material/Button";
import MenuItem from "@mui/material/MenuItem";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";

function FilmForm(props) {

    const [Directors, setDirectors] = useState([]);

    useEffect(() => {
        getAllDirectors().then(response => {
            setDirectors(response.data);
        }).catch(err => {
            console.log(err);
        })
    }, []);

    const [genres, setGenres] = useState([]);

    useEffect(() => {
        getAllGenres().then(response => {
            setGenres(response.data);
        }).catch(err => {
            console.log(err);
        })
    }, []);



    return (
        <>
            <Box
                sx={{
                    padding: 2,
                }}
            >
                <Grid
                    container spacing={2}
                    sx={{ alignItems: 'center' }}
                >
                    <Grid size={{ xs: 12, sm: 3.5 }}>
                        <TextField
                            label="Titre"
                            defaultValue={props.film.titre}
                            onChange={(e) => props.film.titre = e.target.value}
                        />
                    </Grid>

                    <Grid size={{ xs: 12, sm: 1.5 }}>
                        <TextField
                            label="Durée"
                            defaultValue={props.film.duree}
                            onChange={(e) => props.film.duree = e.target.value}
                        />
                    </Grid>

                    <Grid size={{ xs: 12, sm: 2.5 }}>
                        <TextField
                            sx={{ display: 'flex', width: '100%' }}
                            label="Genre"
                            select
                            defaultValue={props.film.genre}
                            onChange={(e) => props.film.genreDTO.id = e.target.value}
                        >
                            {genres.map((real) => (
                                <MenuItem key={real.id} value={real.id} >
                                    {real.nom}
                                </MenuItem>
                            ))}
                        </TextField >
                    </Grid>

                    <Grid size={{ xs: 12, sm: 2.5 }}>
                        <TextField
                            sx={{ display: 'flex', width: '100%' }}
                            label="Réalisateur"
                            select
                            defaultValue={props.film.realisateurDTO.id || ""}
                            onChange={(e) => { console.log(e); props.film.realisateurDTO.id = e.target.value }}
                        >
                            {Directors.map((real) => (
                                <MenuItem key={real.id} value={real.id} >
                                    {real.prenom} {real.nom}
                                </MenuItem>
                            ))}

                            <MenuItem sx={{
                                display: 'flex',
                                justifyContent: 'center',
                                padding: '0',
                            }}>
                                <Button sx={{ width: '100%' }}>
                                    Ajouter
                                </Button>
                            </MenuItem>
                        </TextField >
                    </Grid>

                    <Grid size={{ xs: 12, sm: 2 }}>
                        <Box sx={{ display: 'flex', width: '100%' }}>
                            <Button
                                sx={{ height: '100%' }}
                                variant="contained"
                                onClick={() => {
                                    console.log(props.film);
                                    props.isNew ?
                                        addFilm(
                                            props.film.duree,
                                            props.film.realisateurDTO.id,
                                            props.film.titre,
                                            props.film.genreDTO.id
                                        )
                                        : editFilm(
                                            props.film.id,
                                            props.film.duree,
                                            props.film.realisateurDTO.id,
                                            props.film.titre,
                                            props.film.genreDTO.id
                                        )
                                }}
                            >
                                Enregistrer
                            </Button>
                        </Box>
                    </Grid>
                </Grid >
            </Box >
        </>
    )
}

export default FilmForm