import {Directive, ElementRef, Input, OnChanges, SimpleChanges} from '@angular/core';

@Directive({
  selector: '[ngxCvssColor]'
})
export class CvssColorDirective implements OnChanges {
  @Input('ngxCvssColor') value: number;

  constructor(private elRef: ElementRef) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.value < 4) {
      this.elRef.nativeElement.style.color = 'green';
    }
    if (this.value > 4 && this.value < 7) {
      this.elRef.nativeElement.style.color = 'orange';
    }
    if (this.value > 7 && this.value < 9) {
      this.elRef.nativeElement.style.color = '#f17070';
    }
    if (this.value > 9) {
      this.elRef.nativeElement.style.color = 'red';
    }
  }

}
