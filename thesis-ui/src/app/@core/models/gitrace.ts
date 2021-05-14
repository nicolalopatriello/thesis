export interface Gitrace {
  id?: number;
  gitRepoUrl: string;
  gitProvider: GitProvider;
  connectionId?: number;
  gitDescription: string;
}


export enum GitProvider {
  GITHUB = 'GITHUB',
  GITLAB = 'GITLAB'
}
