import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthDetailsPageComponent } from './health-details-page.component';

describe('HealthDetailsPageComponent', () => {
  let component: HealthDetailsPageComponent;
  let fixture: ComponentFixture<HealthDetailsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HealthDetailsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HealthDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
