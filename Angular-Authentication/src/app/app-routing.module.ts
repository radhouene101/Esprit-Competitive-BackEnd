import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

const routes: Routes = [
  {path:'signup',component:SignupComponent},
  {path:'login',component:LoginComponent},//hedhom nhot fehom link eli bech naayatlou fil bouton w yhezni l component eli nheb aaleha
  {path:'dashboard',component:DashboardComponent},//hedhom nhot fehom link eli bech naayatlou fil bouton w yhezni l component eli nheb aaleha


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
