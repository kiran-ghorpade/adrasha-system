import { Routes } from '@angular/router';
import { ReportsPageComponent, ReportViewPageComponent } from './pages';

export const reportsRoutes: Routes = [
  { path: '', component: ReportsPageComponent },
  { path: ':type', component: ReportViewPageComponent },
];
