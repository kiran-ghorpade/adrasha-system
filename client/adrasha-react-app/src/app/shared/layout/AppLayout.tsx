import { Box, Paper, Stack } from "@mui/material";
import { Sidebar, TopAppBar } from "@shared/components";
import AlertSnackBar from "@shared/components/AlertSnackBar";
import { Outlet } from "react-router-dom";

// Layout
export default function Layout() {
  return (
    <Box sx={{ display: "flex", height: {md:"100vh", xs:"110vh"} }}>
      {/* Sidebar for md and lg screens */}
        <Sidebar />

      {/* Main Content */}
      <Stack flex={1} sx={{ p: { xs: 1, md: 2 } }} spacing={1}>
        {/* TopAppBar */}
        <TopAppBar />

        {/* Alert */}
        <AlertSnackBar />

        {/* Confirmation Dialog */}
        <ConfirmationDialog />

        {/* Main Content */}
        <Paper
          elevation={3}
          component="main"
          sx={{
            // flex: 1,
            overflowY: "auto", // Allows scrolling if content overflows
            paddingBottom: { xs: "10vh" },
            height: 1,
            padding: 1,
          }}
        >
          <Outlet />
        </Paper>

        {/* Bottombar for sm screens */}
        <Hidden smUp>
          <BottomNavBar />
        </Hidden>
      </Stack>
    </Box>
  );
};
