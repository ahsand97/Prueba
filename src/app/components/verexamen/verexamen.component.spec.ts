import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerexamenComponent } from './verexamen.component';

describe('VerexamenComponent', () => {
  let component: VerexamenComponent;
  let fixture: ComponentFixture<VerexamenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerexamenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerexamenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
