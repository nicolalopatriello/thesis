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
      case WatcherType.SIMPLE_DOCKER_INSPECT:
        return 'Simple Docker inspect';
      default:
        return null;
    }
  }

}

export enum WatcherType {
  PYTHON_DEPENDENCY = 'PYTHON_DEPENDENCY',
  DOCKERFILE_NMAP = 'DOCKERFILE_NMAP',
  SIMPLE_DOCKER_INSPECT = 'SIMPLE_DOCKER_INSPECT'
}
