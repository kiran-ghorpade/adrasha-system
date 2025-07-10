import { Routes } from '@angular/router';
import { HealthPageComponent } from './health-page/health-page.component';

export const healthRoutes: Routes = [
  { path: '', component: HealthPageComponent },
  {
    path: 'records',
    loadChildren: () =>
      import('../health-record/health-records.routes').then(
        (routes) => routes.healthRecordRoutes
      ),
  },
];
