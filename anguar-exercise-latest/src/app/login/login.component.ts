import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from  '@angular/forms';
import { Router } from  '@angular/router';
import {UserService} from '../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isSubmitted  =  false;

  constructor(private formBuilder: FormBuilder,private router: Router,private userService: UserService) { }

  ngOnInit() {
    console.log("ngOnInit() method called from login component ");
    if(localStorage.getItem('ACCESS_TOKEN') !== null){
      this.router.navigateByUrl('/user/dashboard');
    }

    this.loginForm  =  this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', [Validators.required,Validators.minLength(6)]]
  });
    
  }

  get formControls() { 
    return this.loginForm.controls;
  }

  doLogin(e: any){
    console.log(this.loginForm.value);
    console.log(this.loginForm);
    this.isSubmitted = true;
    if(this.loginForm.invalid){
      return;
    }
    this.userService.login(this.loginForm.value);
    this.router.navigateByUrl('/user/dashboard');
  }

}
