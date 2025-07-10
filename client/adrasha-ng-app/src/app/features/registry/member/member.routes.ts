import { Routes } from '@angular/router';
import { MemberCreatePageComponent } from './pages/member-create-page/member-create-page.component';
import { MemberEditPageComponent } from './pages/member-edit-page/member-edit-page.component';
import { MemberPageComponent } from './pages/member-page/member-page.component';
import { MemberDetailsComponent } from './components';

export const memberRoutes: Routes = [
  { path: '', component: MemberPageComponent },
  { path: 'new', component: MemberCreatePageComponent },
  { path: 'update', component: MemberEditPageComponent },
  { path: ':id', component: MemberDetailsComponent },
];
