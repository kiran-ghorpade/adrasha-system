import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PageHeaderComponent, PageWrapperComponent } from '@shared/components';
import { map } from 'rxjs';

@Component({
  selector: 'app-report-view-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './report-view-page.component.html',
})
export class ReportViewPageComponent {
  private readonly activatedRoute = inject(ActivatedRoute);
  // private readonly reportService = inject(ReportService);

  // base64PDF = toSignal(this.reportService.)

  type = toSignal(this.activatedRoute.paramMap.pipe(map((p) => p.get('type'))));

  base64PDF = signal(
    `JVBERi0xLjUKJeLjz9MKMSAwIG9iago8PC9UeXBlIC9DYXRhbG9nL1BhZ2VzIDIgMCBSCj4+CmVuZG9iagoKMiAwIG9iago8PC9UeXBlIC9QYWdlcy9LaWRzIFsgMyAwIFIgXS9Db3VudCAxCj4+CmVuZG9iagoKMyAwIG9iago8PC9UeXBlIC9QYWdlL1BhcmVudCAyIDAgUi9SZXNvdXJjZXMgPDwvRm9udCA8PC9GMCA0IDAgUj4+Pj4KL0NvbnRlbnRzIDUgMCBSCj4+CmVuZG9iagoKNCAwIG9iago8PC9UeXBlIC9Gb250L1N1YnR5cGUgL1R5cGUxL05hbWUgL0YwL0Jhc2VGb250IC9IZWx2ZXRpY2EvRW5jb2RpbmcgL1dpbkFuc2lFbmNvZGluZwo+PgplbmRvYmoKCjUgMCBvYmoKPDwvTGVuZ3RoIDY0Pj4Kc3RyZWFtCkJUIAovRjAgMTIgVGYKNTAgNzUwIFRECi9IZWxsbywgUERGIFdvcmxkIQplbmRzdHJlYW0KZW5kb2JqCgp4cmVmCjAgNgowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDAxMTAgMDAwMDAgbiAKMDAwMDAwMDA4NSAwMDAwMCBuIAowMDAwMDAwMjUyIDAwMDAwIG4gCjAwMDAwMDAzMTEgMDAwMDAgbiAKMDAwMDAwMDM5NiAwMDAwMCBuIAp0cmFpbGVyCjw8L1Jvb3QgMSAwIFIKL0luZm8gNyAwIFIKPj4Kc3RhcnR4cmVmCjQxMwpFT0YK`
  );

  blobUrl : string = '';

  showPdf() {
    const byteCharacters = atob(this.base64PDF());
    const byteNumbers = new Array(byteCharacters.length)
      .fill(0)
      .map((_, i) => byteCharacters.charCodeAt(i));
    const byteArray = new Uint8Array(byteNumbers);

    const blob = new Blob([byteArray], { type: 'application/pdf' });
    this.blobUrl = URL.createObjectURL(blob);

    window.open(this.blobUrl);
  }
}
