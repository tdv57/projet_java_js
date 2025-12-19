import TextField from "@mui/material/TextField"
import { useEffect, useState } from "react";
import { getAllRealisateurs } from "./api/RealisateurApi";
import Button from "@mui/material/Button";
import MenuItem from "@mui/material/MenuItem";
import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";

function FilmForm(props) {

    const [realisateurs, setRealisateurs] = useState([]);

    useEffect(() => {
        getAllRealisateurs().then(response => {
            setRealisateurs(response.data);
        }).catch(err => {
            console.log(err);
        })
    }, []);

    return (
        <>
            <Box
                sx={{
                    padding: 2
                }}
            >
                <Grid
                    container spacing={2}
                >
                    <Grid item xs={12} sm={3}>
                        <TextField
                            label="Titre"
                            defaultValue={props.film.titre}
                        />
                    </Grid>

                    <Grid item xs={12} sm={3}>
                        <TextField
                            label="Durée"
                            defaultValue={props.film.duree}
                        />
                    </Grid>

                    <Grid item xs={12}>
                        <Box sx={{ display: 'flex', width: '100%', gap: 2 }}>
                            <TextField
                                label="Réalisateur"
                                select
                                defaultValue={props.film.realisateurDTO.id}
                            >
                                {realisateurs.map((real) => (
                                    <MenuItem key={real.id} value={real.id} >
                                        {real.prenom} {real.nom}
                                    </MenuItem>
                                ))}
                            </TextField >
                            <Button
                                variant="contained"
                                onClick={() => {
                                    alert('clicked');
                                }}
                            >
                                Enregistrer
                            </Button>
                        </Box>
                    </Grid>
                </Grid>
            </Box>
        </>
    )
}

export default FilmForm