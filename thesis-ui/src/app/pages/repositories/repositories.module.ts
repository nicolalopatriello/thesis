import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RepositoriesComponent } from './repositories/repositories.component';
import {RouterModule} from '@angular/router';
import {NbButtonModule, NbCardModule, NbInputModule, NbSelectModule} from '@nebular/theme';
import {ReactiveFormsModule} from '@angular/forms';
import {Ng2SmartTableModule} from 'ng2-smart-table';



@NgModule({
  declarations: [RepositoriesComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([
      {
        path: '',
        component: RepositoriesComponent
      }
    ]),
    NbCardModule,
    ReactiveFormsModule,
    NbButtonModule,
    NbInputModule,
    NbSelectModule,
    Ng2SmartTableModule
  ]
})
export class RepositoriesModule { }
