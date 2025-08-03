import { Routes } from '@angular/router';
import { adminRoutes, ashaRoutes, commonRoutes, publicRoutes } from './routes';

export const routes: Routes = [
  ...adminRoutes,
  ...ashaRoutes,
  ...commonRoutes,
  ...publicRoutes,
];
