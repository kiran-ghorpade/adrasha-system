import { Route } from '@angular/router';
import {
  RoleRequestDetailsPageComponent,
  RoleRequestFormPageComponent,
  RoleRequestPageComponent
} from './pages';

export const roleRequestRoutes: Route[] = [
  { path: '', component: RoleRequestPageComponent },
  { path: 'new', component: RoleRequestFormPageComponent },
  { path: 'update/:id', component: RoleRequestFormPageComponent },
  { path: ':id', component: RoleRequestDetailsPageComponent },
];
