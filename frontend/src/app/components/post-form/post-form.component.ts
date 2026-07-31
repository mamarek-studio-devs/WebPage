import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { BlogService, BlogPost } from '../../services/blog.service';

@Component({
  selector: 'app-post-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  template: `
    <div class="form-container">
      <h1>{{ isEditMode ? 'Edit Post' : 'Create New Post' }}</h1>
      
      <div *ngIf="error" class="error">{{ error }}</div>
      
      <form (ngSubmit)="submitForm()" class="post-form">
        <div class="form-group">
          <label for="title">Title</label>
          <input
            id="title"
            [(ngModel)]="post.title"
            name="title"
            type="text"
            required
            placeholder="Enter post title"
          />
        </div>
        
        <div class="form-group">
          <label for="content">Content</label>
          <textarea
            id="content"
            [(ngModel)]="post.content"
            name="content"
            required
            placeholder="Enter post content"
            rows="15"
          ></textarea>
        </div>
        
        <div class="actions">
          <button type="submit" [disabled]="loading" class="btn-submit">
            {{ loading ? 'Saving...' : (isEditMode ? 'Update Post' : 'Create Post') }}
          </button>
          <a routerLink="/posts" class="btn-cancel">Cancel</a>
        </div>
      </form>
    </div>
  `,
  styles: [`
    .form-container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
    }
    
    h1 {
      margin-bottom: 20px;
      color: #333;
    }
    
    .error {
      background: #ffebee;
      color: red;
      padding: 15px;
      border-radius: 4px;
      margin-bottom: 20px;
    }
    
    .post-form {
      background: #f9f9f9;
      border: 1px solid #ddd;
      border-radius: 4px;
      padding: 30px;
    }
    
    .form-group {
      margin-bottom: 25px;
    }
    
    label {
      display: block;
      margin-bottom: 8px;
      color: #333;
      font-weight: 500;
    }
    
    input[type="text"],
    textarea {
      width: 100%;
      padding: 12px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-family: Arial, sans-serif;
      font-size: 14px;
      box-sizing: border-box;
    }
    
    input[type="text"]:focus,
    textarea:focus {
      outline: none;
      border-color: #2196F3;
      box-shadow: 0 0 5px rgba(33, 150, 243, 0.3);
    }
    
    textarea {
      resize: vertical;
    }
    
    .actions {
      display: flex;
      gap: 10px;
      margin-top: 30px;
    }
    
    button, a {
      padding: 12px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
      text-decoration: none;
    }
    
    .btn-submit {
      background: #4CAF50;
      color: white;
    }
    
    .btn-submit:hover:not(:disabled) {
      background: #45a049;
    }
    
    .btn-submit:disabled {
      background: #ccc;
      cursor: not-allowed;
    }
    
    .btn-cancel {
      background: #9E9E9E;
      color: white;
      display: inline-block;
    }
    
    .btn-cancel:hover {
      background: #757575;
    }
  `]
})
export class PostFormComponent implements OnInit {
  post: BlogPost = { title: '', content: '' };
  isEditMode = false;
  loading = false;
  error: string | null = null;
  postId: number | null = null;

  constructor(
    private blogService: BlogService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.postId = params['id'] || null;
      this.isEditMode = !!this.postId;
      
      if (this.isEditMode && this.postId) {
        this.loadPost();
      }
    });
  }

  loadPost() {
    if (!this.postId) return;
    
    this.blogService.getPostById(this.postId).subscribe({
      next: (data) => {
        this.post = data;
      },
      error: (err) => {
        this.error = 'Failed to load post';
        console.error(err);
      }
    });
  }

  submitForm() {
    if (!this.post.title.trim() || !this.post.content.trim()) {
      this.error = 'Title and content are required';
      return;
    }
    
    this.loading = true;
    this.error = null;
    
    const operation = this.isEditMode && this.postId
      ? this.blogService.updatePost(this.postId, this.post)
      : this.blogService.createPost(this.post);
    
    operation.subscribe({
      next: (data) => {
        this.router.navigate(['/posts', data.id]);
      },
      error: (err) => {
        this.error = 'Failed to save post';
        this.loading = false;
        console.error(err);
      }
    });
  }
}
