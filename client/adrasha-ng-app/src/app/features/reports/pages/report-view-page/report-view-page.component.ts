import { Component, effect, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute } from '@angular/router';
import { ReportsService } from '@core/api';
import { AuthService } from '@core/services';
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';
import { map, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-report-view-page',
  standalone: true,
  imports: [
    MatButtonModule,
    MatIconModule,
    PageWrapperComponent,
    PageHeaderComponent,
  ],
  templateUrl: './report-view-page.component.html',
})
export class ReportViewPageComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly authService = inject(AuthService);
  private readonly reportService = inject(ReportsService);

  type = toSignal(this.route.paramMap.pipe(map((p) => p.get('type'))));
  pdfUrl = signal<string | null>(null);

  constructor() {
    effect(() => {
      const type = this.type();
      if (!type) {
        this.pdfUrl.set(null);
        return;
      }

      this.authService.currentUser
        .pipe(
          map((user) => user?.id),
          switchMap((id) => {
            if (!id) return of(null);

            switch (type) {
              case 'family':
                return this.reportService.generateFamilyReport(
                  { ashaId: id },
                  { responseType: 'blob' as const }
                );
              case 'health':
                return this.reportService.generateHealthRecordsReport(
                  { ashaId: id },
                  { responseType: 'blob' as const }
                );
              case 'member':
                return this.reportService.generateMemberReport(
                  { ashaId: id },
                  { responseType: 'blob' as const }
                );
              default:
                return of(null);
            }
          })
        )
        .subscribe((blob) => {
          if (!blob) {
            this.pdfUrl.set(null);
            return;
          }

          const url = URL.createObjectURL(blob);
          this.pdfUrl.set(url);
        });
    });
  }
}
