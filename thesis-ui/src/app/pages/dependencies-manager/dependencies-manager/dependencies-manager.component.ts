import {Component, OnInit, TemplateRef} from '@angular/core';
import {GitraceService} from '../../../@core/services/gitrace.service';
import {Gitrace} from '../../../@core/models/gitrace';
import {TestVectorsService} from '../../../@core/services/test-vectors.service';
import {TestVector} from '../../../@core/models/test-vector';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {BtnActionsComponent} from "../../test-vectors/test-vectors-table/btnActions.component";
import {LocalDataSource} from "ng2-smart-table";
import {UserTestService} from "../../../@core/services/user-test.service";
import {UserTest} from "../../../@core/models/user-test";

@Component({
  selector: 'ngx-dependencies-manager',
  templateUrl: './dependencies-manager.component.html',
  styleUrls: ['./dependencies-manager.component.scss']
})
export class DependenciesManagerComponent implements OnInit {
  public gitraces: Array<Gitrace> = [];
  public testVectors: Array<TestVector> = [];
  public createUserTestFormGroup: FormGroup;

  public selectedGitraces: Array<number> = [];
  public selectedTestVectors: Array<number> = [];
  public createNewUserTestDialogRef: NbDialogRef<any>;

  get createEnabled() {
    return this.createUserTestFormGroup.valid && (this.selectedTestVectors.length > 0 || this.selectedGitraces.length > 0);
  }

  userTestsTableSettings = {
    columns: {
      url: {
        title: 'URL',
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
              private dialogService: NbDialogService,
              private testVectorsService: TestVectorsService,
              private userTestService: UserTestService,
  ) {
  }

  ngOnInit(): void {
    this.gitraceService.findAll().subscribe(t => {
      this.gitraces = t;
    });

    this.testVectorsService.findAll().subscribe(tv => {
      this.testVectors = tv;
    });

    this.createUserTestFormGroup = new FormGroup({
      url: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [])
    });

    this.findAllUserTests();

  }

  findAllUserTests() {
    this.userTestService.findAll().subscribe(t => {
      this.userTestsTableSource.load(t);
    });

  }

  openCreateNewUserTest(dialog: TemplateRef<any>) {
    this.createNewUserTestDialogRef = this.dialogService.open(
      dialog,
      {
        hasBackdrop: false,
        dialogClass: 'model-full'
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
      url: this.createUserTestFormGroup.controls.url.value,
      description: this.createUserTestFormGroup.controls.description.value,
      gitraceDep: this.selectedGitraces,
      testVectorsDep: this.selectedTestVectors
    };
    this.userTestService.create(userTest).subscribe(t => {
      this.findAllUserTests();
      this.createNewUserTestDialogRef.close();
    });
  }

}
