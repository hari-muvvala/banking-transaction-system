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
  - Verify updated balance (650.0) by reading account again.
- **Noted** that the current update pattern is **read → modify → put**. This is fine for MVP learning, but **not safe under concurrent updates** (we’ll revisit later).

**Why it matters**
- Encapsulates account operations in a dedicated service class.
- Updates balances through repository layer, keeping code cleaner.

**Next step**
- Start drafting system diagrams (ER diagram and high-level architecture).

## Day 6
**What I did**
- Added **ER Diagram** (`docs/diagrams/er-diagram.puml` + `er-diagram.png`) showing:
  - `Account` (PK `accountId`, `accountHolderName`, `balance`, `status`, `createdAt`)
  - `Transaction` (PK `transactionId`, FK `accountId`, `type`, `amount`, `status`, `timestamp`)
  - `AuditLog` (PK `logId`, `entity`, `entityId`, `action`, `details`, `timestamp`)
  - Relationships: Account has Transactions; Account/Transaction has AuditLogs.
- Added **Architecture Diagram** (`docs/diagrams/architecture.puml` + `architecture.png`) showing flow:
  - User → AccountSmokeTest/Main → AccountService → AccountRepository → DynamoDB (Accounts table).
  - Run/Debug passes AWS credentials via environment variables.
- Committed both diagrams into `docs/diagrams/`.

**Why it matters**
- ER diagram = logical data model (Accounts, Transactions, AuditLogs).
- Architecture diagram = system flow and responsibilities.

**Next step**
- **Extend design by introducing `Transaction` entity.** Keep **atomic balance update** (conditional writes) as a **later improvement if required**.

## Day 7
**What I did**
- Added `Transaction` model (PK: `transactionId`) with fields: `accountId`, `type` (`DEPOSIT`/`WITHDRAW`), `amount`, `status`, `timestamp`.
- Implemented `TransactionRepository` using DynamoDB Enhanced Client.
- Extended `AccountService`:
  - Updates `Accounts.balance`.
  - Writes a `Transaction` item per operation (`status="SUCCESS"`).
- Updated `AccountSmokeTest` to call `AccountService`:
  - Start balance = 500.0 → deposit 200 → withdraw 50 → final balance = 650.0.
- Verified in AWS Console:
  - `Accounts` shows updated balance.
  - `Transactions` lists entries for `acc-1002`.

**Why it matters**
- MVP now persists both current state (**balance**) and operation history (**transactions**), enough for a simple demo.
- **This completes the basic requirements up to transaction history (Day 7 scope of the 10-day plan).**

**Next step (minimal)**
- No code changes required. Keep using AWS Console to verify runs.

## Day 8
**What I did**
- Added **JUnit 5 dependencies** (`junit-jupiter-api`, `junit-jupiter-engine`) and **Mockito dependency** in `pom.xml`.
- Created `src/test/java/com/atlas/banking/AccountServiceTest`.
- Used **JUnit 5** to structure tests (`@BeforeEach`, `@Test`, `assertEquals`, `assertThrows`).
- Used **Mockito** to mock `AccountRepository` and `TransactionRepository` so tests run **locally** without AWS.
- Implemented three tests for `AccountService`:
  - **deposit_increases_balance_and_writes_txn**
  - **withdraw_decreases_balance_and_writes_txn**
  - **withdraw_throws_for_insufficient_funds**

**Why it matters**
- Unit tests now verify service logic without needing live AWS.
- Covered both **happy paths** (deposit, withdraw) and **failure path** (insufficient funds).
- Tests are **repeatable**, **automated**, and fast — unlike smoke tests where results had to be eyeballed.
- Learned difference:
  - JUnit = testing framework.
  - Mockito = mocking framework.

**Next step**
- Extend test coverage for edge cases if time permits.
- (Optional) Explore integration testing with DynamoDB Local — not required for MVP.