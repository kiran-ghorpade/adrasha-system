import { Routes } from '@angular/router';
import { MemberDetailsPageComponent, MemberFormPageComponent, MemberPageComponent } from './pages';

export const memberRoutes: Routes = [
  { path: '', component: MemberPageComponent },
  { path: 'new', component: MemberFormPageComponent },
  { path: 'update/:id', component: MemberFormPageComponent },
  { path: ':id', component: MemberDetailsPageComponent },
];
