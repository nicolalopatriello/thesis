import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryDetails} from '../../../@core/models/repository-details';

@Component({
  selector: 'ngx-repository-details',
  templateUrl: './repository-details.component.html',
  styleUrls: ['./repository-details.component.scss']
})
export class RepositoryDetailsComponent implements OnInit {
  public repository: RepositoryDetails;
  selectedDepIdx;
  selectedVulnIdx;
  selectedMetricIdx;

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.repository = this.activatedRoute.snapshot.data?.repository;
  }

  back() {
    this.router.navigate(['pages', 'repositories']);
  }
}
