import {Component, Input} from '@angular/core';

@Component({
  selector: 'ngx-card',
  styleUrls: ['./card.component.scss'],
  templateUrl: './card.component.html',
})

export class CardComponent {

  @Input() value: number;
  @Input() entity: string;
  @Input() icon: string;

}
