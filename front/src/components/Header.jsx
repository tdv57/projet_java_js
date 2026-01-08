import { AppBar, Toolbar, Typography } from "@mui/material";

export default function Header(props) {
  return (
    <Typography variant="h6" sx={{ textAlign: "center", ml: 2 }}>
      {props.title}
    </Typography>
  );
}
