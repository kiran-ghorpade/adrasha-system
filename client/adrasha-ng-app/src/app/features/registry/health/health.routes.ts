import { Routes } from '@angular/router';
import { HealthPageComponent } from './health-page/health-page.component';

export const healthRoutes: Routes = [
  { path: ':id', component: HealthPageComponent },
  {
    path: 'records/:id',
    loadChildren: () =>
      import('../health-record/health-records.routes').then(
        (routes) => routes.healthRecordRoutes
      ),
  },
];
