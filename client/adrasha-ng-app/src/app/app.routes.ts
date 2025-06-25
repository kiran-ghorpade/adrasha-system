import { Routes } from '@angular/router';
// import { Error403Component } from '@core/errors/403.component';
// import { Error404Component } from '@core/errors/404.component';
// import { Error500Component } from '@core/errors/500.component';
import { LoginComponent } from '@features/auth/login/login.component';
// import { RegisterComponent } from '@features/auth/register/register.component';
// import { AdminLayoutComponent } from '@shared/layout/admin-layout/admin-layout.component';
import { AuthLayoutComponent } from '@shared/layout/auth-layout/auth-layout.component';
import { AppComponent } from './app.component';

export const routes: Routes = [
  // {
  //   path: '',
  //   // component: AdminLayoutComponent,
  //   canActivate: [authGuard],
  //   canActivateChild: [authGuard],
  //   children: [
  //     { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  //     { path: 'dashboard', component: DashboardComponent },
  //     // { path: '403', component: Error403Component },
  //     // { path: '404', component: Error404Component },
  //     // { path: '500', component: Error500Component },
  //   ],
  // },
  // {
  //   path: 'auth',
  //   component: AuthLayoutComponent,
  //   children: [
  //     { path: 'login', component: LoginComponent },
  //     // { path: 'register', component: RegisterComponent },
  //   ],
  // },
  { path: '', component: AppComponent },
];
