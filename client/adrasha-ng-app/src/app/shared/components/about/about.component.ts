import { DatePipe } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { GitHubService } from '@core/services/github.service';
import { ChartDataset } from 'chart.js';
import { PieChartComponent } from '../pie-chart/pie-chart.component';

type AboutAppType = {
  name: string;
  author: string;
};

@Component({
  selector: 'app-about',
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    DatePipe,
    PieChartComponent,
  ],
  template: `
    <mat-dialog-content>
      <h4>App Name : ADRASHA ( Alternative Digital Registry for ASHA )</h4>
      <h4>Version: <i>prototype Snapshot 1.0.0</i></h4>
      <h4>Author Name: Kiran Ghorpade</h4>
      <h4>Description: Application using Angular, SpringBoot and MySQL</h4>
      <h4>
        Dependencies For Frontend:
        <ul>
          <li>angular</li>
          <li>angular/material</li>
          <li>tailwindcss</li>
          <li>chart.js</li>
          <li>orval</li>
          <li>ngx-translate</li>
          <li>ng-qrcode</li>
          <li>ngx-print</li>
          <li>axios</li>
          <li>base64-js</li>
        </ul>
      </h4>
      @if(githubUser() && githubRepo() && githubLanguages()){
      <hr />
      <ng-container>
        <h4>👤 {{ githubUser().name }} ({{ githubUser().login }})</h4>
        <p>
          Followers: {{ githubUser().followers }} | Following:
          {{ githubUser().following }}
        </p>
        <p>On GitHub Since: {{ githubUser().created_at | date }}</p>

        <h4>📦 Repo: {{ githubRepo().name }}</h4>
        <p>{{ githubRepo().description }}</p>
        <p>
          ⭐ Stars: {{ githubRepo().stargazers_count }} | 🍴 Forks:
          {{ githubRepo().forks_count }}
        </p>
        <p>Created: {{ githubRepo().created_at | date }}</p>
        <p>Updated: {{ githubRepo().updated_at | date }}</p>

        <h4>🧠 Languages Used:</h4>
        @if(githubLanguagesKeys().length > 0){
        <ng-container >
            <app-pie-chart
              [labels]="githubLanguagesKeys()"
              [data]="githubLanguagesValues()"
            />
        </ng-container>
        }
      </ng-container>
      }
    </mat-dialog-content>

    <mat-dialog-actions>
      <button mat-button mat-dialog-close cdkFocusInitial>Close</button>
      <!-- <a
        mat-icon-button
        href="https://github.com/kiran-ghorpade/adrasha-system"
        target="_blank"
        aria-label="GitHub"
      >
        <mat-icon >
          <img src="https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.svg">
        </mat-icon>
      </a> -->
    </mat-dialog-actions>
  `,
})
export class AboutComponent implements OnInit {
  private github = inject(GitHubService);

  githubUser = signal<any>(null);
  githubRepo = signal<any>(null);
  githubLanguages = signal<Record<string, number>>({});
  githubLanguagesKeys = signal<string[]>([]);
  githubLanguagesValues = signal<number[]>([]);

  ngOnInit() {
    this.github.user$.subscribe((user) => {
      this.githubUser.set(user);
    });

    this.github.repo$.subscribe((repo) => {
      this.githubRepo.set(repo);
    });

    this.setLanguageChartData();
  }

  setLanguageChartData() {
    this.github.languages$.subscribe((langs) => {
      this.githubLanguages.set(langs);

      const originalKeys = Object.keys(langs);
      const values = Object.values(langs);
      const total = values.reduce((acc, val) => acc + val, 0);

      const percentageData = values.map((val) =>
        parseFloat(((val / total) * 100).toFixed(2))
      );

      // Set the display keys with sizes in KB
      this.githubLanguagesKeys.set(
        originalKeys.map(
          (key) => `${key} : ${(langs[key] / 1024).toFixed(2)} kb`
        )
      );

      this.githubLanguagesValues.set(percentageData);
    });
  }
}
