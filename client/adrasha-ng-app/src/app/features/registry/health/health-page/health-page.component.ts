import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { PageHeaderComponent } from '@shared/components';
import { DataLabelComponent } from '@shared/components/data-label/data-label.component';
import { PageWrapperComponent } from '@shared/components/page-wrapper/page-wrapper.component';

@Component({
  selector: 'app-health-page',
  imports: [
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    DataLabelComponent,
    RouterModule,
    CommonModule,
    PageHeaderComponent,
    PageWrapperComponent
],
  templateUrl: './health-page.component.html',
})
export class HealthPageComponent {}
