# Core Banking Security Hardening Patch

This patch addresses the highest-risk findings from the supplied security review.

## Changes intended in this patch bundle
- Restrict admin/employee account creation to authenticated admins; only login and customer registration remain public.
- Default-deny unmatched routes and restrict customer/transaction records to staff/admin routes.
- Add customer ownership checks to user deposit/withdraw endpoints.
- Reject non-positive money amounts in a shared money value object and DTO validation.
- Make deposit and ledger write transactional; correct deposit ledger direction.
- Add account optimistic versioning plus Flyway migration V2.
- Externalize database and JWT secrets, remove authentication debug logging, and configure Docker secrets from environment variables.
- Move frontend API base URL to Vite environment configuration and remove token logging.
- Add domain money validation tests and run backend/frontend CI checks.

## Required setup
1. Copy `engine/.env.example` to `engine/.env`.
2. Set unique `MYSQL_ROOT_PASSWORD`, `DB_PASSWORD`, and a random `JWT_SECRET` of at least 32 bytes.
3. Never commit `.env`; rotate credentials and JWT signing keys that were previously committed.
4. Existing databases must apply Flyway migration V2 before startup.

## Known remaining work
- The uploaded ZIP lacks `.mvn/wrapper/maven-wrapper.properties`, and Maven is not installed in the patch environment, so compilation/tests could not be verified here.
- Idempotency keys are not yet implemented.
- Add transfer ownership checks for customer transfers, broader authorization inside all use cases, and integration tests for security/rollback/concurrency.
- Standardize an immutable ledger, preserve IDs/timestamps when reading transactions, and return DTOs instead of JPA entities.
- Review the diff and run tests before deployment. This is a best-effort hardening patch, not certification or proof of security.
