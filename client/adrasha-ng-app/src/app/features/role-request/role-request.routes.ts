import { Route } from '@angular/router';
import { RoleRequestDetailsComponent } from './components';
import {
  RoleRequestCreatePageComponent,
  RoleRequestEditPageComponent,
} from './pages';
import { RoleRequestPageComponent } from './role-request-page/role-request-page.component';

export const roleRequestRoutes: Route[] = [
  { path: '', component: RoleRequestPageComponent },
  { path: 'new', component: RoleRequestCreatePageComponent },
  { path: 'update', component: RoleRequestEditPageComponent },
  { path: ':id', component: RoleRequestDetailsComponent },
];
