import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  template: `
    <nav class="navbar">
      <div class="nav-container">
        <h1 class="logo">Blog</h1>
        <ul class="nav-links">
          <li><a href="/posts">All Posts</a></li>
          <li><a href="/posts/new">New Post</a></li>
        </ul>
      </div>
    </nav>
    <main>
      <router-outlet></router-outlet>
    </main>
  `,
  styles: [`
    :host {
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }
    
    .navbar {
      background: #333;
      color: white;
      padding: 0;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }
    
    .nav-container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 15px 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    
    .logo {
      margin: 0;
      font-size: 24px;
      font-weight: bold;
    }
    
    .nav-links {
      display: flex;
      list-style: none;
      margin: 0;
      padding: 0;
      gap: 20px;
    }
    
    .nav-links a {
      color: white;
      text-decoration: none;
      transition: color 0.3s;
    }
    
    .nav-links a:hover {
      color: #4CAF50;
    }
    
    main {
      flex: 1;
      background: #f5f5f5;
    }
  `]
})
export class App {}
