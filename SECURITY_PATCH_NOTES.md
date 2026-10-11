# Core Banking Security Hardening Patch

This branch applies an initial hardening pass for the issues in the supplied security review.

## Changes included
- Public access is limited to login and customer registration; creating admins/employees requires an existing admin.
- Unmatched routes are denied by default; customer and transaction record controllers are restricted to admins.
- Direct account management/money endpoints are restricted to staff; only the authenticated customer's account-list GET is open to customer role.
- Customer deposit/withdraw endpoints check the customer's account ownership.
- Negative money values and non-positive deposit/withdraw/transfer amounts are rejected.
- Deposit and ledger writes run in one transaction; deposit ledger direction records the account as destination.
- Optimistic version column plus Flyway migration added for accounts.
- JWT secret, database credentials, and Docker root credential are read from environment variables; token/auth debug logging removed.
- Removed the hard-coded admin@gmail.com/admin123 initialization. First-admin bootstrap now requires explicit environment variables and a password of at least 12 characters.
- Frontend API base URL moved to VITE_API_BASE_URL; token logging removed.
- CI runs backend tests and frontend lint/build on branch pushes and pull requests.

## Setup
1. Copy `engine/.env.example` to `engine/.env`.
2. Set unique values for `MYSQL_ROOT_PASSWORD`, `DB_PASSWORD`, and a cryptographically random `JWT_SECRET` of at least 32 bytes.
3. For initial admin provisioning only, set `BOOTSTRAP_ADMIN_EMAIL` and a unique `BOOTSTRAP_ADMIN_PASSWORD` (12+ characters), start the application, then remove those variables and restart.
4. Never commit `.env`. Rotate credentials/JWT keys that were previously committed.
5. Existing databases must apply Flyway migration V2 before startup.

## Limitations before production use
- CI is running, but this environment could not run Maven locally because Maven is not installed and the repository's Maven wrapper is missing its `.mvn/wrapper/maven-wrapper.properties` file.
- Idempotency keys are not implemented, so retries can still duplicate a money operation.
- The transaction ledger is not yet fully immutable/consistent, and transaction query adapters still need to preserve IDs/timestamps.
- Ownership rules should also live within application use cases, and transfer ownership plus security/rollback/concurrency integration tests remain to be added.
- Several controllers still return JPA entities; error handling, pagination, query ports, and structured audit logging need further work.
- Review the diff and all CI checks before merging. This is a best-effort hardening pass, not a proof of production security.
