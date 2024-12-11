# Darts Fighters

## Overview
This project is fully containerized and managed using Docker Compose. The setup includes a frontend and a backend service, along with a Swagger UI for API documentation.

## Getting Started

### Prerequisites
Before running the project, make sure you have the following dependencies installed:

#### Windows
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (includes Docker Compose)
  - Ensure WSL 2 is enabled and configured if you're using Windows 10/11.

#### Linux
- [Docker Engine](https://docs.docker.com/engine/install/)
- [Docker Compose](https://docs.docker.com/compose/install/)

### Running the Project

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```

2. Start the project using Docker Compose:
   ```bash
   docker-compose up
   ```

   This command will build and start all the containers.

3. Access the services:
   - **Frontend**: [http://localhost:6500](http://localhost:6500)
   - **Backend**: [http://localhost:6501](http://localhost:6501)
   - **Swagger UI**: [http://localhost:6501/swagger-ui/index.html](http://localhost:6501/swagger-ui/index.html)

### Stopping the Project
To stop the running containers, press `Ctrl+C` in the terminal where `docker-compose up` is running or use:
```bash
docker-compose down
```