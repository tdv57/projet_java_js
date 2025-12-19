import TextField from "@mui/material/TextField"
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";

function DirectorForm(props) {

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
                    <Grid size={{ xs: 12, sm: 3 }}>
                        <TextField
                            label="Nom"
                            defaultValue={props.director.nom}
                        />
                    </Grid>

                    <Grid size={{ xs: 12, sm: 3 }}>
                        <TextField
                            label="Prénom"
                            defaultValue={props.director.prenom}
                        />
                    </Grid>

                    <Grid size={{ xs: 12, sm: 3 }}>
                        <TextField
                            label="Date de naissance"
                            defaultValue={props.director.dateNaissance}
                        />
                    </Grid>

                </Grid>
            </Box>
        </>
    )
}

export default FilmForm