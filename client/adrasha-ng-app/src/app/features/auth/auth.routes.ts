import { Route } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

export const authRoutes: Route[] = [
  { path: 'login', title: 'Login', component: LoginComponent },
  { path: 'register', title: 'Registration', component: RegisterComponent },
];
