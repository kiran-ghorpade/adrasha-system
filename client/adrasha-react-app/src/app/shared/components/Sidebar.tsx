import {
  Box,
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Paper,
  useTheme,
} from "@mui/material";
import { Link, useLocation } from "react-router-dom";
import useAppMenu from "../../services/config/useAppMenu";

export default function Sidebar() {
  const location = useLocation();
  const menuList = useAppMenu();
  const theme = useTheme();

  return (
    <Box
      sx={{
        flexShrink: 0,
        p: 2,
        display: "flex",
        alignItems: "center",
        flexDirection: "column",
        justifyContent: "start",
        paddingX: 12,
        width: 80,
      }}
    >
      <Box
        sx={{
          display: "flex",
          alignItems: "center",
          flexDirection: "column",
          justifyContent: "center",
          height: "100%",
        }}
      >
        <Paper elevation={3}>
          <List sx={{ width: 80 }}>
            {menuList.map((item, index) => (
              <ListItemButton
                LinkComponent={Link}
                to={item.to}
                key={index}
                selected={location.pathname.includes(item.to)}
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "center",
                  height: 56,
                  paddingY: 5,
                  paddingX: 2,
                  "&.Mui-selected": {
                    backgroundColor: "transparent", // No background for the entire button
                  },
                  "&.Mui-selected:hover": {
                    backgroundColor: "transparent", // No background when selected and hovered
                  },
                  "&:hover": {
                    backgroundColor: "transparent", // No background for the entire button
                  },
                }}
              >
                {/* Icon Section */}
                <ListItemIcon
                  sx={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    width: 56,
                    height: 32, // Make the area around the icon round
                    borderRadius: 16, // Ensure the icon's background is circular
                    paddingX: 4,
                    transition: "background-color 0.3s ease, color 0.3s ease",
                    backgroundColor: location.pathname.includes(item.to)
                      ? theme.palette.secondary.main
                      : "transparent", // Blue background when selected
                    "&:hover": {
                      backgroundColor: theme.palette.secondary.main, // Hover effect with slight blue
                    },
                  }}
                >
                  {item.icon}
                </ListItemIcon>

                {/* Text Section */}
                <ListItemText
                  secondary={item.label}
                  slotProps={{
                    primary: {
                      // sx: {
                      fontSize: "0.875rem",
                      fontWeight: location.pathname.includes(item.to)
                        ? 700
                        : 400,
                      color: location.pathname.includes(item.to)
                        ? "#1976d2"
                        : "inherit",
                      transition:
                        "color 0.2s ease-in-out, font-weight 0.2s ease-in-out",
                      // },
                    },
                  }}
                />
              </ListItemButton>
            ))}
          </List>
        </Paper>
      </Box>
    </Box>
  );
}
