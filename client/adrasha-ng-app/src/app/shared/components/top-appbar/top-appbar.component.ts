import { Component, computed, inject, OnInit } from '@angular/core';
// import { MatAvatarModule } from '@angular/material/avatar';
import { CommonModule } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { Router, RouterModule } from '@angular/router';
import { AuthService, ThemeService } from '@core/services';
import { AppLogoComponent } from '../../widgets/logo.component';

@Component({
  selector: 'app-top-appbar',
  templateUrl: './top-appbar.component.html',
  imports: [
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule,
    MatTooltipModule,
    MatListModule,
    AppLogoComponent,
    RouterModule,
    CommonModule,
  ],
  styles: `
     .iconShadow{
       box-shadow: var(--mat-sys-level3)
     }
  `,
})
export class TopAppBarComponent {
  private readonly authService = inject(AuthService);
  protected themeService = inject(ThemeService);
  readonly router = inject(Router);

  loggedIn = toSignal(this.authService.isLoggedIn$, { initialValue: false });

  isAdmin = toSignal(this.authService.isAdmin(), { initialValue: false });

  readonly settings = computed(() => {
    const base = [{ label: 'Settings', toLink: '/settings', icon: 'settings' }];
    // if (!this.isAdmin()) {
    //   base.unshift({ label: 'Profile', toLink: '/profile', icon: 'person' });
    // }
    return base;
  });

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}
