import { CommonModule } from '@angular/common';
import { Component, inject, signal, WritableSignal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { Menu } from '@core/constants';
import { MenuService } from '@core/services/menu.service';

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
  private readonly menuService = inject(MenuService);

  menuList: WritableSignal<Menu[]> = signal([]);

  ngOnInit() {
    this.menuService.menu().subscribe((menu) => {
      this.menuList.set(menu);
    });
  }
}
