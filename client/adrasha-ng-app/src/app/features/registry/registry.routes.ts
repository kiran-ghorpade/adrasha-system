import { Route } from '@angular/router';
import { RegistryPageComponent } from './registry-page/registry-page.component';
import { FamilyRegistrationFormComponent } from './family-registration-form/family-registration-form.component';
import { FamilyDetailsPageComponent } from './family-details-page/family-details-page.component';
import { MemberDetailsPageComponent } from './member-details-page/member-details-page.component';

export const registryRoutes: Route[] = [
  { path: '', component: RegistryPageComponent },
  { path: 'family/registration', component: FamilyRegistrationFormComponent },
  { path: 'family/:id', component: FamilyDetailsPageComponent },
  { path: 'member/:id', component: MemberDetailsPageComponent },
];
