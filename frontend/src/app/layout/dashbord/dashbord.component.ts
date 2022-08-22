import { Component, OnInit } from '@angular/core';
import { AuthServiceService } from 'src/app/services/auth-service.service';
import { PostServiceService } from 'src/app/services/post-service.service';
import { LogoutRequestPayload } from '../header/logout-request.payload';
import { PostModel } from './PostModel';

@Component({
  selector: 'app-dashbord',
  templateUrl: './dashbord.component.html',
  styleUrls: ['./dashbord.component.css']
})
export class DashbordComponent implements OnInit {

  posts:Array<PostModel> = [];

  constructor(private postService:PostServiceService,private authService:AuthServiceService) { }

  ngOnInit(): void {

    if(this.authService.isUserLoggedIn())
    {
      this.postService.getAllPosts().subscribe(data => {
  
        console.log(data);
        this.posts = data;
      })
    }
    
  }
}
