import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { Menu } from '@core/constants';
import { MenuService } from '@core/services/menu.service';

@Component({
  selector: 'app-side-navbar',
  imports: [
    MatSidenavModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    RouterModule,
    MatButtonModule,
    CommonModule,
  ],
  templateUrl: './sidebar.component.html',
  styles: `
  .active-link {
      background-color: var(--mat-sys-primary);
      color: var(--mat-sys-on-primary);
    }
  `,
})
export class SideNavBar implements OnInit {
  private readonly menuService = inject(MenuService);

  menuList = signal<Menu[]>([]);

  ngOnInit() {
    this.menuService.menu().subscribe((menu) => {
      this.menuList.set(menu);
    });
  }
}
