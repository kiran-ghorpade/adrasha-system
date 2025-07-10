import { Routes } from '@angular/router';
import { LocationDetailsComponent } from './components';
import {
    LocationCreatePageComponent,
    LocationEditPageComponent,
    LocationPageComponent
} from './pages';

export const locationRoutes: Routes = [
  { path: '', component: LocationPageComponent },
  { path: 'new', component: LocationCreatePageComponent },
  { path: 'update', component: LocationEditPageComponent },
  { path: ':id', component: LocationDetailsComponent },
];
