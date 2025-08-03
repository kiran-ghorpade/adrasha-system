
import { Routes } from '@angular/router';
import { authGuard } from '@core/guards';
import { roleGuard } from '@core/guards/role.guard';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { AppLayout } from 'app/layout';



export const commonRoutes: Routes = [
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
];
