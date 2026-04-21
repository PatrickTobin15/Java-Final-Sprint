# Gym Management System

A Java console application for managing gym users, memberships, workout classes, and merchandise. Built with PostgreSQL and BCrypt.

## Tech Stack
- Java 17
- Maven (dependency management)
- PostgreSQL (database)
- jBCrypt (password hashing)
- Java Logging API (logging to file)

## Project Structure
```
GymManagement/
├── pom.xml
├── sql/
│   └── schema.sql          ← Running this first will setup the database
├── PRESENTATION_SCRIPT.txt
└── src/main/java/com/gym/
    ├── model/              ← User, Admin, Trainer, Member, Membership, WorkoutClass, GymMerch
    ├── dao/                ← UserDAO, MembershipDAO, WorkoutClassDAO, GymMerchDAO
    ├── service/            ← UserService, MembershipService, WorkoutClassService, GymMerchService
    ├── ui/                 ← Main, AdminMenu, TrainerMenu, MemberMenu
    └── util/               ← DatabaseConnection, AppLogger
```

## Setup Instructions

### 1. PostgreSQL Setup
1. Open pgAdmin or psql
2. Create a database: `CREATE DATABASE gym_db;`
3. Run the schema: `psql -U postgres -d gym_db -f sql/schema.sql`

### 2. Configure Database Password
Open `src/main/java/com/gym/util/DatabaseConnection.java` and update:
```java
private static final PASSWORD = "postgres"; // ← change this to your password
```

### 3. Build & Run (VS Code)
Make sure you have the **Extension Pack for Java** and **Maven for Java** installed in VS Code.

```bash
# In the GymManagement folder:
mvn clean package
mvn exec:java -Dexec.mainClass="com.gym.ui.Main"
```

Or if using VS Code run `Main.java` directly using the Run button.

## Default Test Accounts
| Username  | Password    | Role    |
|-----------|-------------|---------|
| admin1    | password123 | Admin   |
| trainer1  | password123 | Trainer |
| member1   | password123 | Member  |

> **Note:** The data in schema.sql uses a pre-hashed BCrypt hash for `password123`.
> When you register new users through the app, passwords are hashed automatically.

## Logging
All activity is written to `gym_app.log` in the root directory.
