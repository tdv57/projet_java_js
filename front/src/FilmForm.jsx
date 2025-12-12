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
        <Grid container spacing={2}>
            <Container>
                <TextField
                    label="Titre"
                    defaultValue={props.film.titre}
                />

                <TextField
                    label="Durée"
                    defaultValue={props.film.duree}
                />

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
            </Container>

            <Button onClick={() => {
                alert('clicked');
            }}>Enregistrer</Button>
        </Grid>
    )
}

export default FilmForm