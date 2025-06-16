import { Logout, Settings } from "@mui/icons-material";
import {
  Avatar,
  Divider,
  Icon,
  IconButton,
  ListItemIcon,
  ListItemText,
  Menu,
  MenuItem,
  Paper,
  Stack,
  Typography,
} from "@mui/material";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../services/store/auth/useAuth";
import ProfileAvatar from "../core/ProfileAvatar";
import SearchBox from "./SearchBox";

// settings in profile icon menu
const settings = [
  { label: "Profile", toLink: "/profile", icon: <Avatar /> },
  { label: "Settings", toLink: "/settings", icon: <Settings /> },
];

export default function TopAppBar() {
  const [anchorElUser, setAnchorElUser] = useState(null);
  const navigate = useNavigate();
  const { logout } = useAuth();

  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    <Stack
      direction="row"
      spacing={1}
      width="100%"
      justifyContent="space-between"
      alignItems="center"
    >
      {/* LOGO */}
      <Paper>
        <Stack direction="row" alignItems="center">
          <IconButton color="inherit">
            <img src="/app/Adrasha_Logo.ico" style={{ height: 22 }} />
          </IconButton>
          <Typography
            align="center"
            sx={{ p: 1, display: { xs: "none", md: "block" } }}
          >
            ADRASHA
          </Typography>
        </Stack>
      </Paper>

      {/* Search Box */}
      <SearchBox />

      {/* Profile Avatar */}
      <Paper sx={{ p: 1 }}>
        <ProfileAvatar
          handleClick={handleOpenUserMenu}
          tooltip="Open settings"
        />

        <Menu
          sx={{
            mt: 5,
            "& .MuiAvatar-root": {
              width: 20,
              height: 20,
            },
          }}
          id="menu-appbar"
          anchorEl={anchorElUser}
          anchorOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
          keepMounted
          transformOrigin={{
            vertical: "top",
            horizontal: "right",
          }}
          open={Boolean(anchorElUser)}
          onClose={handleCloseUserMenu}
        >
          {settings.map((setting) => (
            <MenuItem
              component={Link}
              to={setting.toLink}
              key={setting.label}
              onClick={() => {
                handleCloseUserMenu();  // Close the menu before navigation
                navigate(setting.toLink, { replace: true });
              }}
            >
              <ListItemIcon>
                <Icon>{setting.icon}</Icon>
              </ListItemIcon>
              <ListItemText>{setting.label}</ListItemText>
            </MenuItem>
          ))}
          <Divider />
          <MenuItem
            onClick={() => {
              handleCloseUserMenu();  // Close the menu before logging out
              logout();
            }}
          >
            <ListItemIcon>
              <Logout fontSize="small" />
            </ListItemIcon>
            <ListItemText>Logout</ListItemText>
          </MenuItem>
        </Menu>
      </Paper>
    </Stack>
  );
}
