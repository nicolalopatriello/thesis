import { Component, OnInit } from '@angular/core';
import {GitraceService} from '../../../@core/services/gitrace.service';
import {Gitrace} from '../../../@core/models/gitrace';
import {TestVectorsService} from '../../../@core/services/test-vectors.service';
import {TestVector} from '../../../@core/models/test-vector';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'ngx-dependencies-manager',
  templateUrl: './dependencies-manager.component.html',
  styleUrls: ['./dependencies-manager.component.scss']
})
export class DependenciesManagerComponent implements OnInit {
  public  gitraces: Array<Gitrace> = [];
  public testVectors: Array<TestVector> = [];
  public createFormGroup: FormGroup;

  public selectedGitraces: Array<number> = [];
  public selectedTestVectors: Array<number> = [];
  get createEnabled() {
    return this.createFormGroup.valid && (this.selectedTestVectors.length > 0 || this.selectedGitraces.length > 0);
  }

  constructor(private gitraceService: GitraceService,
              private testVectorsService: TestVectorsService,
              ) { }

  ngOnInit(): void {
    this.gitraceService.findAll().subscribe(t => {
      this.gitraces = t;
    });

    this.testVectorsService.findAll().subscribe(tv => {
      this.testVectors = tv;
    });

    this.createFormGroup = new FormGroup({
      url: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [])
    });

  }

  onGitracesCheckedChanges($event: boolean, gitraceId: number) {
    if ($event) {
      this.selectedGitraces.push(gitraceId);
    } else {
      this.selectedGitraces = this.selectedGitraces.filter(g => g !== gitraceId);
    }
  }

  onTestVectorsCheckedChanges($event: boolean, tvId: number) {
    if ($event) {
      this.selectedTestVectors.push(tvId);
    } else {
      this.selectedTestVectors = this.selectedTestVectors.filter(g => g !== tvId);
    }
  }

  createNewDepManagerEntity() {
    console.log('selectedGitraces');
    console.log(this.selectedGitraces);
    console.log('selectedTestVectors');
    console.log(this.selectedTestVectors);
  }
}
