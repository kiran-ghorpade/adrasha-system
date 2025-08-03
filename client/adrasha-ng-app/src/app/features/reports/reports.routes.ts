import { Routes } from '@angular/router';
import { ReportsPageComponent } from './pages';

export const reportsRoutes: Routes = [
  { path: '', component: ReportsPageComponent },
//   {
//     path: 'families',
//     loadChildren: () =>
//       import('./family/family.routes').then((m) => m.familyRoutes),
//   },
];