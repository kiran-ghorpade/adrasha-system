import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { AuthenticationService } from '@core/api/authentication/authentication.service';
import { LoginRequest } from '@core/model/authService';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  protected readonly http = inject(HttpClient);
  protected readonly translate = inject(TranslateService);
  protected readonly auth = inject(AuthenticationService);

  private readonly baseUrl = 'auth';


  login(loginRequest: LoginRequest) {
    return this.auth.loginUser(loginRequest);
  }

  // refresh(params: Record<string, any>) {
  //   return this.auth.post<Token>(`${this.baseUrl}/refresh`, params);
  // }

  // logout() {
  //   return this.auth.post<any>(`${this.baseUrl}/logout`, {});
  // }
}
