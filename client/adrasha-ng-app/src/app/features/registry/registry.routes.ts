import { Route } from '@angular/router';
import { RegistryPageComponent } from './registry-page/registry-page.component';
import { MemberDetailsPageComponent } from './member';
import {
  FamilyDetailsPageComponent,
  FamilyRegistrationFormComponent,
} from './family';
import { HealthDetailsPageComponent } from './health/health-details-page/health-details-page.component';
import { HealthRecordsPageComponent } from './health/health-records-page/health-records-page.component';
import { MemberRegistrationFormComponent } from './member/member-registration-form/member-registration-form.component';
import { HealthRecordFormComponent } from './health/health-record-form/health-record-form.component';

export const registryRoutes: Route[] = [
  { path: '', component: RegistryPageComponent },
  { path: 'family/new', component: FamilyRegistrationFormComponent },
  { path: 'family/:id', component: FamilyDetailsPageComponent },
  { path: 'member/new', component: MemberRegistrationFormComponent },
  { path: 'member/:id', component: MemberDetailsPageComponent },
  { path: 'health/records', component: HealthRecordsPageComponent },
  { path: 'health/records/new', component: HealthRecordFormComponent },
  { path: 'health/:id', component: HealthDetailsPageComponent },
];
