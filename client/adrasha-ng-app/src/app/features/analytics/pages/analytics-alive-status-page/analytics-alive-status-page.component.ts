import { DatePipe } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AnalyticsMemberAliveStatusChartComponent } from "@features/analytics/components/member/member-alive-status-dist.component";
import { AnalyticsMemberAliveStatusLineChartComponent } from "@features/analytics/components/member/member-alive-status-line.component";
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';

@Component({
  selector: 'app-analytics-alive-status-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    AnalyticsMemberAliveStatusChartComponent,
    AnalyticsMemberAliveStatusLineChartComponent,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    DatePipe,
  ],
  templateUrl: './analytics-alive-status-page.component.html',
})
export class AnalyticsAliveStatusPageComponent {
  today = new Date();
  todayString = this.today.toISOString().split('T')[0];

  // Reactive form for filtering
  dateForm: FormGroup;

  searchStartDate = signal<Date>(this.today);
  searchEndDate = signal<Date>(this.today);

  constructor(private fb: FormBuilder) {
    this.dateForm = this.fb.group({
      startDate: [this.todayString],
      endDate: [this.todayString],
    });

    // Subscribe to changes and update signals
    this.dateForm.valueChanges.subscribe(({ startDate, endDate }) => {
      if (startDate) this.searchStartDate.set(new Date(startDate));
      if (endDate) this.searchEndDate.set(new Date(endDate));
    });
  }
}
