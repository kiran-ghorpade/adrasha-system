// github.service.ts
import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, shareReplay } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class GitHubService {
  private http = inject(HttpClient);
  private baseUrl = 'https://api.github.com';
  private username = 'kiran-ghorpade';
  private repo = 'adrasha-system';

  // Cache results using shareReplay
  user$ = this.http
    .get(`${this.baseUrl}/users/${this.username}`)
    .pipe(shareReplay(1));

  repo$ = this.http
    .get(`${this.baseUrl}/repos/${this.username}/${this.repo}`)
    .pipe(shareReplay(1));
    
  languages$ = this.http
    .get<Record<string, number>>(
      `${this.baseUrl}/repos/${this.username}/${this.repo}/languages`
    )
    .pipe(shareReplay(1));
}
