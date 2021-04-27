import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DependenciesManagerComponent } from './dependencies-manager/dependencies-manager.component';
import {RouterModule} from '@angular/router';
import {NbButtonModule, NbCardModule, NbCheckboxModule, NbInputModule} from "@nebular/theme";
import {ReactiveFormsModule} from "@angular/forms";
import {Ng2SmartTableModule} from "ng2-smart-table";



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
    ReactiveFormsModule,
    NbCardModule,
    Ng2SmartTableModule
  ]
})
export class DependenciesManagerModule { }
