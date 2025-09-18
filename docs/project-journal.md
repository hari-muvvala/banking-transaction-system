# Project Journal

## Day 1
- Created repo and added MVP brief (FR/NFR).
- Next: design Accounts table (partition key only) and create Java project skeleton.

## Day 2
- Created 'Accounts' table in DynamoDB (Partition key: 'accountId' of type String).
- Added first sample item:
	- accountId: "acc-1001"
	- accountHoldername: "Hari Gopal"
	- balance = 1000
- Verified by scanning items in the table.

Next step:
- Scaffold Java project skeleton (Maven/Gradle structure).
- Prepare code to connect to DynamoDB.

### Day 3

- **Scaffolded Java project** using Maven: created `src/main/java` structure and added a `Main` class that prints “Banking Transaction System – Skeleton Ready.”  
- **Added Maven configuration** (`pom.xml`) to use Java 17 and include the AWS SDK dependencies for DynamoDB.  
- **Created `DynamoPing`** to verify a DynamoDB connection; configured AWS credentials and region via environment variables in IntelliJ run configuration.  
- **Verified connection:** running `DynamoPing` printed `DynamoDB client created successfully in region: ap-south-1`.  
- **Next step:** implement the Account model and repository, and write a simple test to save and load an account.

### Day 4

- **Implemented `Account` model** as a DynamoDB bean (partition key `accountId`, with attributes `accountHolderName` and `balance`).  
- **Added `AccountRepository`** using the DynamoDB Enhanced Client (`put` to save an account, `get` to load by ID).  
- **Wrote `AccountSmokeTest`:** created a sample account `acc-1002`, saved it, and read it back from DynamoDB. The test printed the loaded ID, name, and balance, confirming the round trip.  
- **Configured run environment:** set AWS credentials and region in IntelliJ so credentials are passed into the JVM at runtime.  
- **Committed and pushed** the code (`MVP: add Account model, repository, smoke test, and DynamoDB connection`) to GitHub.  
- **Next step:** design and implement deposit/withdraw operations that update the account balance atomically. Begin drafting system diagrams (ER diagram and high‑level architecture).