import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {Connection} from '../../../@core/models/connection';
import {RepositoryService} from '../../../@core/services/repository.service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';

@Component({
  selector: 'ngx-repositories',
  templateUrl: './repositories.component.html',
  styleUrls: ['./repositories.component.scss']
})
export class RepositoriesComponent implements OnInit {

  @ViewChild('addRepositoryDialog') addRepositoryDialog: TemplateRef<any>;

  public newRepositoryFormGroup: FormGroup;

  pendingRequest = false;
  public connections: Array<Connection>;
  private addRepoDialogRef: NbDialogRef<any>;
  editorOptions = {theme: 'vs-dark', language: 'json'};

  constructor(private repositoryService: RepositoryService,
              private dialogService: NbDialogService,
              private toastrService: ToastrService) {
  }

  ngOnInit(): void {

    this.newRepositoryFormGroup = new FormGroup(
      {
        url: new FormControl(null, [Validators.required]),
        branch: new FormControl('master', [Validators.required]),
        username: new FormControl(null, [Validators.required]),
        password: new FormControl(null, [Validators.required]),
        minutesWatchersInterval: new FormControl(5, [Validators.required]),
        recipe: new FormControl(this.defaultRecipe(), [Validators.required]),
      }
    );
    this.findAll();
  }

  private defaultRecipe(): string {
    return `
    {
        "items": [
             {
            "watcherType": "PYTHON_DEPENDENCY",
            "args": {}
             }
        ]
    }
    `;
  }

  findAll() {
    this.repositoryService.findAll().subscribe(t => {
      console.log(t);
    });
  }

  createNewRepository() {
    /*    this.pendingRequest = true;
        this.repositoryService.create(this.newRepositoryFormGroup.getRawValue()).pipe(
          catchError(err => {
            this.toastr.error('Cannot create repository');
            this.newRepositoryFormGroup.reset();
            return of(null);
          })
        ).subscribe(t => {
          this.pendingRequest = false;
          this.findAll();
          this.newRepositoryFormGroup.reset();
          this.newRepositoryFormGroup.controls.gitProvider.setValue(GitProvider.GITHUB);
        });
      }*/

  }

  openAddRepositoryDialog() {
    this.addRepoDialogRef = this.dialogService.open(this.addRepositoryDialog, {dialogClass: 'lg'});
  }

  closeDialog() {
    this.addRepoDialogRef.close();
  }
}
