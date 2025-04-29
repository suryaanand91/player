# player
This project is a Spring Boot-based REST API designed to manage player registration, authentication, and session enforcement with daily time limit controls. It enables a structured approach to handling user sessions with built-in constraints on allowed daily active time.

#Key Features:
* Player Registration:
Register a new player with essential personal details: email, password, name, surname, date of birth, and address.
* Login & Session Creation:
Authenticate players with their email and password. On successful login, a session is created and stored.
* Logout Functionality:
Players can logout via a session identifier, marking the end of an active session.
* Daily Time Limit Enforcement:
Admins can set a daily active time limit per player. A player:
Cannot log in after exceeding the limit.
Is automatically logged out when their session crosses the configured limit.
* Limit Constraints:
A time limit can only be set for active players.
Sessions are checked regularly using a scheduled job (@Scheduled) to enforce limits.
H2 Database (File-based):
A lightweight file-based H2 database is used for easy local testing and development.
Dockerized Setup:
The entire application is containerized using Docker, simplifying deployment and testing.


Tech Stack:
Java 17+
Spring Boot 3
Spring Web, Spring Data JPA
H2 File-Based Database
Docker
