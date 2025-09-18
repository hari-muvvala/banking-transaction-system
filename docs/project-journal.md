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