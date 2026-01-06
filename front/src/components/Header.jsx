import { AppBar, Toolbar, Typography } from "@mui/material";

export default function Header(props) {
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" sx={{ textAlign: "center" }}>
          {props.title}
        </Typography>
      </Toolbar>
    </AppBar>
  );
}
