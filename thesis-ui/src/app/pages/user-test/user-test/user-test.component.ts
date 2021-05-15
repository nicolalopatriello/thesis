import {Component, OnInit, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {GitraceService} from '../../../@core/services/gitrace.service';
import {GitProvider, Gitrace} from '../../../@core/models/gitrace';
import {TestVectorsService} from '../../../@core/services/test-vectors.service';
import {TestVector} from '../../../@core/models/test-vector';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {LocalDataSource} from 'ng2-smart-table';
import {UserTestService} from '../../../@core/services/user-test.service';
import {UserTest} from '../../../@core/models/user-test';
import {UserTestWithDeps} from '../../../@core/models/user-test-with-deps';
import {UserTestActionsComponent} from './user-test-actions.component';
import {ToastrService} from 'ngx-toastr';
import {catchError} from 'rxjs/operators';
import {of} from 'rxjs';
import {githubUrlValidation} from '../../repositories/repositories/repositories.component';
import {ConnectionsService} from '../../../@core/services/connections.service';
import {Connection} from '../../../@core/models/connection';

@Component({
  selector: 'ngx-user-test',
  templateUrl: './user-test.component.html',
  styleUrls: ['./user-test.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class UserTestComponent implements OnInit {

  @ViewChild('userTestDetailsDialog', {static: true}) userTestDetailsDialog: TemplateRef<any>;

  public gitraces: Array<Gitrace> = [];
  public testVectors: Array<TestVector> = [];
  public createUserTestFormGroup: FormGroup;

  public selectedGitraces: Array<number> = [];
  public selectedTestVectors: Array<number> = [];
  public createNewUserTestDialogRef: NbDialogRef<any>;
  public currentUserTestDetails: UserTestWithDeps;
  public userTestDetailsDialogRef: NbDialogRef<any>;
  public connections: Array<Connection>;

  get createEnabled() {
    return this.createUserTestFormGroup.valid && (this.selectedTestVectors.length > 0 || this.selectedGitraces.length > 0);
  }

  userTestsTableSettings = {
    columns: {
      gitRepoUrl: {
        title: 'Repository',
        type: 'string',
      },
      description: {
        title: 'Description',
        type: 'string',
      },
      createdAt: {
        title: 'Created at',
        type: 'string',
      },
      actions: {
        title: 'Actions',
        filter: false,
        type: 'custom',
        renderComponent: UserTestActionsComponent,
        onComponentInitFunction:
          (instance: any) => {
            instance.showDetails.subscribe(row => {
              this.userTestService.findById(row.id).subscribe(t => {
                this.currentUserTestDetails = t;
                this.userTestDetailsDialogRef = this.dialogService.open(this.userTestDetailsDialog, {});
              });
            });
          }
      },
    },
    actions: {
      delete: false,
      add: false,
      edit: false,
      position: 'right'
    },
  };

  userTestsTableSource: LocalDataSource = new LocalDataSource();

  constructor(private gitraceService: GitraceService,
              private toastr: ToastrService,
              private connectionsService: ConnectionsService,
              private dialogService: NbDialogService,
              private testVectorsService: TestVectorsService,
              private userTestService: UserTestService,
  ) {
  }

  GitProvider = GitProvider;

  ngOnInit(): void {
    this.gitraceService.findAll().subscribe(t => {
      this.gitraces = t;
    });

    this.testVectorsService.findAll().subscribe(tv => {
      this.testVectors = tv;
    });

    this.createUserTestFormGroup = new FormGroup({
      gitProvider: new FormControl(GitProvider.GITLAB, [Validators.required]),
      gitRepoUrl: new FormControl(null, [Validators.required]),
      connectionId: new FormControl(null),
      description: new FormControl(null, [])
    });

    this.findAllUserTests();
  }


  findAllConnections() {
    this.connectionsService.findAll().subscribe(conn => {
      this.connections = conn;
    });
  }

  findAllUserTests() {
    this.userTestService.findAll().subscribe(t => {
      this.userTestsTableSource.load(t);
    });

  }

  openCreateNewUserTest(dialog: TemplateRef<any>) {
    this.findAllConnections();
    this.createNewUserTestDialogRef = this.dialogService.open(
      dialog,
      {
        hasBackdrop: false,
        closeOnEsc: false,
      });
  }

  onGitracesCheckedChanges($event: boolean, gitraceId: number) {
    if ($event) {
      this.selectedGitraces.push(gitraceId);
    } else {
      this.selectedGitraces = this.selectedGitraces.filter(g => g !== gitraceId);
    }
  }

  onTestVectorsCheckedChanges($event: boolean, tvId: number) {
    if ($event) {
      this.selectedTestVectors.push(tvId);
    } else {
      this.selectedTestVectors = this.selectedTestVectors.filter(g => g !== tvId);
    }
  }

  newUserTestConfirm() {
    const userTest: UserTest = {
      gitProvider: this.createUserTestFormGroup.controls.gitProvider.value,
      gitRepoUrl: this.createUserTestFormGroup.controls.gitRepoUrl.value,
      description: this.createUserTestFormGroup.controls.description.value,
      gitraceDep: this.selectedGitraces,
      testVectorsDep: this.selectedTestVectors
    };
    this.userTestService.create(userTest).pipe(
      catchError(() => {
        this.toastr.error('Cannot register test');
        this.createUserTestFormGroup.reset();
        return of(null);
      })
    ).subscribe(t => {
      this.selectedTestVectors = [];
      this.selectedGitraces = [];
      this.findAllUserTests();
      this.createNewUserTestDialogRef.close();
    });
  }

  closeCreateNewUserTestDialog() {
    this.selectedTestVectors = [];
    this.selectedGitraces = [];
    this.createNewUserTestDialogRef.close();
  }
}
