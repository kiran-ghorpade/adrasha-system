import { CommonModule } from '@angular/common';
import {
  Component,
  inject,
  OnInit,
  signal
} from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { ActivatedRoute, RouterModule } from '@angular/router';
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
export class AppLayout implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);

  isDashboard = signal(false);

  ngOnInit(): void {
    const routeUrl = this.activatedRoute.snapshot.url.join('/');
    this.isDashboard.set(routeUrl.includes('dashboard'));
  }
}
