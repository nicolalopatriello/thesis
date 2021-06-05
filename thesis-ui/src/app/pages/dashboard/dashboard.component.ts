import {Component, OnDestroy} from '@angular/core';
import {StatisticService} from '../../@core/services/statistic.service';
import {Statistic} from '../../@core/models/statistic';


@Component({
  selector: 'ngx-dashboard',
  styleUrls: ['./dashboard.component.scss'],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  public statistics: Statistic;

  constructor(private statisticsService: StatisticService) {
    this.statisticsService.find().subscribe(t => {
      this.statistics = t;
    });
  }

}
