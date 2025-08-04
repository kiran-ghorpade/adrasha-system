import { Component, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { UserService as UserApiService } from '@core/api/user/user.service';
import { UserResponseDTO } from '@core/model/userService';
import { AuthService } from '@core/services';
import { UserDetailsComponent } from "@features/users/components";
import {
  DataLabelType,
  PageHeaderComponent,
  PageWrapperComponent
} from '@shared/components';
import { getFullName } from '@shared/utils/fullName';

@Component({
  selector: 'app-role-request-details-page',
  imports: [
    PageWrapperComponent,
    PageHeaderComponent,
    MatListModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    RouterModule,
    UserDetailsComponent
],
  templateUrl: './user-details-page.component.html',
})
export class UserDetailsPageComponent {
  private readonly dialog = inject(MatDialog);
  private readonly activatedRoute = inject(ActivatedRoute);
  private readonly userApiService = inject(UserApiService);
  private readonly authService = inject(AuthService);

  data = signal<DataLabelType[]>([]);
  userDetails = signal<UserResponseDTO | null>(null);
  userId: string = '';

  isAdmin = toSignal(this.authService.isAdmin(), { initialValue: false });

  ngOnInit(): void {
    this.userId = this.activatedRoute.snapshot.paramMap.get('id') || '';
    if (this.userId) this.loadData();
  }

  loadData() {
    this.userApiService
      .getUser(this.userId)
      .subscribe((member) => {
        this.data.set(userResponseToData(member));
        this.userDetails.set(member);
      });
  }
}


function userResponseToData(user: UserResponseDTO): DataLabelType[] {
  return [
    { label: 'User ID', value: user.id, icon: 'badge' },
    { label: 'Name', value: getFullName(user.name), icon: 'person' },
    {
      label: 'Health Center ID',
      value: user.healthCenterId,
      icon: 'first_aid',
    },
    {
      label: 'Adhar Number',
      value: user.adharNumber,
      icon: 'credit_card',
    },
    {
      label: 'Roles',
      value: user.roles?.map((role) => role).join(', ') ?? '',
      icon: 'group',
    },
    {
      label: 'Created At',
      value: user.createdAt,
      icon: 'calendar_today',
    },
    {
      label: 'Last Update',
      value: user.updatedAt,
      icon: 'update',
    },
  ];
}
