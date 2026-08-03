# WebPage

A full-stack blog application with:

- **Backend:** Spring Boot 4 (REST API + H2)
- **Frontend:** Angular 22

## Project Structure

- `/backend` – Spring Boot API
- `/frontend` – Angular web app
- `/start-blog.sh` – script to start backend and frontend

## Prerequisites

- Java 25+
- Node.js 18+
- npm

## Run the Application

### Option 1: Quick start script

```bash
./start-blog.sh
```

### Option 2: Start services manually

1. Start backend:

   ```bash
   cd backend
   ./gradlew bootRun
   ```

2. Start frontend:

   ```bash
   cd frontend
   npm install
   npm start
   ```

## Local URLs

- Frontend: http://localhost:4200
- Backend API: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console

## API Endpoints

- `GET /api/posts`
- `GET /api/posts/{id}`
- `POST /api/posts`
- `PUT /api/posts/{id}`
- `DELETE /api/posts/{id}`

## Development

- Backend tests:

  ```bash
  cd backend
  ./gradlew test
  ```

- Frontend tests:

  ```bash
  cd frontend
  npm test
  ```
