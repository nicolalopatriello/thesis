import {Directive, ElementRef, Input, OnChanges, SimpleChanges} from '@angular/core';
import {MetricSeverity} from '../pipes';

@Directive({
  selector: '[ngxMetricSeverityColor]'
})
export class MetricSeverityColorDirective implements OnChanges {
  @Input('ngxMetricSeverityColor') value: MetricSeverity | string;

  constructor(private elRef: ElementRef) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    switch (this.value) {
      case MetricSeverity.LOW:
        this.elRef.nativeElement.style.color = 'green';
        break;
      case MetricSeverity.MEDIUM:
        this.elRef.nativeElement.style.color = 'orange';
        break;
      case MetricSeverity.HEIGHT:
        this.elRef.nativeElement.style.color = 'red';
        break;
    }
  }

}
