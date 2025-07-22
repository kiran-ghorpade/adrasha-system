import { Routes } from '@angular/router';
import {
  LocationDetailsPageComponent,
  LocationFormPageComponent,
  LocationPageComponent
} from './pages';

export const locationRoutes: Routes = [
  { path: '', component: LocationPageComponent },
  { path: 'new', component: LocationFormPageComponent },
  { path: 'update/:id', component: LocationFormPageComponent },
  { path: ':id', component: LocationDetailsPageComponent },
];
