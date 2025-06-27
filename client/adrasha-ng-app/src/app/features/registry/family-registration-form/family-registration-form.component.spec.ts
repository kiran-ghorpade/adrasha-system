import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FamilyRegistrationFormComponent } from './family-registration-form.component';

describe('FamilyRegistrationFormComponent', () => {
  let component: FamilyRegistrationFormComponent;
  let fixture: ComponentFixture<FamilyRegistrationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FamilyRegistrationFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FamilyRegistrationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
