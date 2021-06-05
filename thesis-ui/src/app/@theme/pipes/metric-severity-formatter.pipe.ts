import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'metricSeverityFormatter'
})
export class MetricSeverityFormatterPipe implements PipeTransform {

  transform(value: MetricSeverity | string, ...args: unknown[]): string {
    switch (value) {
      case MetricSeverity.LOW:
        return 'Low';
      case MetricSeverity.MEDIUM:
        return 'Medium';
      case MetricSeverity.HEIGHT:
        return 'Height';
      case MetricSeverity.UNDEFINED:
        return '-';
      default:
        return null;
    }
  }

}

export enum MetricSeverity {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HEIGHT = 'HEIGHT',
  UNDEFINED = 'UNDEFINED'
}
