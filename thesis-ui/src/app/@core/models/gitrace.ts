export interface Gitrace {
  id?: number;
  gitRepoUrl: string;
  gitProvider: GitProvider;
  token?: string;
  gitDescription: string;
}


export enum GitProvider {
  GITHUB = 'GITHUB',
  GITLAB = 'GITLAB'
}
