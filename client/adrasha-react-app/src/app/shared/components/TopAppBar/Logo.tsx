import { IconButton, Paper, Stack, Typography } from "@mui/material";

type LogoProps = {
    logoSrc : string,
    logoTitle : string
}

export default function Logo({logoSrc, logoTitle}: LogoProps) {
  return (
      <Paper>
        <Stack direction="row" alignItems="center">
          <IconButton color="inherit">
            <img src={logoSrc} style={{ height: 22 }} />
          </IconButton>
          <Typography
            align="center"
            sx={{ p: 1, display: { xs: "none", md: "block" } }}
          >
            {logoTitle}
          </Typography>
        </Stack>
      </Paper>
  )
}
