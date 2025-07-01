import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleRequestPageComponent } from './role-request-page.component';

describe('RoleRequestPageComponent', () => {
  let component: RoleRequestPageComponent;
  let fixture: ComponentFixture<RoleRequestPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoleRequestPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoleRequestPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
