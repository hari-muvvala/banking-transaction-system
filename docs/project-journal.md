# Project Journal

## Day 1
- Created repo and added MVP brief (FR/NFR).

**Next step:**
- design Accounts table (partition key only) and create Java project skeleton.

## Day 2
- Created `Accounts` table in DynamoDB (Partition key: `accountId` of type String).
- Added first sample item:
	- accountId: "acc-1001"
	- accountHoldername: "Hari Gopal"
	- balance = 1000
- Verified by scanning items in the table.

**Next step:**
- Scaffold Java project skeleton (Maven/Gradle structure).
- Prepare code to connect to DynamoDB.

## Day 3
- Scaffolded Java project using Maven: created `src/main/java` structure and added a `Main` class that prints “Banking Transaction System – Skeleton Ready” to verify the build.
- Added Maven configuration (`pom.xml`) with Java 17 settings and AWS SDK dependencies for DynamoDB.
- Implemented `DynamoPing` class to instantiate a DynamoDB client and test connectivity. Configured AWS credentials and region via IntelliJ run configuration. Running `DynamoPing` printed “DynamoDB client created successfully in region ap-south-1”.

**Next step:**
- Implement the `Account` model and a repository class to save/load accounts.

## Day 4
- Implemented `Account` model as a DynamoDB bean (`@DynamoDbBean`, partition key `accountId`) with attributes for `accountHolderName` and `balance`.
- Created `AccountRepository` using the enhanced DynamoDB client: methods `put` to save an account and `get` to load by `accountId`.
- Wrote `AccountSmokeTest` which creates a sample account (`acc-1002`), saves it, and loads it back. The test printed the expected ID, name, and balance, confirming the round-trip.
- Configured run environments to pass AWS credentials and region at runtime.
- Committed and pushed changes with message `MVP: add Account model, repository, smoke test, and DynamoDB connection`.

**Next step:**
- Implement deposit/withdraw operations that update the account balance, and start drafting ER diagrams.

## Day 5
**What I did**
- Implemented `AccountService` class to support deposit and withdraw operations.
- Modified `AccountSmokeTest` to:
  - Create account (`acc-1002`, initial balance 500.0).
  - Deposit 200 and withdraw 50 using the service.
  - Verified updated balance (650.0) by reading account again.
  - Verified end-to-end flow of repository + service + smoke test in DynamoDB.

**Why it matters**
- Encapsulates account operations in a dedicated service class.
- Updates balances through repository layer, keeping code cleaner.

**Next step**
- Start drafting system diagrams (ER diagram and high-level architecture).

## Day 6
**What I did**
- Added **ER Diagram** (`er-diagram.puml` + `er-diagram.png`) showing:
  - `Account` entity (accountId PK, accountHolderName, balance, status, createdAt).
  - `Transaction` entity (transactionId PK, accountId FK, type, amount, status, timestamp).
  - `AuditLog` entity (logId PK, entity, entityId, action, details, timestamp).
  - Relationships: Account has Transactions, Account/Transaction has AuditLogs.
- Added **Architecture Diagram** (`architecture.puml` + `architecture.png`) showing flow:
  - User → AccountService/Main → AccountRepository → DynamoDB (Accounts table).
  - Run/Debug passes AWS credentials via environment variables.
- Committed both diagrams into `docs/diagrams/`.

**Why it matters**
- ER diagram = focus on logical data model (Accounts, Transactions, AuditLogs).
- Architecture diagram = focus on system flow and responsibilities.

**Next step**
- Add atomic balance update logic in `AccountService` using DynamoDB conditional writes.
- Extend design for Transaction and AuditLog entities.