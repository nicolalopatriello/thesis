export interface RepositoryCreateRequest {
  url: string;
  username: string;
  password: string;
  branch: string;
  recipe: any;
  minutesWatchersInterval: number;
}
