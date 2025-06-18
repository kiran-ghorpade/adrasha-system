import { useAuth } from "@core/auth";
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
    Paper
} from "@mui/material";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

// settings in profile icon menu
const settings = [
  { label: "Profile", toLink: "/profile", icon: <Avatar /> },
  { label: "Settings", toLink: "/settings", icon: <Settings /> },
];


export default function ProfileMenu() {
  const [anchorElUser, setAnchorElUser] = useState(null);
  const navigate = useNavigate();
  const { logout } = useAuth();

  const handleOpenUserMenu = (event : any) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  return (
    <Paper sx={{ p: 1 }}>
      <IconButton onClick={handleOpenUserMenu}>
        <Avatar />
      </IconButton>

      <Menu
        sx={{
          mt: 5,
          "& .MuiAvatar-root": {
            width: 20,
            height: 20,
          },
        }}
        id="menu-appbar"
        // anchorEl={anchorElUser}
        // anchorOrigin={{
        //   vertical: "top",
        //   horizontal: "right",
        // }}
        // keepMounted
        // transformOrigin={{
        //   vertical: "top",
        //   horizontal: "right",
        // }}
        open={Boolean(anchorElUser)}
        onClose={handleCloseUserMenu}
      >
        {settings.map((setting) => (
          <MenuItem
            component={Link}
            to={setting.toLink}
            key={setting.label}
            onClick={() => {
              handleCloseUserMenu(); // Close the menu before navigation
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
            handleCloseUserMenu(); // Close the menu before logging out
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
  );
}
