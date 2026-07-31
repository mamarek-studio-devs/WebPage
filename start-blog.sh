#!/bin/bash

# Quick Start Script for Blog Application
# This script will start both backend and frontend in separate terminal windows

echo "========================================="
echo "Blog Application - Quick Start"
echo "========================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if Java is installed
echo -e "${YELLOW}Checking Java installation...${NC}"
if ! command -v java &> /dev/null; then
    echo -e "${RED}Java is not installed. Please install Java 25+ first.${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Java found${NC}"

# Check if Node.js is installed
echo -e "${YELLOW}Checking Node.js installation...${NC}"
if ! command -v node &> /dev/null; then
    echo -e "${RED}Node.js is not installed. Please install Node.js 18+ first.${NC}"
    exit 1
fi
echo -e "${GREEN}✓ Node.js found${NC}"

echo ""
echo -e "${YELLOW}Starting Backend...${NC}"
cd backend
./gradlew bootRun &
BACKEND_PID=$!
echo -e "${GREEN}✓ Backend started (PID: $BACKEND_PID)${NC}"

# Wait a bit for backend to start
sleep 5

echo ""
echo -e "${YELLOW}Starting Frontend...${NC}"
cd ../frontend
npm install --silent 2>/dev/null
npm start &
FRONTEND_PID=$!
echo -e "${GREEN}✓ Frontend started (PID: $FRONTEND_PID)${NC}"

echo ""
echo -e "${GREEN}=========================================${NC}"
echo -e "${GREEN}Blog Application is running!${NC}"
echo -e "${GREEN}=========================================${NC}"
echo ""
echo "Frontend: http://localhost:4200"
echo "Backend:  http://localhost:8080"
echo "H2 Console: http://localhost:8080/h2-console"
echo ""
echo "Press Ctrl+C to stop both services..."
echo ""

# Wait for both processes
wait $BACKEND_PID
wait $FRONTEND_PID
