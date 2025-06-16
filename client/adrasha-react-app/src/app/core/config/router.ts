import { appRoutes } from "app/app.routes";
import { createHashRouter } from "react-router-dom";


// Router Provider
export const router = createHashRouter(appRoutes);
