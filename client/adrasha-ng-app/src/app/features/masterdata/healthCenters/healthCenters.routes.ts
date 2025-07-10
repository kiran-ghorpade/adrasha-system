import { Routes } from '@angular/router';
import {
  HealthCenterCreatePageComponent,
  HealthCenterEditPageComponent,
  HealthCenterPageComponent,
} from './pages';
import { HealthCenterDetailsComponent } from './components';

export const healthCenterRoutes: Routes = [
  { path: '', component: HealthCenterPageComponent },
  { path: 'new', component: HealthCenterCreatePageComponent },
  { path: 'update', component: HealthCenterEditPageComponent },
  { path: ':id', component: HealthCenterDetailsComponent },
];
