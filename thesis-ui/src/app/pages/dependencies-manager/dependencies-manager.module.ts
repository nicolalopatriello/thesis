import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DependenciesManagerComponent } from './dependencies-manager/dependencies-manager.component';
import {RouterModule} from '@angular/router';
import {NbButtonModule, NbCheckboxModule, NbInputModule} from "@nebular/theme";
import {ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [DependenciesManagerComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([
      {
        path: '',
        component: DependenciesManagerComponent
      }
    ]),
    NbCheckboxModule,
    NbInputModule,
    NbButtonModule,
    ReactiveFormsModule
  ]
})
export class DependenciesManagerModule { }
