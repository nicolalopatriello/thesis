import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {GitProvider} from '../../../@core/models/gitrace';
import {GitraceService} from '../../../@core/services/gitrace.service';
import {LocalDataSource} from 'ng2-smart-table';
import {ToastrService} from 'ngx-toastr';
import {catchError} from 'rxjs/operators';
import {of} from 'rxjs';

@Component({
  selector: 'ngx-repositories',
  templateUrl: './repositories.component.html',
  styleUrls: ['./repositories.component.scss']
})
export class RepositoriesComponent implements OnInit {

  public newGitraceFormGroup: FormGroup;

  settings = {
    columns: {
      gitRepoUrl: {
        title: 'URL',
        type: 'string',
      },
      gitDescription: {
        title: 'Description',
        type: 'string',
      },
      gitProvider: {
        title: 'Provider',
        type: 'string',
      },
      lastRepoUpdate: {
        title: 'Last commit',
        type: 'string',
      },
      registrationTime: {
        title: 'Registered at',
        type: 'string',
      },
    },
    actions: {
      delete: false,
      add: false,
      edit: false,
      position: 'right'
    },
  };

  source: LocalDataSource = new LocalDataSource();

  GitProvider = GitProvider;
  pendingRequest = false;

  constructor(private gitraceService: GitraceService, private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.newGitraceFormGroup = new FormGroup(
      {
        gitProvider: new FormControl(GitProvider.GITHUB, [Validators.required]),
        gitRepoUrl: new FormControl(null, [Validators.required, githubUrlValidation]),
        token: new FormControl(null, []),
        gitDescription: new FormControl(null, [])
      }
    );
    this.findAll();
  }

  findAll() {
    this.gitraceService.findAll().subscribe(t => {
      if (!!t) {
        this.source.load(t);
      }
    });
  }

  createNewGitrace() {
    this.pendingRequest = true;
    this.gitraceService.create(this.newGitraceFormGroup.getRawValue()).pipe(
      catchError(err => {
        this.toastr.error('Cannot create repository');
        return of(null);
      })
    ).subscribe(t => {
      this.pendingRequest = false;
      this.findAll();
      this.newGitraceFormGroup.reset();
      this.newGitraceFormGroup.controls.gitProvider.setValue(GitProvider.GITHUB);
    });
  }
}

export const githubUrlValidation: ValidatorFn = (control: FormControl) => {
  const ext = control.value?.slice(control.value.length - 4);
  if (ext === '.git') {
    return null;
  } else {
    return {invalidUrl: true};
  }
};
