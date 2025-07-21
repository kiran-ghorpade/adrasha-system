import { Routes } from '@angular/router';
import { NcdDetailsComponent } from './components';
import {
  NcdFormPageComponent,
  NcdPageComponent
} from './pages';

export const ncdRoutes: Routes = [
  { path: '', component: NcdPageComponent },
  { path: 'new', component: NcdFormPageComponent },
  { path: 'update/:id', component: NcdFormPageComponent },
  { path: ':id', component: NcdDetailsComponent },
];
