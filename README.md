# Banking Transaction System

A simple Java + DynamoDB project to demonstrate account operations (deposit, withdraw) and transaction history.

---

## Features
- Account model stored in DynamoDB
- Deposit / Withdraw with balance updates
- Transaction history (Transaction table)
- Unit tests with JUnit 5 and Mockito (no AWS required)
- Built with Maven

---

## Tech Stack
- Java 17 (Temurin)
- Maven 3.9.x
- AWS SDK v2 (DynamoDB Enhanced Client)
- JUnit 5, Mockito

---

## Repository Layout
```
docs/
 ├── diagrams/
 │    ├── architecture.puml / .png
 │    └── er-diagram.puml / .png
 ├── project-brief.md
 └── project-journal.md
src/
 ├── main/java/com/atlas/banking/...   # app code (services, repos, models, Main)
 └── test/java/com/atlas/banking/...   # unit tests
pom.xml
README.md
```

---

## Build & Run

Clean and build the project:

```bash
mvn clean install
```

Run the app (prints skeleton message):

```bash
mvn -q -DskipTests exec:java -Dexec.mainClass=com.atlas.banking.Main
```

Expected output:

```
Banking Transaction System – Skeleton Ready
```

---

## Tests

Run unit tests:

```bash
mvn test
```

All tests run locally with Mockito (no AWS credentials needed).  
Covers deposit, withdraw, and insufficient funds scenarios.

---

## Diagrams
- [ER Diagram](docs/diagrams/er-diagram.png)
- [Architecture Diagram](docs/diagrams/architecture.png)

---

## Documentation
- [Project Brief (MVP v0.1)](docs/project-brief.md)  
- [Project Journal (Day 1 → Day 9+)](docs/project-journal.md)  
- [Diagrams](docs/diagrams/)

---

## License
MIT
