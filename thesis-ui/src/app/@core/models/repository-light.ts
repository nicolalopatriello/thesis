export interface RepositoryLight {
  id: number;
  url: string;
  branch: string;
  lastCommitSha: string;
  runnerId: number;
  runnerStartedAt: number;
  runnerFinishedAt: number;
  reciepe: any;
  minutesWatchersInterval: number;
}
