import { Route } from '@angular/router';
import { RoleRequestPageComponent } from './role-request-page/role-request-page.component';
import { RoleRequestFormComponent } from './role-request-form/role-request-form.component';

export const roleRequestRoutes: Route[] = [
  { path: 'history', component: RoleRequestPageComponent },
  { path: 'new', component: RoleRequestFormComponent },
];
