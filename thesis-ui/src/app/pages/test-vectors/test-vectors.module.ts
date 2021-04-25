import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import { TestVectorsTableComponent } from './test-vectors-table/test-vectors-table.component';
import * as path from 'path';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {NbCardModule, NbIconModule, NbInputModule, NbTreeGridModule} from '@nebular/theme';
import {ThemeModule} from '../../@theme/theme.module';
import {BtnActionsComponent} from './test-vectors-table/btnActions.component';



@NgModule({
  declarations: [TestVectorsTableComponent, BtnActionsComponent],
  imports: [
    CommonModule,
    Ng2SmartTableModule,
    NbCardModule,
    NbTreeGridModule,
    NbIconModule,
    NbInputModule,
    ThemeModule,
    RouterModule.forChild([
      {
        path: '',
        component: TestVectorsTableComponent
      }
    ])
  ]
})
export class TestVectorsModule { }
