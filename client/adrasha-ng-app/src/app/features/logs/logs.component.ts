import { CommonModule, DatePipe, JsonPipe } from '@angular/common';
import {
  Component,
  forwardRef,
  inject,
  OnDestroy,
  OnInit,
  signal,
} from '@angular/core';
import {
  MAT_BOTTOM_SHEET_DATA,
  MatBottomSheet,
  MatBottomSheetRef,
} from '@angular/material/bottom-sheet';
import { MatButton, MatButtonModule } from '@angular/material/button';
import { MatChip } from '@angular/material/chips';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatIcon } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { PageHeaderComponent } from '@shared/components';
import { PageWrapperComponent } from '@shared/components/page-wrapper/page-wrapper.component';
import { LogItem, LogService } from '@shared/services';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-logs',
  imports: [
    PageWrapperComponent,
    MatListModule,
    MatIcon,
    MatButton,
    DatePipe,
    MatChip,
    PageHeaderComponent,
  ],
  template: `<app-page-wrapper>
    <div top><app-page-header title="Logs" icon="terminal"/></div>
    <div content class="h-full w-full">
      <!-- Sidebar: Logs List -->
      <button matButton (click)="clearLogs()">clear</button>

      <mat-action-list>
        @for (log of logs(); track $index) {
        <mat-list-item
          (click)="onClick($index)"
          class="cursor-pointer hover:bg-gray-100"
        >
          <div matListItemIcon>
            @switch (log.status) { @case ('info') {
            <mat-icon class="text-blue-500"> info</mat-icon>
            } @case ('success') {
            <mat-icon class="text-green-500"> check_circle</mat-icon>
            } @case ('failure') {
            <mat-icon class="text-red-500"> error</mat-icon>
            } }
          </div>
          <div matListItemTitle class="truncate">
            {{ log.message }}
          </div>
          <div matListItemLine class="text-sm text-gray-500">
            {{ log.status }}
          </div>
          <div matListItemMeta class="text-sm text-gray-500">
            @if (!log.isViewed) {
            <mat-chip class="ml-2 text-red-500">
              <div class="text-blue-500">new</div>
            </mat-chip>
            }
          </div>
          <div matListItemMeta class="text-xs text-gray-400">
            {{ log.timestamp | date : 'HH:mm:ss' }}
          </div>
        </mat-list-item>
        }
      </mat-action-list>
    </div>
  </app-page-wrapper> `,
})
export class LogsComponent implements OnInit, OnDestroy {
  private readonly logService = inject(LogService);
  private _bottomSheet = inject(MatDialog);
  private subscription: Subscription | null = null;

  logs = signal<LogItem[]>([]);
  selectedLog = signal<LogItem | null>(null);

  private refreshIntervalId: any;

  ngOnInit(): void {
    this.subscription = this.logService.logs$.subscribe((list) => {
      this.logs.set(list.reverse());
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) this.subscription.unsubscribe();
    if (this.refreshIntervalId) {
      clearInterval(this.refreshIntervalId);
    }
  }
  close() {
    this.selectedLog.set(null);
  }

  onClick(index: number) {
    const log = this.logs()[index];
    if (log) {
      log.isViewed = true;
      this.logService.update(index, log);
      this.selectedLog.set(log);
      this._bottomSheet.open(BottomSheet, {
        data: log,
      });
    }
  }

  clearLogs() {
    this.logService.clear();
  }
}

@Component({
  selector: 'app-bottom-sheet',
  template: ` <section class="overflow-scroll p-2 flex flex-col items-center">
    <h3 matDialogTitle>Log Details</h3>
    <div matDialogContent>
      <pre class="w-full text-sm bg-gray-100 text-balance">{{
        data | json
      }}</pre>
    </div>
  </section>`,
  imports: [
    MatListModule,
    CommonModule,
    JsonPipe,
    MatButtonModule,
    MatDialogModule,
  ],
})
export class BottomSheet {
  public data: LogItem = inject(MAT_DIALOG_DATA);
}
