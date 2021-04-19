import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { ECommerceComponent } from './_to_remove/e-commerce/e-commerce.component';
import { NotFoundComponent } from './_to_remove/miscellaneous/not-found/not-found.component';

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'dashboard',
      component: ECommerceComponent,
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
      path: 'dependencies-manager',
      loadChildren: () => import('./dependencies-manager/dependencies-manager.module')
        .then(m => m.DependenciesManagerModule),
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    },
    {
      path: '**',
      component: NotFoundComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
