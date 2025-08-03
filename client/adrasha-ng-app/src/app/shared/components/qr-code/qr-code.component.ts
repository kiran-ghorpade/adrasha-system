import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { QrCodeComponent } from 'ng-qrcode';
import { NgxPrintModule } from 'ngx-print';
import { AppLogoComponent } from '../../widgets/logo.component';

export interface FamilyQRType {
  url: string;
  homeId: string;
  headname: string;
  contact: string;
}

@Component({
  selector: 'app-qr-code',
  imports: [
    NgxPrintModule,
    QrCodeComponent,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    AppLogoComponent,
  ],
  template: ` 
    <mat-dialog-content id="printSection">
      @if(data){
      <div class="text-center">
        <app-logo />
        <h4>App URL : {{ data.url }}</h4>
        <qr-code [value]="data.url" size="300" errorCorrectionLevel="M" />
        <div class="">
          <h4>House Id : {{ data.homeId }}</h4>
          <h4>Head Name : {{ data.headname }}</h4>
          <h4>Contact : {{ data.contact }}</h4>
        </div>
      </div>
      }@else {
      <h4>Failed Load Information</h4>
      }
    </mat-dialog-content>
    <mat-dialog-actions>
      <button matButton mat-dialog-close cdkFocusInitial>close</button>
      <button
        matButton="filled"
        printTitle="ADRASHA"
        [useExistingCss]="true"
        [openNewTab]="true"
        printSectionId="printSection"
        [disabled]="data == undefined"
        ngxPrint
      >
        <mat-icon> print </mat-icon>
        print
      </button>
    </mat-dialog-actions>`,
})
export class QrCodeDialog {
  data: FamilyQRType = inject(MAT_DIALOG_DATA);
}
