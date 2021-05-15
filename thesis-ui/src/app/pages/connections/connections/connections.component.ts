import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {GitProvider} from '../../../@core/models/gitrace';
import {LocalDataSource} from 'ng2-smart-table';
import {ToastrService} from 'ngx-toastr';
import {catchError} from 'rxjs/operators';
import {of} from 'rxjs';
import {ConnectionsService} from '../../../@core/services/connections.service';

@Component({
  selector: 'ngx-connections',
  templateUrl: './connections.component.html',
  styleUrls: ['./connections.component.scss']
})
export class ConnectionsComponent implements OnInit {

  public newConnectionFormGroup: FormGroup;

  settings = {
    columns: {
      gitProvider: {
        title: 'Provider',
        type: 'string',
      },
      name: {
        title: 'Name',
        type: 'string',
      },
      endpoint: {
        title: 'Endpoint',
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

  constructor(private connectionsService: ConnectionsService, private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.newConnectionFormGroup = new FormGroup(
      {
        gitProvider: new FormControl(GitProvider.GITLAB, [Validators.required]),
        endpoint: new FormControl(null, [Validators.required]),
        name: new FormControl(null, [Validators.required]),
        token: new FormControl(null, [])
      }
    );
    this.findAll();
  }

  findAll() {
    this.connectionsService.findAll().subscribe(t => {
      if (!!t) {
        this.source.load(t);
      }
    });
  }

  createConnection() {
    this.pendingRequest = true;
    this.connectionsService.create(this.newConnectionFormGroup.getRawValue()).pipe(
      catchError(err => {
        this.toastr.error('Cannot create repository');
        this.newConnectionFormGroup.reset();
        return of(null);
      })
    ).subscribe(t => {
      this.pendingRequest = false;
      this.findAll();
      this.newConnectionFormGroup.reset();
      this.newConnectionFormGroup.controls.gitProvider.setValue(GitProvider.GITHUB);
    });
  }

}
