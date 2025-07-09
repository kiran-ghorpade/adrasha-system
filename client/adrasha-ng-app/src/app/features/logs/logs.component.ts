import { DatePipe, JsonPipe } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIcon } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { PageWrapperComponent } from '@shared/components/page-wrapper/page-wrapper.component';
import { LogItem, LogService } from '@shared/services';

@Component({
  selector: 'app-logs',
  imports: [
    PageWrapperComponent,
    MatListModule,
    MatIcon,
    MatButton,
    DatePipe,
    JsonPipe,
  ],
  template: `<app-page-wrapper>
    <div content class="h-full w-full flex flex-col lg:flex-row">
      <!-- Sidebar: Logs List -->
      <div
        class="w-full lg:w-1/3 border-r border-gray-300 max-h-[60vh] lg:max-h-full overflow-auto"
      >
        <button matButton (click)="clearLogs()">clear</button>

        <mat-action-list>
          @for (log of logs(); track $index) {
          <mat-list-item
            (click)="onClick($index)"
            class="cursor-pointer hover:bg-gray-100"
          >
            <div matListItemIcon>
              <mat-icon>
                @switch (log.status) { @case ('info') { info } @case ('success')
                { check_circle } @case ('failure') { error } }
              </mat-icon>
            </div>
            <div matListItemTitle class="truncate">{{ log.message }}</div>
            <div matListItemTitle class="text-sm text-gray-500">
              {{ log.status }}
            </div>
            <div matListItemMeta class="text-xs text-gray-400">
              {{ log.timestamp | date : 'medium' }}
            </div>
          </mat-list-item>
          }
        </mat-action-list>
      </div>

      <!-- Details Panel -->
      @if (selectedLog()) {
      <section
        class="w-full lg:w-2/3 paper flex flex-col items-center p-4 overflow-auto"
      >
        <button matButton (click)="onClick(-1)">close</button>
        <h3 class="text-lg font-semibold mb-4">Log Details</h3>
        <pre class="w-full text-sm bg-gray-100 p-4 rounded overflow-auto">{{
          selectedLog() | json
        }}</pre>
      </section>
      }
    </div>
  </app-page-wrapper> `,
})
export class LogsComponent implements OnInit {
  private readonly logService = inject(LogService);
  private matDilog = inject(MatDialog);

  logs = signal<LogItem[]>([]);
  selectedLog = signal<LogItem | null>(null);

  ngOnInit(): void {
    this.loadLogs();
  }

  loadLogs() {
    this.logs.set(this.logService.logs);
  }

  onClick(index: number) {
    this.selectedLog.set(this.logs()[index]);
  }

  clearLogs() {
    this.logService.clear();
    this.loadLogs();
  }
}
