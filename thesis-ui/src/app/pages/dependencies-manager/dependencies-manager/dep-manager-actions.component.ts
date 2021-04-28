import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

import { ViewCell } from 'ng2-smart-table';

@Component({
  template: `
    <div class="btn-group-vertical">
      <button type="button" style="cursor: pointer;" class="btn btn-success" (click)="showDetails.emit(rowData)">Details</button>
    </div>
  `,
})
export class DepManagerActionsComponent implements ViewCell {

  @Input() value: string;
  @Output() showDetails: EventEmitter<any> = new EventEmitter<any>();

  rowData: any;

}
