import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RepositoriesComponent } from './repositories/repositories.component';
import {RouterModule} from '@angular/router';
import {
  NbButtonModule,
  NbCardModule,
  NbDialogModule,
  NbIconModule,
  NbInputModule, NbListModule,
  NbSelectModule
} from '@nebular/theme';
import {ReactiveFormsModule} from '@angular/forms';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {ToastrModule} from 'ngx-toastr';
import {MonacoEditorModule} from 'ngx-monaco-editor';
import { RepositoryDetailsComponent } from './repository-details/repository-details.component';
import {RepositoryDetailsResolve} from './services/repository-details-resolve';
import {ChartModule} from 'angular2-chartjs';
import {CoreModule} from '../../@core/core.module';
import {ThemeModule} from '../../@theme/theme.module';



@NgModule({
  declarations: [RepositoriesComponent, RepositoryDetailsComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([
      {
        path: '',
        component: RepositoriesComponent
      },
      {
        path: ':repositoryId/details',
        component: RepositoryDetailsComponent,
        resolve: {
          repository: RepositoryDetailsResolve
        },
      }
    ]),
    NbCardModule,
    ReactiveFormsModule,
    NbButtonModule,
    NbInputModule,
    NbSelectModule,
    Ng2SmartTableModule,
    NbDialogModule.forRoot({hasBackdrop: true, closeOnBackdropClick: false}),
    ToastrModule,
    MonacoEditorModule,
    NbIconModule,
    NbListModule,
    ThemeModule
  ]
})
export class RepositoriesModule { }
