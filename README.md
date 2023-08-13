# Trello Board Application

This project is a Trello-like board application developed using Spring Boot for the backend API and vanilla JavaScript for the frontend user interface. It allows users to create boards, manage cards within boards, and move cards between different sections.

## Backend

The backend of the application is developed using Spring Boot and provides REST APIs for board and card management. It includes the following features:

- Create, Read, Update, and Delete boards.
- Create, Read, Update, and Delete cards within boards.
- Move cards between different sections.


## Frontend

The frontend of the application is developed using vanilla JavaScript and interacts with the backend APIs to provide a user-friendly interface for managing boards and cards. It includes the following features:

- Display boards and cards with sections.
- Create new cards within boards.
- Update card details and move them between sections.
- Delete cards from boards.


## Docker

The application can be easily containerized using Docker. The `docker-compose.yml` file provided in the root directory sets up a multi-container environment with services for the database, backend, and frontend.
