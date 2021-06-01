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
  selectedDepIdx = 0;
  selectedVulnIdx = 0;
   data = {
    datasets: [{
      label: 'CVE Score',
      data: [65],
      backgroundColor: [
        'rgba(255, 99, 132, 0.2)'
      ],
      borderColor: [
        'rgb(255, 99, 132)'
      ],
      borderWidth: 1
    }]
  };
  options: {
    scales: {
      c: {
        beginAtZero: true
      }
    }
  }

  constructor(private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.repository = this.activatedRoute.snapshot.data?.repository;
  }

  back() {
    this.router.navigate(['pages', 'repositories']);
  }
}
