import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {RepositoryService} from '../../../@core/services/repository.service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {RepositoryLight} from '../../../@core/models/repository-light';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryCreateRequest} from '../../../@core/models/repository-create-request';
import {catchError} from 'rxjs/operators';
import {of} from 'rxjs';

@Component({
  selector: 'ngx-repositories',
  templateUrl: './repositories.component.html',
  styleUrls: ['./repositories.component.scss']
})
export class RepositoriesComponent implements OnInit {

  @ViewChild('addRepositoryDialog') addRepositoryDialog: TemplateRef<any>;
  @ViewChild('deleteRepositoryDialog') deleteRepositoryDialog: TemplateRef<any>;

  pendingRequest = false;
  editorOptions = {theme: 'vs-dark', language: 'json'};

  private addRepoDialogRef: NbDialogRef<any>;
  public repositories: Array<RepositoryLight> = [];
  private deleteRepoDialogRef: NbDialogRef<any>;
  public currentRepositoryToDelete: RepositoryLight;
  public newRepositoryFormGroup: FormGroup;

  constructor(private repositoryService: RepositoryService,
              private dialogService: NbDialogService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private toastrService: ToastrService) {
  }

  ngOnInit(): void {


    this.newRepositoryFormGroup = new FormGroup(
      {
        url: new FormControl(null, [Validators.required, gitUrlValidator]),
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
             },
             {
              "watcherType": "DOCKERFILE_NMAP",
              "args": {}
              }
        ]
    }
    `;
  }

  findAll() {
    this.repositoryService.findAll().subscribe(t => {
      this.repositories = t;
    });
  }

  createNewRepository() {
    const r: RepositoryCreateRequest = this.newRepositoryFormGroup.getRawValue();


    this.repositoryService.create({
      ...r,
      recipe: JSON.parse(r.recipe.replace(/\s/g, ''))
    }).pipe(
      catchError(err => {
        this.toastrService.error('Cannot create repository');
        this.newRepositoryFormGroup.reset();
        return of(null);
      })
    ).subscribe(resp => {
      this.toastrService.success('Repository created');
      this.findAll();
      this.newRepositoryFormGroup.reset();
      this.addRepoDialogRef.close();
    });
  }

  openAddRepositoryDialog() {
    this.addRepoDialogRef = this.dialogService.open(this.addRepositoryDialog);
  }

  closeDeleteRepoDialog() {
    this.currentRepositoryToDelete = null;
    this.deleteRepoDialogRef.close();
  }

  closeAddRepoDialog() {
    this.addRepoDialogRef.close();
  }

  repositoryDetails(r: RepositoryLight) {
    this.router.navigate([r.id, 'details'], {relativeTo: this.activatedRoute});
  }

  openDeleteRepositoryDialog(r: RepositoryLight) {
    this.deleteRepoDialogRef = this.dialogService.open(this.deleteRepositoryDialog);
    this.currentRepositoryToDelete = r;
  }

  deleteRepositoryConfirm() {
    this.repositoryService.delete(this.currentRepositoryToDelete.id).pipe(
      catchError(() => {
        this.toastrService.error('Cannot delete repository');
        return of(null);
      })
    ).subscribe(() => {
        this.closeDeleteRepoDialog();
        this.findAll();
        this.toastrService.success('Repository successfully deleted');
      }
    );
  }
}

export const gitUrlValidator: ValidatorFn = (control: FormControl) => {
  const ext = control.value?.slice(control.value.length - 4);
  if (ext === '.git') {
    return null;
  } else {
    return {invalidGitUrl: true};
  }
};
