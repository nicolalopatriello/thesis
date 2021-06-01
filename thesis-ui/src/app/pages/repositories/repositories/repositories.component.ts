import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {RepositoryService} from '../../../@core/services/repository.service';
import {NbDialogRef, NbDialogService} from '@nebular/theme';
import {RepositoryLight} from '../../../@core/models/repository-light';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'ngx-repositories',
  templateUrl: './repositories.component.html',
  styleUrls: ['./repositories.component.scss']
})
export class RepositoriesComponent implements OnInit {

  @ViewChild('addRepositoryDialog') addRepositoryDialog: TemplateRef<any>;

  public newRepositoryFormGroup: FormGroup;

  pendingRequest = false;
  private addRepoDialogRef: NbDialogRef<any>;
  editorOptions = {theme: 'vs-dark', language: 'json'};
  public repositories: Array<RepositoryLight> = [];

  constructor(private repositoryService: RepositoryService,
              private dialogService: NbDialogService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
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
      this.repositories = t;
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

  repositoryDetails(r: RepositoryLight) {
    this.router.navigate([r.id, 'details'], {relativeTo: this.activatedRoute});
  }
}
