import { Component, inject, signal } from '@angular/core';
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
import { TranslateModule, TranslateService } from '@ngx-translate/core';

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
    TranslateModule
  ],
  templateUrl: './location-details-page.component.html',
})
export class LocationDetailsPageComponent {
  private readonly dialog = inject(MatDialog);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly locationApiService = inject(LocationApiService);
  private readonly locationService = inject(LocationService);
  private readonly translateService = inject(TranslateService);

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
        title: this.translateService.instant('app.features.registry.family.dialogs.title'),
        message: this.translateService.instant('app.features.registry.family.dialogs.message'),
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
  return [
    { label: 'app.features.masterdata.location.table.id', value: location.id, icon: 'label' },
    { label: 'app.common.name', value: location.name, icon: 'place' },
    { label: 'app.features.masterdata.location.table.type', value: location.type || 'Not Available', icon: 'category' },
    { label: 'app.features.masterdata.location.table.pincode', value: location.pincode, icon: 'pin' },
    { label: 'app.features.masterdata.location.table.subdistrict', value: location.subdistrict, icon: 'location_city' },
    { label: 'app.features.masterdata.location.table.district', value: location.district, icon: 'map' },
    { label: 'app.features.masterdata.location.table.state', value: location.state, icon: 'public' },
    { label: 'app.features.masterdata.location.table.country', value: location.country, icon: 'flag' },
    { label: 'app.common.createdAt', value: location.createdAt, icon: 'calendar_today' },
    { label: 'app.common.updatedAt', value: location.updatedAt, icon: 'update' },
  ];
}
