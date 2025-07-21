import { Component } from '@angular/core';
import { PageWrapperComponent, PageHeaderComponent } from "@shared/components";

@Component({
  selector: 'app-settings-page',
  imports: [PageWrapperComponent, PageHeaderComponent],
  templateUrl: './settings-page.component.html'
})
export class SettingsPageComponent {

}
