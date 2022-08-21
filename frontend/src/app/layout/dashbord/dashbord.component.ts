import { Component, OnInit } from '@angular/core';
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

  constructor(private postService:PostServiceService) { }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe(data => {
      console.log(data);
      this.posts = data;
    })
    
  }
}
