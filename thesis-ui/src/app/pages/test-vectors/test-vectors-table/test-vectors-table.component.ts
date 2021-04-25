import {Component, OnInit} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';
import {TestVectorsService} from '../../../@core/services/test-vectors.service';
import {BtnActionsComponent} from './btnActions.component';

@Component({
  selector: 'ngx-test-vectors-table',
  templateUrl: './test-vectors-table.component.html',
  styleUrls: ['./test-vectors-table.component.scss']
})
export class TestVectorsTableComponent implements OnInit {

  settings = {
    columns: {
      fileName: {
        title: 'Name',
        type: 'string',
      },
      hash: {
        title: 'Hash',
        type: 'string',
      },
      registrationTime: {
        title: 'Registered at',
        type: 'string',
      },
      lastUpdate: {
        title: 'Last update',
        type: 'string',
      },
      url: {
        title: 'Actions',
        filter: false,
        type: 'custom',
        renderComponent: BtnActionsComponent,
        onComponentInitFunction:
          (instance: any) => {
            instance.showPdf.subscribe(row => {
              window.open(row.url, '_blank');
            });
          }
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

  constructor(private testVectorsService: TestVectorsService) {
    this.testVectorsService.findAll().subscribe(t => {
      this.source.load(t);
    });
  }


  ngOnInit(): void {
  }

}
