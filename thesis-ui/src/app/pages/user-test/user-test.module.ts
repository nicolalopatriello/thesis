import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import {NbButtonModule, NbCardModule, NbCheckboxModule, NbInputModule} from '@nebular/theme';
import {ReactiveFormsModule} from '@angular/forms';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {UserTestComponent} from './user-test/user-test.component';
import {UserTestActionsComponent} from './user-test/user-test-actions.component';
import {ToastrModule} from 'ngx-toastr';



@NgModule({
  declarations: [UserTestComponent, UserTestActionsComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([
      {
        path: '',
        component: UserTestComponent
      }
    ]),
    NbCheckboxModule,
    NbInputModule,
    NbButtonModule,
    ReactiveFormsModule,
    NbCardModule,
    Ng2SmartTableModule,
    ToastrModule
  ]
})
export class UserTestModule { }
