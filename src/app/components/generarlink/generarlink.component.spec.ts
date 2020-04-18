import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerarlinkComponent } from './generarlink.component';

describe('GenerarlinkComponent', () => {
  let component: GenerarlinkComponent;
  let fixture: ComponentFixture<GenerarlinkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GenerarlinkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GenerarlinkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
