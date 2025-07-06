import { CommonModule } from '@angular/common';
import {
  Component,
  inject,
  OnInit,
  signal
} from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { Menu } from '@core/constants';
import { MenuService } from '@core/services/menu.service';

@Component({
  selector: 'app-side-navbar',
  templateUrl: './sidebar.component.html',
  standalone: true,
  imports: [
    MatSidenavModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    RouterModule,
    CommonModule,
  ],
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
