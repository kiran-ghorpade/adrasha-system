import { Routes } from '@angular/router';
import {
  HealthRecordPageComponent,
} from './pages';
import { HealthRecordDetailsComponent } from './components';
import { HealthRecordFormPageComponent } from './pages/health-record-form-page/health-record-form-page.component';

export const healthRecordRoutes: Routes = [
  { path: '', component: HealthRecordPageComponent },
  { path: 'new', component: HealthRecordFormPageComponent },
  { path: 'update/:id', component: HealthRecordFormPageComponent },
  { path: ':id', component: HealthRecordDetailsComponent },
];
