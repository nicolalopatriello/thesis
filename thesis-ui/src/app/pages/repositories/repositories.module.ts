import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RepositoriesComponent } from './repositories/repositories.component';
import {RouterModule} from '@angular/router';



@NgModule({
  declarations: [RepositoriesComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([
      {
        path: '',
        component: RepositoriesComponent
      }
    ])
  ]
})
export class RepositoriesModule { }
