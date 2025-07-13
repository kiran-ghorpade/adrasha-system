import { Route } from '@angular/router';
import { RoleRequestDetailsComponent } from './components';
import {
  RoleRequestCreatePageComponent,
  RoleRequestEditPageComponent,
  RoleRequestPageComponent,
} from './pages';

export const roleRequestRoutes: Route[] = [
  { path: '', component: RoleRequestPageComponent },
  { path: 'new', component: RoleRequestCreatePageComponent },
  { path: 'update', component: RoleRequestEditPageComponent },
  { path: ':id', component: RoleRequestDetailsComponent },
];
