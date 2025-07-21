import { Routes } from '@angular/router';

export const registryRoutes: Routes = [
  // { path: '', component: RegistryPageComponent },
  {
    path: 'families',
    loadChildren: () =>
      import('./family/family.routes').then((m) => m.familyRoutes),
  },
  {
    path: 'members',
    loadChildren: () =>
      import('./member/member.routes').then((m) => m.memberRoutes),
  },
  {
    path: 'health',
    loadChildren: () =>
      import('./health/health.routes').then((m) => m.healthRoutes),
  },
];