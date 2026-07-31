import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BlogService, BlogPost } from '../../services/blog.service';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="post-list-container">
      <h1>Blog Posts</h1>
      <a routerLink="/posts/new" class="btn-new">+ New Post</a>
      
      <div *ngIf="loading" class="loading">Loading posts...</div>
      
      <div *ngIf="!loading && error" class="error">{{ error }}</div>
      
      <div *ngIf="!loading && posts.length === 0" class="no-posts">
        No posts yet. <a routerLink="/posts/new">Create one!</a>
      </div>
      
      <div class="posts">
        <div *ngFor="let post of posts" class="post-card">
          <h2>{{ post.title }}</h2>
          <p class="preview">{{ post.content | slice:0:150 }}...</p>
          <small class="date">{{ post.createdAt | date:'short' }}</small>
          <div class="actions">
            <a [routerLink]="['/posts', post.id]" class="btn-view">View</a>
            <a [routerLink]="['/posts', post.id, 'edit']" class="btn-edit">Edit</a>
            <button (click)="deletePost(post.id!)" class="btn-delete">Delete</button>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .post-list-container {
      max-width: 900px;
      margin: 0 auto;
      padding: 20px;
    }
    
    h1 {
      margin-bottom: 20px;
      color: #333;
    }
    
    .btn-new {
      display: inline-block;
      background: #4CAF50;
      color: white;
      padding: 10px 20px;
      border-radius: 4px;
      text-decoration: none;
      margin-bottom: 20px;
    }
    
    .btn-new:hover {
      background: #45a049;
    }
    
    .loading, .error, .no-posts {
      text-align: center;
      padding: 20px;
      font-size: 16px;
    }
    
    .error {
      color: red;
      background: #ffebee;
      border-radius: 4px;
    }
    
    .posts {
      display: grid;
      gap: 20px;
    }
    
    .post-card {
      border: 1px solid #ddd;
      border-radius: 4px;
      padding: 20px;
      background: #f9f9f9;
    }
    
    .post-card h2 {
      margin: 0 0 10px 0;
      color: #333;
    }
    
    .preview {
      color: #666;
      margin: 10px 0;
      line-height: 1.5;
    }
    
    .date {
      display: block;
      color: #999;
      margin-bottom: 15px;
    }
    
    .actions {
      display: flex;
      gap: 10px;
    }
    
    .actions a, .actions button {
      padding: 8px 12px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      text-decoration: none;
      font-size: 14px;
    }
    
    .btn-view {
      background: #2196F3;
      color: white;
    }
    
    .btn-view:hover {
      background: #0b7dda;
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
export class PostListComponent implements OnInit {
  posts: BlogPost[] = [];
  loading = true;
  error: string | null = null;

  constructor(private blogService: BlogService) {}

  ngOnInit() {
    this.loadPosts();
  }

  loadPosts() {
    this.loading = true;
    this.error = null;
    this.blogService.getAllPosts().subscribe({
      next: (data) => {
        this.posts = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load posts';
        this.loading = false;
        console.error(err);
      }
    });
  }

  deletePost(id: number) {
    if (confirm('Are you sure you want to delete this post?')) {
      this.blogService.deletePost(id).subscribe({
        next: () => this.loadPosts(),
        error: (err) => {
          this.error = 'Failed to delete post';
          console.error(err);
        }
      });
    }
  }
}
