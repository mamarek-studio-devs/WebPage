# Blog Application

A simple blog webpage built with **Spring Boot** (backend) and **Angular** (frontend).

## Features

- **Create** blog posts with title and content
- **Read** all posts or view individual post details
- **Update** existing posts
- **Delete** posts
- Clean and responsive UI

## Prerequisites

- Java 25+ (for backend)
- Node.js 18+ (for frontend)
- npm or yarn (for frontend dependencies)
- Git

## Project Structure

```
├── backend/          # Spring Boot REST API
│   ├── src/
│   │   ├── main/java/pl/mamarek/backend/
│   │   │   ├── controller/      # REST endpoints
│   │   │   ├── model/           # Entity classes
│   │   │   └── repository/      # JPA repositories
│   │   └── resources/
│   │       └── application.yaml # Configuration
│   └── build.gradle             # Gradle build file
│
└── frontend/         # Angular web application
    ├── src/
    │   ├── app/
    │   │   ├── services/        # Blog service (API calls)
    │   │   └── components/      # Blog components
    │   └── styles.css           # Global styles
    └── package.json             # NPM dependencies
```

## Setup and Running

### Backend Setup

1. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Build the project:**
   ```bash
   ./gradlew build
   ```

3. **Run the Spring Boot application:**
   ```bash
   ./gradlew bootRun
   ```

   The backend will start on `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory:**
   ```bash
   cd frontend
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start the development server:**
   ```bash
   npm start
   ```

   The frontend will start on `http://localhost:4200`

4. **Open your browser** and navigate to `http://localhost:4200`

## API Endpoints

### Blog Posts

- **GET** `/api/posts` - Get all blog posts
- **GET** `/api/posts/{id}` - Get a specific post by ID
- **POST** `/api/posts` - Create a new post
  ```json
  {
    "title": "Post Title",
    "content": "Post content"
  }
  ```
- **PUT** `/api/posts/{id}` - Update an existing post
  ```json
  {
    "title": "Updated Title",
    "content": "Updated content"
  }
  ```
- **DELETE** `/api/posts/{id}` - Delete a post

## Frontend Routes

- `/posts` - View all blog posts (default)
- `/posts/new` - Create a new post
- `/posts/{id}` - View a specific post
- `/posts/{id}/edit` - Edit a post

## Database

The application uses **H2 in-memory database**. Data is stored in memory and will be lost when the application restarts. 

To switch to a persistent database (e.g., PostgreSQL), update `application.yaml` with the appropriate database configuration.

## Technology Stack

### Backend
- Spring Boot 4.1.0
- Spring Data JPA
- H2 Database
- Java 25

### Frontend
- Angular 22.1.0
- TypeScript
- Standalone Components
- RxJS for reactive programming

## Development Notes

- CORS is enabled for `http://localhost:4200` on the backend
- The frontend uses Angular's standalone components (no NgModules)
- HTTP client uses the Fetch API
- All responses include timestamps (createdAt, updatedAt)

## Troubleshooting

**Backend won't start:**
- Ensure Java 25+ is installed: `java -version`
- Check if port 8080 is already in use
- Try cleaning the build: `./gradlew clean build`

**Frontend can't connect to backend:**
- Ensure backend is running on `http://localhost:8080`
- Check browser console for CORS errors
- Verify Angular is running on `http://localhost:4200`

**Frontend not loading:**
- Clear npm cache: `npm cache clean --force`
- Reinstall dependencies: `rm -rf node_modules && npm install`

## Future Enhancements

- User authentication and authorization
- Categories/tags for posts
- Comments on posts
- Full-text search
- Markdown support
- Post scheduling
- User profiles
