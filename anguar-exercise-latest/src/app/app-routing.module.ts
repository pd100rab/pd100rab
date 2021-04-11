import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './services/auth.guard';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'user/login'},
  { path: 'user/login', loadChildren: () => import('./login/login.module').then(m => m.LoginModule) },
  { path: 'user/dashboard', loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule) , canActivate: [AuthGuard] }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule
  ]
})
export class AppRoutingModule { }
