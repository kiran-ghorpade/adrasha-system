import { Component, Input } from '@angular/core';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-logo',
  template: `
      <mat-chip class="shadow-md">
        <img matChipAvatar src="/images/adrasha_logo.ico" class="w-6 h-6 ml-3" />
        <h3 class="font-normal hidden md:block">ADRASHA</h3>
      </mat-chip>
  `,
  styles: `
  ::ng-deep .mat-mdc-standard-chip .mdc-evolution-chip__action--primary::before {
  content: none;
  border: none !important;
  height: 0 !important;
  width: 0 !important;
  box-sizing: content-box;
  position: unset !important;
  z-index: unset !important;
  pointer-events: none;
}

  `,
  imports: [MatChipsModule],
})
export class AppLogoComponent {
  @Input() showName = true;
}
