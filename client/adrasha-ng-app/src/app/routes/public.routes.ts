import { Routes } from '@angular/router';
import { LogsComponent } from '@features/logs';
import { PageNotFoundComponent } from '@shared/components';
import {
  AppLayout,
  AuthLayoutComponent,
  BlankLayoutComponent,
} from 'app/layout';

export const publicRoutes: Routes = [
  {
    path: '',
    title: 'ADRASHA',
    pathMatch: 'full',
    redirectTo: '/dashboard',
  },
  {
    path: 'auth',
    title: 'Authentication',
    component: AuthLayoutComponent,
    loadChildren: () =>
      import('@features/auth/auth.routes').then((route) => route.authRoutes),
  },
  {
    path: 'logs',
    title: 'Logs',
    component: BlankLayoutComponent,
    pathMatch: 'full',
    children: [{ path: '', component: LogsComponent }],
  },
  {
    path: 'settings',
    title: 'settings',
    component: AppLayout,
    loadChildren: () =>
      import('@features/settings/settings.routes').then(
        (route) => route.settingsRoutes
      ),
  },
  {
    path: '**',
    title: 'ADRASHA : Not Found',
    component: BlankLayoutComponent,
    children: [{ path: '', component: PageNotFoundComponent }],
  },
];
