import { Component } from '@angular/core';
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
    TopAppBarComponent,
  ],
})
export class AppLayout {}
