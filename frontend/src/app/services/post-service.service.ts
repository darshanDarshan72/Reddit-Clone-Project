import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';
import { PostModel } from '../layout/dashbord/PostModel';
import { AuthServiceService } from './auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class PostServiceService {

  constructor(private http:HttpClient,private authService:AuthServiceService) { }


  getAllPosts() : Observable<Array<PostModel>>{
    return this.http.get<Array<PostModel>>('http://localhost:8080/api/posts');
  }
}
