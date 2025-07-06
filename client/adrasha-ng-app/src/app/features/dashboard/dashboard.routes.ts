import { Route } from '@angular/router';
import { DashboardComponent } from '../dashboard/dashboard/dashboard.component';

export const dashboardRoutes: Route[] = [
  {
    path: '',
    component: DashboardComponent,
    // canActivate: [RoleGuard],
    // canActivateChild: [RoleGuard],
    // children: [
    //   { path: '', component: DashboardComponent }
    // ],
  },
];
