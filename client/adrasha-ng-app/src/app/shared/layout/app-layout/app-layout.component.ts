import { CommonModule } from '@angular/common';
import { Component, ViewEncapsulation } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { RouterModule } from '@angular/router';
import { BottomNavBarComponent } from '@shared/components/bottom-nav-bar/bottom-nav-bar.component';
import { SideNavBar } from '@shared/components/sidebar/sidebar.component';
import { TopAppBarComponent } from '@shared/components/top-appbar/top-appbar.component';

@Component({
  selector: 'app-layout',
  templateUrl: './app-layout.component.html',
  imports: [
    RouterModule,
    BottomNavBarComponent,
    SideNavBar,
    MatCardModule,
    TopAppBarComponent,
    CommonModule,
  ],
  styles: `@use '@angular/material' as mat;`,
})
export class AppLayout {}
