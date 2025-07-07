import { Component, inject, OnInit, signal } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { UserService } from '@core/api/user/user.service';
import { UserResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { PageHeaderComponent } from '../../../shared/components/page-header/page-header.component';
import { HealthCenterService } from '@core/api/health-center/health-center.service';
import {
  HealthCenterResponseDTO,
  LocationResponseDTO,
} from '@core/model/masterdataService';
import { LocationService } from '@core/api/location/location.service';

@Component({
  selector: 'app-profile-page',
  imports: [MatCardModule, PageHeaderComponent],
  templateUrl: './profile-page.component.html',
})
export class ProfilePageComponent implements OnInit {
  private readonly authService = inject(AuthService);
  private readonly userService = inject(UserService);
  private readonly healthCenterService = inject(HealthCenterService);
  private readonly locationService = inject(LocationService);

  userDetails = signal<UserResponseDTO>({});
  healthCenterDetails = signal<HealthCenterResponseDTO>({});
  addressDetails = signal<LocationResponseDTO>({});

  ngOnInit(): void {
    this.loadUserDetails();
    this.loadHealthCenter();
    this.loadLocationDetails();
  }

  loadUserDetails() {
    this.userService.getCurrentUser().subscribe((user) => {
      this.userDetails.set(user);
    });
  }

  loadHealthCenter() {
    this.healthCenterService
      .getHealthCenter(this.userDetails().healthCenterId || '')
      .subscribe((healthCenter) => {
        this.healthCenterDetails.set(healthCenter);
      });
  }

  loadLocationDetails() {
    this.locationService
      .getLocation(this.healthCenterDetails().locationId || '')
      .subscribe((location) => {
        this.addressDetails.set(location);
      });
  }
}

interface userprofile{
  fullname:string,
  adharNumber:string,
  registrationDate:Date,

  healthcenterName : string,
  
  village : string,
  pincode : string,
  subdistrict: string,
  state: string,
  country: string,
  
}
// user
    // name?: Name;
    // adharNumber?: string;
    // createdAt?: string;
    // updatedAt?: string;

// healthcenter
    // name?: string;
    // centerType?: HealthCenterResponseDTOCenterType;
    // locationId?: string;
    // totalFamilies?: number;
    // totalPopulation?: number;
    // createdAt?: string;
    // updatedAt?: string;

// address
    // name?: string;
    // type?: LocationResponseDTOType;
    // pincode?: string;
    // subdistrict?: string;
    // district?: string;
    // state?: string;
    // country?: string;
    // createdAt?: string;
    // updatedAt?: string;