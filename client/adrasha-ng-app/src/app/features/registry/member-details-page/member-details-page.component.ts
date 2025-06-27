import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { PaperComponent } from '@core/components/paper/paper.component';
import { BackButtonComponent } from '@shared/components/back-button/back-button.component';
import { DataLabelComponent } from '@shared/components/data-label/data-label.component';
import { TopToolBar } from "../../../shared/components/top-toolbar/top-toolbar.component";

@Component({
  selector: 'app-member-details-page',
  imports: [
    MatToolbarModule,
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
  templateUrl: './member-details-page.component.html',
})
export class MemberDetailsPageComponent {}
