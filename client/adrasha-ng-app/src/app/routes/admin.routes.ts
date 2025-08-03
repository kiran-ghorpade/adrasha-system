import { Routes } from '@angular/router';
import { authGuard } from '@core/guards';
import { roleGuard } from '@core/guards/role.guard';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { AppLayout } from 'app/layout';



export const adminRoutes: Routes = [
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
];
