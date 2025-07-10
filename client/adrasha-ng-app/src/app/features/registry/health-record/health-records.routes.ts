import { Routes } from '@angular/router';
import {
  HealthRecordCreatePageComponent,
  HealthRecordEditPageComponent,
  HealthRecordPageComponent,
} from './pages';
import { HealthRecordDetailsComponent } from './components';

export const healthRecordRoutes: Routes = [
  { path: '', component: HealthRecordPageComponent },
  { path: 'new', component: HealthRecordCreatePageComponent },
  { path: 'update', component: HealthRecordEditPageComponent },
  { path: ':id', component: HealthRecordDetailsComponent },
];
