import { DatePipe } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AnalyticsFamilyPovertyPieChartComponent } from '@features/analytics/components/family/family-poverty-dist.component';
import { AnalyticsFamilyPovertyLineChartComponent } from '@features/analytics/components/family/family-povery-line.component';
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';

@Component({
  selector: 'app-analytics-poverty-page',
  standalone: true,
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    AnalyticsFamilyPovertyPieChartComponent,
    AnalyticsFamilyPovertyLineChartComponent,
    DatePipe,
  ],
  templateUrl: './analytics-poverty-page.component.html',
})
export class AnalyticsPovertyPageComponent {
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
