import { Routes } from '@angular/router';
import {
    NcdDetailsComponent
} from './components';
import {
    NcdCreatePageComponent,
    NcdEditPageComponent,
    NcdPageComponent
} from './pages';

export const ncdRoutes: Routes = [
  { path: '', component: NcdPageComponent },
  { path: 'new', component: NcdCreatePageComponent },
  { path: 'update', component: NcdEditPageComponent },
  { path: ':id', component: NcdDetailsComponent },
];
