import { Component, inject, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { NcdService as NcdApiService } from '@core/api';
import { PageWrapperComponent, PageHeaderComponent, ConfirmationComponent, DataLabelType } from "@shared/components";
import { NcdService } from '../../services';
import { NCDResponseDTO } from '@core/model/masterdataService';
import { NcdDetailsComponent } from "../../components";

@Component({
  selector: 'app-ncd-details-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    RouterModule,
    NcdDetailsComponent
],
  templateUrl: './ncd-details-page.component.html',
})
export class NcdDetailsPageComponent {
  private readonly dialog = inject(MatDialog);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly ncdApiService = inject(NcdApiService);
  private readonly ncdService = inject(NcdService);

  data = signal<DataLabelType[]>([]);
  ncdDetails = signal<NCDResponseDTO | null>(null);
  ncdId: string = '';

  ngOnInit(): void {
    this.ncdId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    if (this.ncdId) this.loadData();
  }

  loadData() {
    this.ncdApiService
      .getNCD(this.ncdId)
      .subscribe((ncd) => {
        if (ncd) {
          this.data.set(ncdToData(ncd) ?? []);
          this.ncdDetails.set(ncd);
        } else {
          this.data.set([]);
          this.ncdDetails.set(null);
        }
      });
  }

  handleDeleteClick() {
    const dialogRef = this.dialog.open(ConfirmationComponent, {
      data: {
        title: 'Do you want to delete this member?',
        message: 'Member and his healthrecords will be deleted',
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.ncdService.delete(this.ncdId);
      }
    });
  }
}

function ncdToData(ncd: NCDResponseDTO): DataLabelType[] {
  return [
    { label: 'ID', value: ncd.id, icon: 'badge' },
    { label: 'Name', value: ncd.name, icon: 'medical_services' },
    { label: 'Description', value: ncd.description, icon: 'description' },
    { label: 'Created At', value: ncd.createdAt, icon: 'calendar_today' },
    { label: 'Updated At', value: ncd.updatedAt, icon: 'update' },
  ];
}
