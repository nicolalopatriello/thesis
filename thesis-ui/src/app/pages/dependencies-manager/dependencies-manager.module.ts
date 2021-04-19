import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DependenciesManagerComponent } from './dependencies-manager/dependencies-manager.component';
import {RouterModule} from '@angular/router';



@NgModule({
  declarations: [DependenciesManagerComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([
      {
        path: '',
        component: DependenciesManagerComponent
      }
    ])
  ]
})
export class DependenciesManagerModule { }
