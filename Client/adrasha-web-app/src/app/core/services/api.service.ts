import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '@env/environment';
import { TokenService } from '@core/services';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private readonly BASE_URL = environment.apiBaseUrl;
  private readonly tokenService = inject(TokenService);

  constructor(private http: HttpClient) {}

  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.BASE_URL}/${endpoint}`, {
      headers: this.getHeaders()
    });
  }

  post<T>(endpoint: string, body: any): Observable<T> {
    return this.http.post<T>(`${this.BASE_URL}/${endpoint}`, body, {
      headers: this.getHeaders()
    });
  }

  put<T>(endpoint: string, body: any): Observable<T> {
    return this.http.put<T>(`${this.BASE_URL}/${endpoint}`, body, {
      headers: this.getHeaders()
    });
  }

  delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.BASE_URL}/${endpoint}`, {
      headers: this.getHeaders()
    });
  }

  private getHeaders(): HttpHeaders {
    const token = this.tokenService.getBearerToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `${token}` } : {})
    });
  }
}
