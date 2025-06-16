// import { HttpClient } from '@angular/common/http';
// import { inject, Injectable } from '@angular/core';

// import { environment } from '@env/environment';
// import { TranslateService } from '@ngx-translate/core';
// import { Token, User } from '../models';
// import { ApiService } from './api.service';

// @Injectable({
//   providedIn: 'root',
// })
// export class LoginService {
//   protected readonly http = inject(HttpClient);
//   protected readonly translate = inject(TranslateService);
//   protected readonly apiService = inject(ApiService);

//   private readonly baseUrl = 'auth';

//   login(username: string, password: string) {
//     return this.apiService.post<Token>(`${this.baseUrl}/login`, { username, password });
//   }

//   refresh(params: Record<string, any>) {
//     return this.apiService.post<Token>(`${this.baseUrl}/refresh`, params);
//   }

//   logout() {
//     return this.apiService.post<any>(`${this.baseUrl}/logout`, {});
//   }

//   user() {
//     return this.apiService.get<User>(`${this.baseUrl}/user`);
//   }
// }
