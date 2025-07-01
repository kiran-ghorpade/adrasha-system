import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { DataLabelComponent } from '../../../../shared/components/data-label/data-label.component';
import { PageHeaderComponent } from '../../../../shared/components/page-header/page-header.component';

@Component({
  selector: 'app-family-details-page',
  imports: [
    MatToolbarModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    DataLabelComponent,
    RouterModule,
    CommonModule,
    PageHeaderComponent,
  ],
  templateUrl: './family-details-page.component.html',
})
export class FamilyDetailsPageComponent {}
