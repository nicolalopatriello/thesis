import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'repoNameExtract'
})
export class RepoNameExtractPipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): unknown {
    const s = value.split('/');
    if (s.length > 0) {
      const v = s[s.length - 1];
      return v.replace('.git', '');
    }
  }

}
