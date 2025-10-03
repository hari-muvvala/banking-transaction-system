# Banking Transaction System

A simple Java + DynamoDB project to demonstrate account operations (deposit, withdraw) and transaction history.

## Features
- Account model stored in DynamoDB
- Deposit / Withdraw with balance updates
- Transaction history (Transaction table)
- Unit tests with JUnit 5 and Mockito (no AWS required)
- Built with Maven

## Tech Stack
- Java 17 (Temurin)
- Maven 3.9.x
- AWS SDK v2 (DynamoDB Enhanced Client)
- JUnit 5, Mockito

## Repository Layout
```
docs/
 ├── diagrams/
 │   ├── architecture.puml / .png
 │   └── er-diagram.puml / .png
 └── project-journal.md
src/
 ├── main/java/com/atlas/banking/...   # app code (services, repos, models, Main)
 └── test/java/com/atlas/banking/...   # unit tests
pom.xml
README.md
```

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

## Tests

Run unit tests:

```bash
mvn test
```

All tests run locally with Mockito (no AWS credentials required).  
Covers deposit, withdraw, and insufficient funds scenarios.

## Diagrams
- docs/diagrams/er-diagram.png
- docs/diagrams/architecture.png

## Project Journal
See `docs/project-journal.md` for daily progress (Day 1 → Day 9+).

## License
MIT
