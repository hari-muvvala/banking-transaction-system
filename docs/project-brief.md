# Banking Transaction System — MVP (v0.1)

## FRs (v0.1)
1. Create account (accountId, name, email, balance=0)
2. Deposit money
3. Withdraw money (prevent negative balance)
4. Get balance

## NFRs (v0.1)
- Use AWS free tier only.
- Keep secrets/keys out of source code and the repo.
- Add simple logs for each operation.

## Initial Architecture
Client → Java API (to be added) → DynamoDB (Accounts table first)