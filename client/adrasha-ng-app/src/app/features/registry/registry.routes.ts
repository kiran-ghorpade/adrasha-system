import { Route } from '@angular/router';
import {
  FamilyDetailsPageComponent,
  FamilyRegistrationFormComponent,
} from './family';
import { HealthDetailsPageComponent } from './health/health-details-page/health-details-page.component';
import { HealthRecordFormComponent } from './health/health-record-form/health-record-form.component';
import { HealthRecordsPageComponent } from './health/health-records-page/health-records-page.component';
import { MemberDetailsPageComponent } from './member';
import { MemberRegistrationPageComponent } from './member/member-registration-page/member-registration-page.component';
import { MemberUpdatePageComponent } from './member/member-update-page/member-update-page.component';
import { RegistryPageComponent } from './registry-page/registry-page.component';

export const registryRoutes: Route[] = [
  { path: '', component: RegistryPageComponent },
  { path: 'family/new', component: FamilyRegistrationFormComponent },
  { path: 'family/:id', component: FamilyDetailsPageComponent },
  { path: 'member/new', component: MemberRegistrationPageComponent },
  { path: 'member/update', component: MemberUpdatePageComponent },
  { path: 'member/:id', component: MemberDetailsPageComponent },
  { path: 'health/records', component: HealthRecordsPageComponent },
  { path: 'health/records/new', component: HealthRecordFormComponent },
  { path: 'health/:id', component: HealthDetailsPageComponent },
];
