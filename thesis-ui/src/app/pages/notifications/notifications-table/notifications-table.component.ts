import {Component, OnInit} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';
import {TestVectorsService} from '../../../@core/services/test-vectors.service';
import {NotificationService} from '../../../@core/services/notification.service';

@Component({
  selector: 'ngx-notifications-table',
  templateUrl: './notifications-table.component.html',
  styleUrls: ['./notifications-table.component.scss']
})
export class NotificationsTableComponent {

  settings = {
    columns: {
      userTestUrl: {
        title: 'User test',
        type: 'string',
      },
      changedDepType: {
        title: 'Changed dependency',
        type: 'string',
      },
      uuid: {
        title: 'Resolve UUID',
        type: 'string',
      },
      checked: {
        title: 'Fixed',
        type: 'string',
      },
      createdAt: {
        title: 'Created at',
        type: 'string',
      },
      checkedAt: {
        title: 'Fixed at',
        type: 'string',
      },
    },
    actions: {
      delete: false,
      add: false,
      edit: false,
      position: 'right'
    },
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(private notificationService: NotificationService) {
    this.notificationService.findAll().subscribe(t => {
      this.source.load(t);
    });
  }


}
