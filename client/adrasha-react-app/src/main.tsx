import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./app/App";
import "@core/config";  // setup i18n with /config

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <App />
  </StrictMode>
);
