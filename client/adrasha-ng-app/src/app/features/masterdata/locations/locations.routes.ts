import { Routes } from '@angular/router';
import { LocationDetailsComponent } from './components';
import {
  LocationFormPageComponent,
  LocationPageComponent
} from './pages';

export const locationRoutes: Routes = [
  { path: '', component: LocationPageComponent },
  { path: 'new', component: LocationFormPageComponent },
  { path: 'update/:id', component: LocationFormPageComponent },
  { path: ':id', component: LocationDetailsComponent },
];
