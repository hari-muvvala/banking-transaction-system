# Project Journal

## Day 1
**What I did**
- Initialized repository with MVP brief (`project-brief.md` including FRs and NFRs).

**Why it matters**
- Establishes the scope and baseline requirements for the MVP.

**Next step**
- Design `Accounts` table (partition key only) and create Java project skeleton.

---

## Day 2
**What I did**
- Created `Accounts` table in DynamoDB with partition key `accountId` (String).
- Inserted a sample record:
  - accountId: "acc-1001"
  - accountHolderName: "Hari Gopal"
  - balance: 1000
- Verified with a table scan.

**Why it matters**
- Validates DynamoDB table design and basic operations.

**Next step**
- Scaffold Java project structure with Maven/Gradle.
- Prepare code to connect to DynamoDB.

---

## Day 3
**What I did**
- Scaffolded Java project using Maven (`src/main/java` + `Main` class).
- Added Maven configuration (`pom.xml`) with Java 17 and AWS SDK dependencies.
- Implemented `DynamoPing` class to test DynamoDB connectivity.
- Verified successful connection in region `ap-south-1`.

**Why it matters**
- Provides working project skeleton with verified AWS connectivity.

**Next step**
- Implement `Account` model and repository class.

---

## Day 4
**What I did**
- Implemented `Account` model (`@DynamoDbBean`, PK: `accountId`).
- Built `AccountRepository` with `put` and `get` methods using Enhanced Client.
- Added `AccountSmokeTest` to round-trip save and load an account.
- Verified AWS credentials/region configuration works.

**Why it matters**
- Confirms DynamoDB integration with a real model and repository.

**Next step**
- Implement deposit/withdraw operations and draft ER diagram.

---

## Day 5
**What I did**
- Implemented `AccountService` with deposit/withdraw logic.
- Updated `AccountSmokeTest`:
  - Created account with balance 500.
  - Performed deposit (+200) and withdraw (–50).
  - Verified final balance = 650.
- Noted: Current pattern is read → modify → put (not concurrency-safe).

**Why it matters**
- Encapsulates account logic in a service class.
- Establishes foundation for balance updates.

**Next step**
- Draft ER and high-level architecture diagrams.

---

## Day 6
**What I did**
- Added **ER Diagram** (`er-diagram.puml` + `.png`) with `Account`, `Transaction`, `AuditLog`.
- Added **Architecture Diagram** (`architecture.puml` + `.png`) showing flow:
  User → Main/SmokeTest → Service → Repository → DynamoDB.
- Committed both diagrams into `docs/diagrams/`.

**Why it matters**
- ER diagram = logical data model.
- Architecture diagram = high-level system flow.

**Next step**
- Introduce `Transaction` entity.

---

## Day 7
**What I did**
- Implemented `Transaction` model (PK: `transactionId`).
- Added `TransactionRepository`.
- Extended `AccountService` to:
  - Update account balance.
  - Write a `Transaction` entry per operation.
- Verified in AWS Console:
  - `Accounts` shows updated balance.
  - `Transactions` table records operations.

**Why it matters**
- System now persists both balance state and transaction history.

**Next step**
- Continue testing and validation.

---

## Day 8
**What I did**
- Added **JUnit 5** for unit testing.
- Integrated **Mockito** to mock repositories (no AWS required).
- Created `AccountServiceTest`:
  - Deposit increases balance.
  - Withdraw decreases balance.
  - Withdraw with insufficient funds throws exception.
- All tests passed in IntelliJ.

**Why it matters**
- Provides automated, repeatable verification of account logic.
- Decouples tests from AWS.

**Next step**
- Extend coverage with edge cases.

---

## Day 9
**What I did**
- Fixed build configuration:
  - Maven compiler set to Java 17 (`<release>17</release>`).
  - UTF-8 encoding added.
- Verified build with `mvn clean install` → **BUILD SUCCESS** + all tests green.
- Ran app via `mvn exec:java` → skeleton message printed.
- Cleaned repo:
  - Expanded `.gitignore` (ignore `target/`, `.idea/`, `.DS_Store`).
  - Removed tracked build/IDE files.
- Verified artifact: `target/banking-transaction-system-0.1.0.jar`.

**Why it matters**
- Stable, warning-free build.
- Clean repository ready for demo.

**Next step**
- Add and refine `README.md` with build/run instructions.
- (Optional) More edge-case tests.

---

## Day 10
**Planned**
- Finalize demo preparation.
- Verify requirements against MVP brief.
- Walkthrough with trainer.