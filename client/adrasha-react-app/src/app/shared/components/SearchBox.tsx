/* eslint-disable react/prop-types */
import { Search } from "@mui/icons-material";
import {
  Autocomplete,
  Avatar,
  IconButton,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { useMember } from "../../services/store/member/useMember";
import DataNotFound from "./DataNotFound";
import Loading from "./Loading";
import { getFullName } from "../../services/utils/getFullName";

export default function SearchBox() {
  const { memberData, isLoading } = useMember();
  console.log(memberData);
  const options = memberData?.map((member) => ({
    name: getFullName(member),
    dateOfBirth: member.dateOfBirth,
  }));
  
  // const options = [
  //   "hello world",
  //   "mello world",
  //   "jello world",
  //   "fello world",
  //   "kello world",
  //   "hello world",
  //   "mello world",
  // ];

  const [value, setValue] = useState(""); // State to manage the selected value

  return (
    <>
      {/* SearchBox */}
      <Autocomplete
        value={value} // Controlled value
        onChange={(event, newValue) => setValue(newValue)} // Set the new value
        disablePortal
        options={options}
        sx={{ p: 0 }}
        renderInput={(params) => <SearchInput params={params} />}
        renderOption={(props, option) => (
          <SearchOutput props={props} option={option} setValue={setValue} />
        )}
        noOptionsText={<NoSearchOption isLoading={isLoading} />}
      />
    </>
  );
}

function SearchInput({ params }) {
  return (
    <Paper
      component="form"
      sx={{
        display: "flex",
        alignItems: "center",
        width: { md: 720, xs: 320 },
      }}
    >
      <IconButton type="submit" sx={{ p: "10px" }} aria-label="search">
        <Search />
      </IconButton>
      <TextField
        {...params}
        placeholder="Search"
        sx={{
          border: "none",
          "& .MuiInputBase-root": {
            border: "none",
          },
          "& .MuiInputBase-input": {
            padding: "10px",
            fontSize: "14px",
            height: 1,
          },
          "& .MuiOutlinedInput-notchedOutline": {
            border: "none",
          },
          "& .MuiAutocomplete-endAdornment": {
            display: "none",
          },
        }}
      />
    </Paper>
  );
}

function SearchOutput({ isLoading, isError, option, props, setValue }) {
  const navigate = useNavigate();

  const handleItemClick = () => {
    setValue(""); // Clear the input field after selection
    navigate(`registry/health/${option?.id}`);
  };

  return (
    <>
      {isLoading && <Loading />}
      {isError && <DataNotFound />}
      <Paper>
        <li {...props}>
          <ListItem onClick={handleItemClick}>
            <ListItemAvatar>
              <Avatar>{option.name[0]}</Avatar>
            </ListItemAvatar>
            <ListItemText
              primary={option.name}
              secondary={
                <Typography variant="body2">
                  BirthDate : {option.birthDate}
                </Typography>
              }
            />
          </ListItem>
        </li>
      </Paper>
    </>
  );
}

function NoSearchOption({ isLoading }) {
  return (
    <>
      <DataNotFound msg="No Member found" alertMsg="Search Valid Keywords" />
      {isLoading && <Loading />}
    </>
  );
}
