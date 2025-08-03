import { Component, inject } from '@angular/core';
import { MatChipsModule } from '@angular/material/chips';
import { MatDialog } from '@angular/material/dialog';
import { AboutComponent } from '@shared/components/about/about.component';

@Component({
  selector: 'app-logo',
  imports: [MatChipsModule],
  template: `
    <mat-chip
      style=" box-shadow: var(--mat-sys-level3)"
      (click)="openAppInfo()"
    >
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
    cursor : grab;
  }`,
})
export class AppLogoComponent {
  private dialog = inject(MatDialog);

  openAppInfo() {
    this.dialog.open(AboutComponent, { data: {} });
  }
}
