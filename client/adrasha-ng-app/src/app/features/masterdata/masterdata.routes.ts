import { Route } from '@angular/router';
import { MasterDataPageComponent } from './master-data-page/master-data-page.component';

export const masterdataRoutes: Route[] = [
  {
    path: '',
    component: MasterDataPageComponent,
  },
  {
    path: 'locations',
    loadChildren: () =>
      import('./locations/locations.routes').then((m) => m.locationRoutes),
  },
  {
    path: 'healthCenters',
    loadChildren: () =>
      import('./healthCenters/healthCenters.routes').then(
        (m) => m.healthCenterRoutes
      ),
  },
  {
    path: 'ncds',
    loadChildren: () => import('./ncd/ncd.routes').then((m) => m.ncdRoutes),
  },
];
