import { Routes } from '@angular/router';
import {
  FamilyCreatePageComponent,
  FamilyEditPageComponent,
  FamilyPageComponent,
} from './pages';
import { FamilyDetailsComponent } from './components';

export const familyRoutes: Routes = [
  { path: '', component: FamilyPageComponent },
  { path: 'new', component: FamilyCreatePageComponent },
  { path: 'update', component: FamilyEditPageComponent },
  { path: ':id', component: FamilyDetailsComponent },
];
