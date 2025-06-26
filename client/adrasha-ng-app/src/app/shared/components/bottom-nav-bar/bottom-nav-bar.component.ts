import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { Menu, MenuService } from '@core/services/menu.service';


const menuList: Menu[] = [
  {
    label: 'Dashboard',
    icon: 'dashboard',
    to: '/dashboard',
  },
  {
    label: 'Registry',
    icon: 'app_registration',
    to: '/registry',
  },
  {
    label: 'Search',
    icon: 'search',
    to: '/search',
  },
  {
    label: 'Analytics',
    icon: 'show_chart',
    to: '/anaytics',
  },
  {
    label: 'Reports',
    icon: 'picture_as_pdf',
    to: '/reports',
  },
];

@Component({
  selector: 'app-bottom-navbar',
    imports: [
    MatSidenavModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    RouterModule,
    CommonModule,
  ],
  templateUrl: './bottom-nav-bar.component.html',
})
export class BottomNavBarComponent {
  menuList: Menu[] = menuList;

  constructor(private menuService: MenuService) {}

  ngOnInit() {
    // this.menuList.set(menuList);
  }
}
