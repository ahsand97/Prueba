import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RealizarexamenComponent } from './realizarexamen.component';

describe('RealizarexamenComponent', () => {
  let component: RealizarexamenComponent;
  let fixture: ComponentFixture<RealizarexamenComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RealizarexamenComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RealizarexamenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
