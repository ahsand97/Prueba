import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogcrearexamenComponent } from './dialogcrearexamen.component';

describe('DialogcrearexamenComponent', () => {
  let component: DialogcrearexamenComponent;
  let fixture: ComponentFixture<DialogcrearexamenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogcrearexamenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogcrearexamenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
