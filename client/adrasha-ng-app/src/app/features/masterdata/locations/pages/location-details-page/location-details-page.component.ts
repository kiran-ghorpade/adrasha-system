import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { LocationService as LocationApiService } from '@core/api';
import {
  ConfirmationComponent,
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent,
} from '@shared/components';
import { LocationService } from '../../services';
import { LocationResponseDTO } from '@core/model/masterdataService';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { LocationDetailsComponent } from '../../components';

@Component({
  selector: 'app-location-details-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    RouterModule,
    LocationDetailsComponent,
  ],
  templateUrl: './location-details-page.component.html',
})
export class LocationDetailsPageComponent {
  private readonly dialog = inject(MatDialog);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly locationApiService = inject(LocationApiService);
  private readonly locationService = inject(LocationService);

  data = signal<DataLabelType[]>([]);
  locationDetails = signal<LocationResponseDTO | null>(null);
  locationId: string = '';

  ngOnInit(): void {
    this.locationId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    if (this.locationId) this.loadData();
  }

  loadData() {
    this.locationApiService
      .getLocation(this.locationId)
      .subscribe((location) => {
        if (location) {
          this.data.set(locationToData(location) ?? []);
          this.locationDetails.set(location);
        } else {
          this.data.set([]);
          this.locationDetails.set(null);
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
        this.locationService.delete(this.locationId);
      }
    });
  }
}

function locationToData(location: LocationResponseDTO): DataLabelType[] {
  return [{ label: 'name', value: location.name, icon: 'location' }];
}
