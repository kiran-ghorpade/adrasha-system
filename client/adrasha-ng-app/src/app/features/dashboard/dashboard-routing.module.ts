import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from '../../../app/features/dashboard/dashboard/dashboard.component';
import { RoleGuard } from '@core/guards/role.guard';
import { AdminDashboardComponent } from '../../../app/features/dashboard/components/admin-dashboard/admin-dashboard.component';

const routes: Routes = [
  {
      path: '/dashboard',
      component: DashboardComponent,
      canActivate: [RoleGuard],
      canActivateChild: [RoleGuard],
      children: [
        { path: '', component: AdminDashboardComponent }
      ],
    },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
