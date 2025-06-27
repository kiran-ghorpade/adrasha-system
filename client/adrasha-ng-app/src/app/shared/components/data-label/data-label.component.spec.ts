import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataLabelComponent } from './data-label.component';

describe('DataLabelComponent', () => {
  let component: DataLabelComponent;
  let fixture: ComponentFixture<DataLabelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataLabelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DataLabelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
