export interface UserTest {
  id?: number;
  createdAt?: string;
  url: string;
  description: string;
  gitraceDep: number[];
  testVectorsDep: number[];
}
