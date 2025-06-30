import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataCardLabelComponent } from './data-card-label.component';

describe('DataCardLabelComponent', () => {
  let component: DataCardLabelComponent;
  let fixture: ComponentFixture<DataCardLabelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataCardLabelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DataCardLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
