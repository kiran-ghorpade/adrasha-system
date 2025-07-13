import { Routes } from '@angular/router';
import { authGuard } from '@core/guards';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { AppLayout, AuthLayoutComponent, BlankLayoutComponent } from './layout';
import { PageNotFoundComponent } from '@shared/components';
import { LogsComponent } from '@features/logs';

export const routes: Routes = [
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
    component: AppLayout,
    loadChildren: () =>
      import('@features/profile/profile.routes').then(
        (route) => route.profileRoutes
      ),
  },
  {
    path: 'dashboard',
    title: 'Dashboard',
    // canActivate: [authGuard],
    data: {
      roles: [UserResponseDTORolesItem.ADMIN, UserResponseDTORolesItem.ASHA],
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
    component: AppLayout,
    loadChildren: () =>
      import('@features/registry/registry.routes').then(
        (route) => route.registryRoutes
      ),
  },
  {
    path: 'search',
    title: 'Search',
    component: AppLayout,
    loadChildren: () =>
      import('@features/search/search.routes').then(
        (route) => route.searchPageRoutes
      ),
  },
  {
    path: 'role-requests',
    title: 'Role Request',
    component: AppLayout,
    pathMatch: 'full',
    loadChildren: () =>
      import('@features/role-request/role-request.routes').then(
        (route) => route.roleRequestRoutes
      ),
  },
  {
    path: 'masterdata',
    title: 'Masterdata',
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
    path: '**',
    title: 'Not Found',
    component: PageNotFoundComponent,
  },
];
