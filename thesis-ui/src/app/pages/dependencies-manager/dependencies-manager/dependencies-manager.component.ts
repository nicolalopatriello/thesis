import { Component, OnInit } from '@angular/core';
import {GitraceService} from "../../../@core/services/gitrace.service";
import {Gitrace} from "../../../@core/models/gitrace";
import {TestVectorsService} from "../../../@core/services/test-vectors.service";
import {TestVector} from "../../../@core/models/test-vector";

@Component({
  selector: 'ngx-dependencies-manager',
  templateUrl: './dependencies-manager.component.html',
  styleUrls: ['./dependencies-manager.component.scss']
})
export class DependenciesManagerComponent implements OnInit {
  private gitraces: Array<Gitrace>;
  private testVectors: Array<TestVector>;

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

  }

}
