import { Routes } from '@angular/router';
import {
  AnalyticsAgePageComponent,
  AnalyticsAliveStatusPageComponent,
  AnalyticsGenderPageComponent,
  AnalyticsNCDPageComponent,
  AnalyticsPageComponent,
  AnalyticsPovertyPageComponent,
} from './pages';

export const analyticsRoutes: Routes = [
  { path: '', component: AnalyticsPageComponent },
  { path: 'age', component: AnalyticsAgePageComponent },
  { path: 'gender', component: AnalyticsGenderPageComponent },
  { path: 'poverty', component: AnalyticsPovertyPageComponent },
  { path: 'aliveStatus', component: AnalyticsAliveStatusPageComponent },
  { path: 'ncd', component: AnalyticsNCDPageComponent },
];
