import { DatePipe } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AnalyticsHealthNCDChartComponent } from "@features/analytics/components/health/health-ncd-dist.component";
import { AnalyticsHealthNCDLineChartComponent } from "@features/analytics/components/health/health-ncd-line.component";
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';

@Component({
  selector: 'app-analytics-ncd-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    DatePipe,
    AnalyticsHealthNCDChartComponent,
    AnalyticsHealthNCDLineChartComponent,
  ],
  templateUrl: './analytics-ncd-page.component.html',
})
export class AnalyticsNCDPageComponent {
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
