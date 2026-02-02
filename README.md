# Online Chess Game

A real-time multiplayer chess game built with Spring Boot, React, and MongoDB.

## 🛠️ Tech Stack
-   **Backend**: Java 17, Spring Boot 3, Spring Security, WebSocket (planned), MongoDB.
-   **Frontend**: React, Vite, chess.js, react-chessboard, Axios.
-   **Database**: MongoDB.

## 📋 Prerequisites
Before running, ensure you have the following installed:
1.  **Java 17 Development Kit (JDK)**
2.  **Apache Maven** (Required to build the backend as the wrapper is not included)
3.  **Node.js & npm**
4.  **MongoDB Server** (Must be running on port 27017)

## 🚀 How to Run

### 1. Start the Database
Ensure your MongoDB service is running locally.
-   **Windows**: Task Manager -> Services -> MongoDB
-   **Linux/Mac**: `sudo systemctl start mongod`

### 2. Run the Backend (Server)
The backend runs on `http://localhost:8080`.

```bash
cd backend
mvn spring-boot:run
```

*Note: If `mvn` command is not found, you need to add Apache Maven to your system PATH.*

### 3. Run the Frontend (Client)
The frontend runs on `http://localhost:5173`.

```bash
cd frontend
npm install  # Install dependencies (only first time)
npm run dev
```

## 🎮 How to Play
1.  Open `http://localhost:5173`.
2.  Register a new account (e.g., `WhitePlayer`).
3.  Login.
4.  Click "Create New Game".
5.  Open a **Incognito Window** (or another browser).
6.  Go to `http://localhost:5173`.
7.  Register a second account (e.g., `BlackPlayer`).
8.  Login and you will see the game in the Lobby. Click "Join".
9.  Play chess! (Moves sync every few seconds).

## ⚠️ Troubleshooting
-   **Backend fails to start?** Check if MongoDB is running.
-   **'mvn' not recognized?** Install Maven from https://maven.apache.org/download.cgi
-   **Frontend API errors?** Ensure Backend is running on port 8080.
