import {
  BottomNavigation,
  BottomNavigationAction,
  Box,
  Paper,
} from "@mui/material";
import { Link } from "react-router-dom";
import useAppMenu from "../../services/config/useAppMenu";
import { useState } from "react";

export default function BottomNavBar() {
  const [value, setValue] = useState(0);
  const menuList = useAppMenu();

  return (
    <>
      <Box
        sx={{
          position: "fixed",
          bottom: 0,
          left: 0,
          right: 0,
          p: 1,
        }}
      >
        <Paper role="presentation" elevation={3}>
          <BottomNavigation
            showLabels
            value={value}
            onChange={(event, newValue) => {
              setValue(newValue);
            }}
            sx={{
              display: "flex",
              justifyContent: "space-between", // Distribute items evenly
              width:1, // Make sure it's 100% width of its container
            }}
          >
            {menuList.map((item, index) => (
              <BottomNavigationAction
                sx={{
                  p: 0,
                  minWidth:0,
                }}
                LinkComponent={Link}
                to={item.to}
                key={index}
                label={item.label}
                icon={item.icon}
              />
            ))}
          </BottomNavigation>
        </Paper>
      </Box>
    </>
  );
}
