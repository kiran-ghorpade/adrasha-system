import { CommonModule } from '@angular/common';
import { Component, inject, signal, WritableSignal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { Menu } from '@core/constants';
import { MenuService } from '@core/services/menu.service';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-bottom-navbar',
  imports: [
    MatSidenavModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    RouterModule,
    CommonModule,
    TranslateModule
  ],
  templateUrl: './bottom-nav-bar.component.html',
  styles: `
  .active-link {
      background-color: var(--mat-sys-primary);
      color: var(--mat-sys-on-primary);
    }
  `,
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
