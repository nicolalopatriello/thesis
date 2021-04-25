import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

import { ViewCell } from 'ng2-smart-table';

@Component({
  template: `
    <div class="btn-group-vertical">
      <button type="button" style="cursor: pointer;" class="btn btn-success" (click)="showPdf.emit(rowData)">Show</button>
<!--
      <button type="button" style="cursor: pointer;" class="btn btn-info" (click)="showRaw.emit(true)">TXT</button>
-->
    </div>
  `,
})
export class BtnActionsComponent implements ViewCell {

  @Input() value: string;
  @Output() showPdf: EventEmitter<any> = new EventEmitter<any>();
  @Output() showRaw: EventEmitter<any> = new EventEmitter<any>();

  rowData: any;

}
