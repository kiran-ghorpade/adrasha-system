import { Injectable } from '@angular/core';
import { BehaviorSubject, share } from 'rxjs';

export interface Menu {
  label: string; // background color
  icon: string;
  to:string;
}


@Injectable({
  providedIn: 'root',
})
export class MenuService {
  private readonly menu$ = new BehaviorSubject<Menu[]>([]);

  /** Get all the menu data. */
  getAll() {
    return this.menu$.asObservable();
  }

  /** Observe the change of menu data. */
  change() {
    return this.menu$.pipe(share());
  }

  /** Initialize the menu data. */
  set(menu: Menu[]) {
    // this.menu$.next(menu);
    // return this.menu$.asObservable();

    // return menuList;
  }

  /** Add one item to the menu data. */
  add(menu: Menu) {
    const tmpMenu = this.menu$.value;
    tmpMenu.push(menu);
    this.menu$.next(tmpMenu);
  }

  /** Reset the menu data. */
  reset() {
    this.menu$.next([]);
  }
}
