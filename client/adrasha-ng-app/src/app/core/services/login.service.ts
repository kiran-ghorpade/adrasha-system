import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { environment } from '@env/environment';
import { TranslateService } from '@ngx-translate/core';
import { Token, User } from '../models';
import { AuthenticationManagementService } from '@core/api/authentication-management/authentication-management.service';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  protected readonly http = inject(HttpClient);
  protected readonly translate = inject(TranslateService);
  protected readonly auth = inject(AuthenticationManagementService);

  private readonly baseUrl = 'auth';

  login(username: string, password: string) {
    return this.auth.post<Token>(`${this.baseUrl}/login`, { username, password });
  }

  refresh(params: Record<string, any>) {
    return this.auth.post<Token>(`${this.baseUrl}/refresh`, params);
  }

  logout() {
    return this.auth.post<any>(`${this.baseUrl}/logout`, {});
  }

  user() {
    return this.auth.get<User>(`${this.baseUrl}/user`);
  }
}
