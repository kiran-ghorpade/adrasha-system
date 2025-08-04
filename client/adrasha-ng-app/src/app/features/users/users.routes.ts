import { Route } from '@angular/router';
import {
  UserDetailsPageComponent,
  UserFormPageComponent,
  UserPageComponent
} from './pages';

export const usersRoutes: Route[] = [
  { path: '', component: UserPageComponent },
  { path: 'new', component: UserFormPageComponent },
  { path: 'update/:id', component: UserFormPageComponent },
  { path: ':id', component: UserDetailsPageComponent },
];
