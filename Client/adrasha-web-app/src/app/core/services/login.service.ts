import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { environment } from '@env/environment';
import { TranslateService } from '@ngx-translate/core';
import { Token, User } from '../models';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  protected readonly http = inject(HttpClient);
  protected readonly translate = inject(TranslateService);

  private readonly baseUrl = environment.apiBaseUrl+'/auth';

  login(username: string, password: string, rememberMe = false) {
    return this.http.post<Token>(`${this.baseUrl}/login`, { username, password, rememberMe });
  }

  refresh(params: Record<string, any>) {
    return this.http.post<Token>(`${this.baseUrl}/refresh`, params);
  }

  logout() {
    return this.http.post<any>(`${this.baseUrl}/logout`, {});
  }

  user() {
    return this.http.get<User>('/user');
  }
}
