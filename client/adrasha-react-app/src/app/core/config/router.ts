import { createHashRouter } from "react-router-dom";
import { appRoutes } from "../../app.routes"

// Router Provider
export const router = createHashRouter(appRoutes);
