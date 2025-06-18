import {
  Stack
} from "@mui/material";
import Logo from "./Logo";
import ProfileMenu from "./ProfileMenu";



export default function TopAppBar() {

  return (
    <Stack
      direction="row"
      spacing={1}
      width="100%"
      justifyContent="space-between"
      alignItems="center"
    >

      <Logo logoSrc="/app/Adrasha_Logo.ico" logoTitle="ADRASHA"/>

      {/* Search Box */}
      {/* <SearchBox /> */}

      {/* Profile Avatar */}
      <ProfileMenu />
    </Stack>
  );
}
