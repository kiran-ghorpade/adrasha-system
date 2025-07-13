import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatGridListModule } from "@angular/material/grid-list";
import { MatIconModule } from "@angular/material/icon";
import { MatListModule } from "@angular/material/list";
import { MatToolbarModule } from "@angular/material/toolbar";
import { RouterModule } from "@angular/router";
import { DataLabelComponent, PageHeaderComponent, PageWrapperComponent } from "@shared/components";


@Component({
  selector: 'app-health-page',
  imports: [
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatGridListModule,
    MatButtonModule,
    RouterModule,
    CommonModule,
    DataLabelComponent,
    PageHeaderComponent,
    PageWrapperComponent
],
  templateUrl: './health-page.component.html',
})
export class HealthPageComponent {}
