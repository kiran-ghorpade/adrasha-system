import { Routes } from '@angular/router';
import { authRoutes } from '@features/auth';
import { LoginComponent } from '@features/auth/login/login.component';
import { PageNotFoundComponent } from '@shared/components/page-not-found/page-not-found.component';
// import { Error403Component } from '@core/errors/403.component';
// import { Error404Component } from '@core/errors/404.component';
// import { Error500Component } from '@core/errors/500.component';
// import { RegisterComponent } from '@features/auth/register/register.component';
// import { AdminLayoutComponent } from '@shared/layout/admin-layout/admin-layout.component';
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
    component: BlankLayoutComponent,
    loadChildren: () =>
      import('@features/role-request/role-request.routes').then(
        (route) => route.roleRequestRoutes
      ),
  },
  {
    path: '**',
    title: 'Not Found',
    component: PageNotFoundComponent,
  },
];
