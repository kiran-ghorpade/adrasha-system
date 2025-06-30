import { Route } from '@angular/router';
import { SearchMemberComponent } from './search-member/search-member.component';
import { SearchPageComponent } from './search-page/search-page.component';

export const searchPageRoutes: Route[] = [
  { path: '', component: SearchPageComponent },
];
