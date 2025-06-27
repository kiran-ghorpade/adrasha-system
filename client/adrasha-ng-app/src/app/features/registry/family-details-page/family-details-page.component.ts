import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BackButtonComponent } from '../../../shared/components/back-button/back-button.component';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { PaperComponent } from '../../../core/components/paper/paper.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { CommonModule } from '@angular/common';
import { DataLabelComponent } from "../../../shared/components/data-label/data-label.component";
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { TopToolBar } from "../../../shared/components/top-toolbar/top-toolbar.component";

@Component({
  selector: 'app-family-details-page',
  imports: [
    MatToolbarModule,
    BackButtonComponent,
    MatCardModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    DataLabelComponent,
    PaperComponent,
    RouterModule,
    CommonModule,
    TopToolBar
],
  templateUrl: './family-details-page.component.html',
})
export class FamilyDetailsPageComponent {}
