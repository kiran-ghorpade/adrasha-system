import { SideMenu } from "@core/constants";
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

type SidebarProps = {
  menuList: SideMenu[];
};

export default function Sidebar({ menuList }: SidebarProps) {
  const location = useLocation();
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
                key={index}
                selected={location.pathname.includes(item.route)}
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "center",
                  height: 56,
                  paddingY: 5,
                  paddingX: 2,
                  "&.Mui-selected": {
                    backgroundColor: "transparent",
                  },
                  "&.Mui-selected:hover": {
                    backgroundColor: "transparent",
                  },
                  "&:hover": {
                    backgroundColor: "transparent",
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
                    height: 32,
                    borderRadius: 16,
                    paddingX: 4,
                    transition: "background-color 0.3s ease, color 0.3s ease",
                    backgroundColor: location.pathname.includes(item.route)
                      ? theme.palette.secondary.main
                      : "transparent",
                    "&:hover": {
                      backgroundColor: theme.palette.secondary.main,
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
                      sx: {
                        fontSize: "0.875rem",
                        fontWeight: location.pathname.includes(item.route)
                          ? 700
                          : 400,
                        color: location.pathname.includes(item.route)
                          ? "#1976d2"
                          : "inherit",
                        transition:
                          "color 0.2s ease-in-out, font-weight 0.2s ease-in-out",
                      },
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
