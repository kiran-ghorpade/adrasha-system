import { Routes } from '@angular/router';
import { authGuard } from '@core/guards';
import { roleGuard } from '@core/guards/role.guard';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { LogsComponent } from '@features/logs';
import { PageNotFoundComponent } from '@shared/components';
import { AppLayout, AuthLayoutComponent, BlankLayoutComponent } from './layout';

export const routes: Routes = [
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
    path: 'profile',
    title: 'Profile',
    canActivate: [authGuard, roleGuard],
    data: {
      roles: [
        UserResponseDTORolesItem.ADMIN,
        UserResponseDTORolesItem.ASHA,
        UserResponseDTORolesItem.USER,
      ],
    },
    component: AppLayout,
    loadChildren: () =>
      import('@features/profile/profile.routes').then(
        (route) => route.profileRoutes
      ),
  },
  {
    path: 'dashboard',
    title: 'Dashboard',
    canActivate: [authGuard, roleGuard],
    data: {
      roles: [
        UserResponseDTORolesItem.ADMIN,
        UserResponseDTORolesItem.ASHA,
        UserResponseDTORolesItem.USER,
      ],
    },
    component: AppLayout,
    loadChildren: () =>
      import('@features/dashboard/dashboard.routes').then(
        (route) => route.dashboardRoutes
      ),
  },
  {
    path: 'registry',
    title: 'Registry',
    canActivate: [authGuard, roleGuard],
    data: {
      roles: [UserResponseDTORolesItem.ASHA],
    },
    component: AppLayout,
    loadChildren: () =>
      import('@features/registry/registry.routes').then(
        (route) => route.registryRoutes
      ),
  },
  {
    path: 'reports',
    title: 'Reports',
    canActivate: [authGuard, roleGuard],
    data: {
      roles: [UserResponseDTORolesItem.ASHA],
    },
    component: AppLayout,
    loadChildren: () =>
      import('@features/reports/reports.routes').then(
        (route) => route.reportsRoutes
      ),
  },
  {
    path: 'role-requests',
    title: 'Role Request',
    canActivate: [authGuard, roleGuard],
    data: {
      roles: [UserResponseDTORolesItem.ADMIN, UserResponseDTORolesItem.USER],
    },
    component: AppLayout,
    loadChildren: () =>
      import('@features/role-request/role-request.routes').then(
        (route) => route.roleRequestRoutes
      ),
  },
  {
    path: 'masterdata',
    title: 'Masterdata',
    canActivate: [authGuard, roleGuard],
    data: { roles: [UserResponseDTORolesItem.ADMIN] },
    component: AppLayout,
    loadChildren: () =>
      import('@features/masterdata/masterdata.routes').then(
        (route) => route.masterdataRoutes
      ),
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
    component: PageNotFoundComponent,
  },
];
