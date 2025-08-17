import { Component } from '@angular/core';
import { TranslatePipe } from '@ngx-translate/core';
import { PageHeaderComponent, PageWrapperComponent } from "@shared/components";

@Component({
  selector: 'app-settings-page',
  imports: [PageWrapperComponent, PageHeaderComponent, TranslatePipe],
  templateUrl: './settings-page.component.html'
})
export class SettingsPageComponent {

}
