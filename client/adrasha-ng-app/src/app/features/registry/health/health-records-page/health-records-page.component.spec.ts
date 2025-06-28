import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthRecordsPageComponent } from './health-records-page.component';

describe('HealthRecordsPageComponent', () => {
  let component: HealthRecordsPageComponent;
  let fixture: ComponentFixture<HealthRecordsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HealthRecordsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HealthRecordsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
