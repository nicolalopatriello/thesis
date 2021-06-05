import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RepositoryDetails} from '../../../@core/models/repository-details';
import {interval, Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {NbDialogRef} from '@nebular/theme';
import {RepositoryService} from '../../../@core/services/repository.service';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'ngx-repository-details',
  templateUrl: './repository-details.component.html',
  styleUrls: ['./repository-details.component.scss']
})
export class RepositoryDetailsComponent implements OnInit, OnDestroy {
  private destroy$: Subject<boolean> = new Subject<boolean>();
  public repository: RepositoryDetails;
  selectedDepIdx;
  selectedVulnIdx;
  selectedMetricIdx;

  constructor(private activatedRoute: ActivatedRoute,
              private repositoryService: RepositoryService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.repository = this.activatedRoute.snapshot.data?.repository;
    interval(environment.pollingTime)
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.refresh();
      });
  }

  back() {
    this.router.navigate(['pages', 'repositories']);
  }


  ngOnDestroy(): void {
    this.destroy$.next(true);
  }

  private refresh() {
    this.repositoryService.findById(this.repository.id).subscribe(r => {
      this.repository = r;
    });
  }
}
