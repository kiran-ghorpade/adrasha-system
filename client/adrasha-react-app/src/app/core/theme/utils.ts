
// get saved theme from localstorage
export const getUserTheme = () => {
  const savedTheme = localStorage.getItem("theme");
  return savedTheme == "dark";
};