import {Gitrace} from './gitrace';
import {TestVector} from './test-vector';

export interface UserTestWithDeps {
  url: string;
  description: string;
  createdAt: string;
  gitraceDep: Array<Gitrace>;
  testVectorDep: Array<TestVector>;
}
