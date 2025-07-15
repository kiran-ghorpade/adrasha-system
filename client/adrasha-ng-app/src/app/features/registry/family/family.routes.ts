import { Routes } from '@angular/router';
import {
  FamilyCreatePageComponent,
  FamilyDetailsPageComponent,
  FamilyEditPageComponent,
  FamilyPageComponent,
} from './pages';

export const familyRoutes: Routes = [
  { path: '', component: FamilyPageComponent },
  { path: 'new', component: FamilyCreatePageComponent },
  { path: 'update/:id', component: FamilyEditPageComponent },
  { path: ':id', component: FamilyDetailsPageComponent },
];
