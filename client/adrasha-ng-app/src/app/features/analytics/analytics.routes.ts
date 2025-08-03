import { Routes } from '@angular/router';
import { AnalyticsPageComponent } from './pages';

export const analyticsRoutes: Routes = [
  { path: '', component: AnalyticsPageComponent },
//   {
//     path: 'families',
//     loadChildren: () =>
//       import('./family/family.routes').then((m) => m.familyRoutes),
//   },
];