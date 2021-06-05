import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'watcherTypeFormatter'
})
export class WatcherTypeFormatterPipe implements PipeTransform {

  transform(value: WatcherType | string, ...args: unknown[]): string {
    switch (value) {
      case WatcherType.DOCKERFILE_NMAP:
        return 'Dockerfile NMAP version watcher';
      case WatcherType.PYTHON_DEPENDENCY:
        return 'Requirements.txt vulnerabilities watcher';
      default:
        return null;
    }
  }

}

export enum WatcherType {
  PYTHON_DEPENDENCY = 'PYTHON_DEPENDENCY',
  DOCKERFILE_NMAP = 'DOCKERFILE_NMAP'
}
