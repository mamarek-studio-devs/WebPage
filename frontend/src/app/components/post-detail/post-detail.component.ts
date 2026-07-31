import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { BlogService, BlogPost } from '../../services/blog.service';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="post-detail-container">
      <div *ngIf="loading" class="loading">Loading post...</div>
      
      <div *ngIf="!loading && error" class="error">{{ error }}</div>
      
      <div *ngIf="!loading && post" class="post-content">
        <h1>{{ post.title }}</h1>
        <small class="date">Posted on {{ post.createdAt | date:'medium' }}</small>
        <div *ngIf="post.updatedAt !== post.createdAt" class="updated">
          Updated on {{ post.updatedAt | date:'medium' }}
        </div>
        
        <div class="content">
          {{ post.content }}
        </div>
        
        <div class="actions">
          <a routerLink="/posts" class="btn-back">← Back to Posts</a>
          <a [routerLink]="['/posts', post.id, 'edit']" class="btn-edit">Edit</a>
          <button (click)="deletePost()" class="btn-delete">Delete</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .post-detail-container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
    }
    
    .loading, .error {
      text-align: center;
      padding: 20px;
      font-size: 16px;
    }
    
    .error {
      color: red;
      background: #ffebee;
      border-radius: 4px;
    }
    
    .post-content {
      background: #f9f9f9;
      border: 1px solid #ddd;
      border-radius: 4px;
      padding: 30px;
    }
    
    h1 {
      margin: 0 0 15px 0;
      color: #333;
    }
    
    .date, .updated {
      display: block;
      color: #666;
      font-size: 14px;
      margin-bottom: 5px;
    }
    
    .content {
      margin: 30px 0;
      line-height: 1.8;
      color: #333;
      white-space: pre-wrap;
    }
    
    .actions {
      display: flex;
      gap: 10px;
      margin-top: 30px;
      padding-top: 20px;
      border-top: 1px solid #ddd;
    }
    
    .actions a, .actions button {
      padding: 10px 15px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      text-decoration: none;
      font-size: 14px;
    }
    
    .btn-back {
      background: #9E9E9E;
      color: white;
    }
    
    .btn-back:hover {
      background: #757575;
    }
    
    .btn-edit {
      background: #FF9800;
      color: white;
    }
    
    .btn-edit:hover {
      background: #e68900;
    }
    
    .btn-delete {
      background: #f44336;
      color: white;
    }
    
    .btn-delete:hover {
      background: #da190b;
    }
  `]
})
export class PostDetailComponent implements OnInit {
  post: BlogPost | null = null;
  loading = true;
  error: string | null = null;
  postId: number | null = null;

  constructor(
    private blogService: BlogService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.postId = params['id'];
      if (this.postId) {
        this.loadPost();
      }
    });
  }

  loadPost() {
    if (!this.postId) return;
    
    this.loading = true;
    this.error = null;
    this.blogService.getPostById(this.postId).subscribe({
      next: (data) => {
        this.post = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load post';
        this.loading = false;
        console.error(err);
      }
    });
  }

  deletePost() {
    if (this.postId && confirm('Are you sure you want to delete this post?')) {
      this.blogService.deletePost(this.postId).subscribe({
        next: () => {
          window.location.href = '/posts';
        },
        error: (err) => {
          this.error = 'Failed to delete post';
          console.error(err);
        }
      });
    }
  }
}
