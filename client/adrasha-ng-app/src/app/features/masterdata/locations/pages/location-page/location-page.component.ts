import { Component, inject, signal } from '@angular/core';
import { PageWrapperComponent, PageHeaderComponent } from '@shared/components';
import { LocationListComponent } from '../../components';
import { LocationService } from '@core/api';
import { LocationResponseDTO } from '@core/model/masterdataService';
import { MatPaginatorModule } from '@angular/material/paginator';

@Component({
  selector: 'app-location-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    LocationListComponent,
    MatPaginatorModule,
  ],
  templateUrl: './location-page.component.html',
})
export class LocationPageComponent {
  private readonly locationService = inject(LocationService);

  userId: string = '';
  locationList = signal<LocationResponseDTO[]>([]);

  // page metadata
  totalSize = signal(0);
  page = signal(0);

  ngOnInit(): void {
    this.loadRoleRequests();
  }

  onPageChange(event: any) {
    this.loadRoleRequests();
  }

  loadRoleRequests() {
    this.locationService
      .getAllLocations({
        filterDTO: {},
        pageable: {
          page: 1,
          size: 10,
          sort: [],
        },
      })
      .subscribe((rolerequests) => {
        this.locationList.set(rolerequests.content || []);
        this.totalSize.set(rolerequests.numberOfElements || 0);
      });
  }
}
