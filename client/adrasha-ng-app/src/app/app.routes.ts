import { Routes } from '@angular/router';
import { AuthGuard } from '@core/guards';
import { RoleGuard } from '@core/guards/role.guard';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { PageNotFoundComponent } from '@shared/components/page-not-found/page-not-found.component';
import { AppLayout } from '@shared/layout/app-layout/app-layout.component';
import { AuthLayoutComponent } from '@shared/layout/auth-layout/auth-layout.component';
import { BlankLayoutComponent } from '@shared/layout/blank-layout/blank-layout.component';

export const routes: Routes = [
  {
    path: 'auth',
    title: 'Authentication',
    component: AuthLayoutComponent,
    loadChildren: () =>
      import('@features/auth/auth.routes').then((route) => route.authRoutes),
  },
  {
    path: 'dashboard',
    title: 'Dashboard',
    // canActivate: [AuthGuard, RoleGuard],
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
    path: 'role-request',
    title: 'Role Request',
    component: AppLayout,
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
    path: '**',
    title: 'Not Found',
    component: PageNotFoundComponent,
  },
];
