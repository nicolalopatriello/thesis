import {GitProvider} from './gitrace';

export interface Connection {
  id?: number;
  gitProvider: GitProvider;
  endpoint: string;
  name: string;
  token: string;
}


