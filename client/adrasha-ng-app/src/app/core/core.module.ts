import { CommonModule } from '@angular/common';
import { NgModule, Optional, SkipSelf } from '@angular/core';
import { LoginService, TranslateLangService } from '../../app/core/services';

@NgModule({
  declarations: [],
  imports: [CommonModule],
  providers: [LoginService, TranslateLangService],
  
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import it only in AppModule.');
    }
  }
}
