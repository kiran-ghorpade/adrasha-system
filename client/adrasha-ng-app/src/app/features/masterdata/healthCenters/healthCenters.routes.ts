import { Routes } from '@angular/router';
import { HealthCenterDetailsComponent } from './components';
import {
  HealthCenterFormPageComponent,
  HealthCenterPageComponent
} from './pages';

export const healthCenterRoutes: Routes = [
  { path: '', component: HealthCenterPageComponent },
  { path: 'new', component: HealthCenterFormPageComponent },
  { path: 'update/:id', component: HealthCenterFormPageComponent },
  { path: ':id', component: HealthCenterDetailsComponent },
];
