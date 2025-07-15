import { inject, Injectable } from '@angular/core';
import { Menu, SIDEBAR_MENUS } from '@core/constants';
import { UserResponseDTORolesItem } from '@core/model/userService';
import { BehaviorSubject, map, Observable, of } from 'rxjs';
import { AuthService } from '.';

@Injectable({
  providedIn: 'root',
})
export class MenuService {
  private readonly authService = inject(AuthService);

  private menuList: Menu[] = [];

  constructor() {
    this.authService
      .currentUser
      .pipe(map((user) => user?.roles))
      .subscribe((roles) => {
        this.getMenuList(roles);
      });
  }

  private getMenuList(roles: string[] = []) {
    if (roles?.includes(UserResponseDTORolesItem.ADMIN)) {
      this.menuList = SIDEBAR_MENUS.admin;
    } else if (roles?.includes(UserResponseDTORolesItem.ASHA)) {
      this.menuList = SIDEBAR_MENUS.asha;
    } else {
      this.menuList = SIDEBAR_MENUS.user;
    }
  }

  menu(): Observable<Menu[]> {
    return of(this.menuList);
  }
}
