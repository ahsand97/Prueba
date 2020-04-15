import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogcrearpreguntaComponent } from './dialogcrearpregunta.component';

describe('DialogcrearpreguntaComponent', () => {
  let component: DialogcrearpreguntaComponent;
  let fixture: ComponentFixture<DialogcrearpreguntaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogcrearpreguntaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogcrearpreguntaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
