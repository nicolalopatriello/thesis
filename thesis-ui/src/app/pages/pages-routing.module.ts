import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'dashboard',
      loadChildren: () => import('./dashboard/dashboard.module')
        .then(m => m.DashboardModule),
    },
    {
      path: 'test-vectors',
      loadChildren: () => import('./test-vectors/test-vectors.module')
        .then(m => m.TestVectorsModule),
    },
    {
      path: 'repositories',
      loadChildren: () => import('./repositories/repositories.module')
        .then(m => m.RepositoriesModule),
    },
    {
      path: 'moon-cloud-probes',
      loadChildren: () => import('./user-test/user-test.module')
        .then(m => m.UserTestModule),
    },
    {
      path: 'notifications',
      loadChildren: () => import('./notifications/notifications.module')
        .then(m => m.NotificationsModule),
    },
    {
      path: 'connections',
      loadChildren: () => import('./connections/connections.module')
        .then(m => m.ConnectionsModule),
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
