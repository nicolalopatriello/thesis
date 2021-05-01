import {GitProvider} from './gitrace';

export interface UserTest {
  id?: number;
  createdAt?: string;
  gitRepoUrl: string;
  gitProvider: GitProvider;
  description: string;
  gitraceDep: number[];
  testVectorsDep: number[];
}
