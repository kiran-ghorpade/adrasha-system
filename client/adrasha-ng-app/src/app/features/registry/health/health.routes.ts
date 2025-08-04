import { Routes } from '@angular/router';

export const healthRoutes: Routes = [
  // { path: ':id', component: HealthPageComponent },
  {
    path: 'records/:id',
    loadChildren: () =>
      import('./records/health-records.routes').then(
        (routes) => routes.healthRecordRoutes
      ),
  },
];
