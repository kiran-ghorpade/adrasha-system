import { Component, OnInit, AfterViewInit, inject } from '@angular/core';
import { PreloaderService, SettingsService } from '@core/services';
import { RouterOutlet } from '@angular/router';
import { CoreModule } from '@core/core.module';

@Component({
  selector: 'app-root',
  template: `<router-outlet />`,
  imports: [RouterOutlet, CoreModule],
})
export class AppComponent implements OnInit, AfterViewInit {
  private readonly preloader = inject(PreloaderService);
  private readonly settings = inject(SettingsService);

  ngOnInit() {
    this.settings.setDirection();
    this.settings.setTheme();
  }

  ngAfterViewInit() {
    this.preloader.hide();
  }
}
