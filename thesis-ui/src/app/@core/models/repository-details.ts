export interface RepositoryDetails {
  id: number;
  url: string;
  branch: string;
  lastCommitSha: string;
  dependencies: DependencyWithVulnerabilities[];
}

export interface DependencyWithVulnerabilities {
  id: number;
  name: string;
  version: string;
  programmingLanguage: string;
  vulnerabilities: Vulnerability[];
}

export interface Vulnerability {
  id: number;
  cveId: string;
  cvePublishedAt: number;
  cveModifiedAt: number;
  cvss: number;
  cvssVector: string;
  cvePatch: string;
  dependencyId: number;
  riskIndex: number;
}
