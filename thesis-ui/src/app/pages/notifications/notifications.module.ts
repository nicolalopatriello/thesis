import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {NbCardModule, NbIconModule, NbInputModule, NbTreeGridModule} from '@nebular/theme';
import {ThemeModule} from '../../@theme/theme.module';
import {NotificationsTableComponent} from './notifications-table/notifications-table.component';



@NgModule({
  declarations: [NotificationsTableComponent],
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
        component: NotificationsTableComponent
      }
    ])
  ]
})
export class NotificationsModule { }
