import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private router: Router, private userService:UserService) { }

  ngOnInit() {

  }

  logout(){
    this.userService.logout();
    this.router.navigateByUrl('/user/login');
  }

}
