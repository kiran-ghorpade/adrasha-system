import { Routes } from '@angular/router';
import { authGuard } from '@core/guards';
import { roleGuard } from '@core/guards/role.guard';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { AppLayout } from 'app/layout';

export const ashaRoutes: Routes = [
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
    path: 'search',
    title: 'Search',
    // canActivate: [authGuard, roleGuard],
    data: {
      roles: [UserResponseDTORolesItem.ASHA],
    },
    component: AppLayout,
    loadChildren: () =>
      import('@features/search/search.routes').then(
        (route) => route.searchPageRoutes
      ),
  },
  {
    path: 'reports',
    title: 'Reports',
    // canActivate: [authGuard, roleGuard],
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
    path: 'analytics',
    title: 'Analytics',
    // canActivate: [authGuard, roleGuard],
    data: {
      roles: [UserResponseDTORolesItem.ASHA],
    },
    component: AppLayout,
    loadChildren: () =>
      import('@features/analytics/analytics.routes').then(
        (route) => route.analyticsRoutes
      ),
  },
];
