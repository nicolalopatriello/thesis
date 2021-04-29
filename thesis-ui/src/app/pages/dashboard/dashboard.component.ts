import {Component, OnDestroy} from '@angular/core';
import {StatisticsService} from '../../@core/services/statistics.service';
import {Statistics} from '../../@core/models/statistics';


@Component({
  selector: 'ngx-dashboard',
  styleUrls: ['./dashboard.component.scss'],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  public statistics: Statistics;

  constructor(private statisticsService: StatisticsService) {
    this.statisticsService.find().subscribe(t => {
      this.statistics = t;
    });
  }

}
