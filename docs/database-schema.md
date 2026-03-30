# Normalized Database Schema — LoanFlow 360

This schema stays consistent with **LoanFlow 360** microservices architecture.

**Design principles used**

* each service owns its own data
* no shared database across services
* cross-service communication happens through APIs and Kafka events
* relational schemas are normalized for transactional services
* MongoDB is used only where flexible event/audit storage is justified

---

# 1. Identity & Access Service

## Purpose

Owns user identity, credentials, roles, and token/session control.

## Tables Owned

### 1. `users`

**Primary Key**

* `user_id`

**Foreign Keys**

* none

**Sample fields**

* `user_id` — BIGINT
* `username` — VARCHAR(150), unique
* `email` — VARCHAR(255), unique
* `password_hash` — VARCHAR(255)
* `first_name` — VARCHAR(100)
* `last_name` — VARCHAR(100)
* `status` — VARCHAR(30)
* `created_at` — TIMESTAMP
* `updated_at` — TIMESTAMP

**Notes**

* stores core identity for applicants, loan officers, and admins
* keeps authentication separate from business data

**Indexing considerations**

* unique index on `username`
* unique index on `email`
* index on `status`

---

### 2. `roles`

**Primary Key**

* `role_id`

**Foreign Keys**

* none

**Sample fields**

* `role_id` — BIGINT
* `role_code` — VARCHAR(50), unique
* `role_name` — VARCHAR(100)
* `description` — VARCHAR(255)

**Examples**

* `APPLICANT`
* `LOAN_OFFICER`
* `ADMIN`

**Indexing considerations**

* unique index on `role_code`

---

### 3. `user_roles`

**Primary Key**

* composite: `user_id`, `role_id`

**Foreign Keys**

* `user_id` → `users.user_id`
* `role_id` → `roles.role_id`

**Sample fields**

* `user_id` — BIGINT
* `role_id` — BIGINT
* `assigned_at` — TIMESTAMP

**Relationship**

* many-to-many between `users` and `roles`

**Why normalized**

* supports users with multiple roles without repeating role data in the `users` table

---

### 4. `refresh_tokens`

**Primary Key**

* `refresh_token_id`

**Foreign Keys**

* `user_id` → `users.user_id`

**Sample fields**

* `refresh_token_id` — BIGINT
* `user_id` — BIGINT
* `token_value` — VARCHAR(500), unique
* `expires_at` — TIMESTAMP
* `revoked` — BOOLEAN
* `created_at` — TIMESTAMP

**Relationship**

* one-to-many from `users` to `refresh_tokens`

**Workflow support**

* supports login, refresh, logout, token revocation

**Indexing considerations**

* unique index on `token_value`
* index on `user_id`
* index on `expires_at`

---

## Relationships

* `users` ↔ `roles`: many-to-many via `user_roles`
* `users` → `refresh_tokens`: one-to-many

## How this supports the workflow

* login validates `users`
* role checks come from `user_roles`
* token refresh and logout are backed by `refresh_tokens`

## Important constraints

* usernames and emails must be unique
* password hash never stored in plaintext
* role assignment must reference valid user and role rows

---

# 2. Loan Application Service

## Purpose

Owns the core loan application lifecycle and business state.

## Tables Owned

### 1. `loan_applications`

**Primary Key**

* `application_id`

**Foreign Keys**

* none at database level to other services

**Sample fields**

* `application_id` — BIGINT
* `applicant_user_id` — BIGINT
* `loan_type` — VARCHAR(50)
* `requested_amount` — DECIMAL(15,2)
* `term_months` — INT
* `annual_income` — DECIMAL(15,2)
* `employment_status` — VARCHAR(50)
* `status` — VARCHAR(50)
* `submitted_at` — TIMESTAMP
* `updated_at` — TIMESTAMP
* `assigned_officer_user_id` — BIGINT, nullable
* `application_reference` — VARCHAR(50), unique

**Why no FK to Identity service**

* in microservices, the service stores `applicant_user_id` and `assigned_officer_user_id` as external references, not cross-database foreign keys

**Indexing considerations**

* unique index on `application_reference`
* index on `applicant_user_id`
* index on `assigned_officer_user_id`
* index on `status`
* composite index on `(status, submitted_at)`

---

### 2. `application_addresses`

**Primary Key**

* `address_id`

**Foreign Keys**

* `application_id` → `loan_applications.application_id`

**Sample fields**

* `address_id` — BIGINT
* `application_id` — BIGINT
* `address_type` — VARCHAR(30)
* `line1` — VARCHAR(255)
* `line2` — VARCHAR(255), nullable
* `city` — VARCHAR(100)
* `state_code` — VARCHAR(20)
* `postal_code` — VARCHAR(20)
* `country_code` — VARCHAR(10)

**Relationship**

* one-to-many from `loan_applications` to `application_addresses`

**Why normalized**

* allows current and prior addresses without repeating columns in application table

**Indexing considerations**

* index on `application_id`
* optional composite index on `(application_id, address_type)`

---

### 3. `application_employment_details`

**Primary Key**

* `employment_detail_id`

**Foreign Keys**

* `application_id` → `loan_applications.application_id`

**Sample fields**

* `employment_detail_id` — BIGINT
* `application_id` — BIGINT
* `employer_name` — VARCHAR(255)
* `job_title` — VARCHAR(150)
* `employment_type` — VARCHAR(50)
* `monthly_income` — DECIMAL(15,2)
* `years_employed` — DECIMAL(4,1)

**Relationship**

* one-to-many or one-to-one depending on business scope

**Recommendation**

* for early-stage scope, treat as one current employment record per application
* enforce unique constraint on `application_id` if only one record is allowed

---

### 4. `application_status_history`

**Primary Key**

* `status_history_id`

**Foreign Keys**

* `application_id` → `loan_applications.application_id`

**Sample fields**

* `status_history_id` — BIGINT
* `application_id` — BIGINT
* `previous_status` — VARCHAR(50)
* `new_status` — VARCHAR(50)
* `changed_by_user_id` — BIGINT
* `change_reason` — VARCHAR(255)
* `changed_at` — TIMESTAMP

**Relationship**

* one-to-many from `loan_applications` to `application_status_history`

**Workflow support**

* tracks lifecycle transitions like `SUBMITTED`, `PENDING_DOCUMENTS`, `UNDERWRITING_IN_PROGRESS`, `APPROVED`, `REJECTED`

**Indexing considerations**

* index on `application_id`
* index on `changed_at`

---

### 5. `outbox_events`

**Primary Key**

* `outbox_event_id`

**Foreign Keys**

* none required

**Sample fields**

* `outbox_event_id` — BIGINT
* `aggregate_type` — VARCHAR(100)
* `aggregate_id` — VARCHAR(100)
* `event_type` — VARCHAR(100)
* `event_payload` — JSON or TEXT
* `event_status` — VARCHAR(30)
* `created_at` — TIMESTAMP
* `published_at` — TIMESTAMP, nullable

**Why included**

* supports reliable Kafka publishing using the outbox pattern

**Indexing considerations**

* index on `event_status`
* index on `created_at`

---

## Relationships

* `loan_applications` → `application_addresses`: one-to-many
* `loan_applications` → `application_employment_details`: one-to-one or one-to-many
* `loan_applications` → `application_status_history`: one-to-many

## How this supports the workflow

* main application submission is stored in `loan_applications`
* detailed applicant data is normalized into child tables
* status changes are preserved historically
* Kafka publishing reliability is supported by `outbox_events`

## Important constraints

* requested amount and term should be positive
* application status should come from controlled enum-like values
* one active application per applicant and loan type may be enforced through business rules rather than strict DB constraint if needed

---

# 3. Document Management Service

## Purpose

Owns required document tracking, upload metadata, and verification results.

## Tables Owned

### 1. `application_documents`

**Primary Key**

* `document_id`

**Foreign Keys**

* none to external services
* `application_id` is stored as external business reference

**Sample fields**

* `document_id` — BIGINT
* `application_id` — BIGINT
* `document_type` — VARCHAR(50)
* `file_name` — VARCHAR(255)
* `storage_key` — VARCHAR(500)
* `content_type` — VARCHAR(100)
* `file_size_bytes` — BIGINT
* `upload_status` — VARCHAR(30)
* `verification_status` — VARCHAR(30)
* `uploaded_by_user_id` — BIGINT
* `uploaded_at` — TIMESTAMP
* `verified_at` — TIMESTAMP, nullable

**Why external ref**

* `application_id` belongs to Loan Application Service but is needed locally for document workflow

**Indexing considerations**

* index on `application_id`
* composite index on `(application_id, document_type)`
* index on `verification_status`

---

### 2. `document_requirements`

**Primary Key**

* `requirement_id`

**Foreign Keys**

* none

**Sample fields**

* `requirement_id` — BIGINT
* `loan_type` — VARCHAR(50)
* `document_type` — VARCHAR(50)
* `required_flag` — BOOLEAN
* `display_order` — INT
* `active_flag` — BOOLEAN

**Why normalized**

* keeps required document rules configurable by loan type

**Indexing considerations**

* composite unique index on `(loan_type, document_type)`

---

### 3. `document_verification_audit`

**Primary Key**

* `verification_audit_id`

**Foreign Keys**

* `document_id` → `application_documents.document_id`

**Sample fields**

* `verification_audit_id` — BIGINT
* `document_id` — BIGINT
* `verified_by_user_id` — BIGINT
* `verification_result` — VARCHAR(30)
* `notes` — VARCHAR(500)
* `verified_at` — TIMESTAMP

**Relationship**

* one-to-many from `application_documents` to `document_verification_audit`

**Workflow support**

* supports officer review history and re-verification cases

---

### 4. `processed_events`

**Primary Key**

* `processed_event_id`

**Foreign Keys**

* none

**Sample fields**

* `processed_event_id` — BIGINT
* `event_id` — VARCHAR(100), unique
* `event_type` — VARCHAR(100)
* `processed_at` — TIMESTAMP

**Why included**

* supports idempotent Kafka consumption

**Indexing considerations**

* unique index on `event_id`

---

## Relationships

* `application_documents` → `document_verification_audit`: one-to-many
* `document_requirements` is standalone reference data

## How this supports the workflow

* uploaded documents are tracked in `application_documents`
* required checklist logic comes from `document_requirements`
* officer review and verification decisions are stored in `document_verification_audit`
* duplicate Kafka deliveries are safely handled using `processed_events`

## Important constraints

* storage key should be unique per physical upload
* verification cannot be marked complete without upload metadata present
* required document rules should be versioned carefully if business rules change over time

---

# 4. Underwriting & Decision Service

## Purpose

Owns underwriting evaluations, decision outcomes, and manual review actions.

## Tables Owned

### 1. `underwriting_cases`

**Primary Key**

* `underwriting_case_id`

**Foreign Keys**

* none to external schemas
* stores `application_id` as external reference

**Sample fields**

* `underwriting_case_id` — BIGINT
* `application_id` — BIGINT
* `case_status` — VARCHAR(50)
* `decision_status` — VARCHAR(50), nullable
* `decision_reason` — VARCHAR(500), nullable
* `manual_review_required` — BOOLEAN
* `created_at` — TIMESTAMP
* `completed_at` — TIMESTAMP, nullable

**Indexing considerations**

* unique index on `application_id` if only one active case per application
* index on `case_status`
* index on `decision_status`

---

### 2. `underwriting_checks`

**Primary Key**

* `check_id`

**Foreign Keys**

* `underwriting_case_id` → `underwriting_cases.underwriting_case_id`

**Sample fields**

* `check_id` — BIGINT
* `underwriting_case_id` — BIGINT
* `check_type` — VARCHAR(50)
* `check_status` — VARCHAR(30)
* `result_code` — VARCHAR(50)
* `result_summary` — VARCHAR(255)
* `executed_at` — TIMESTAMP

**Relationship**

* one-to-many from `underwriting_cases` to `underwriting_checks`

**Why normalized**

* underwriting is made of multiple checks like debt-to-income, income sufficiency, fraud flags, external score checks

---

### 3. `underwriting_decisions`

**Primary Key**

* `decision_id`

**Foreign Keys**

* `underwriting_case_id` → `underwriting_cases.underwriting_case_id`

**Sample fields**

* `decision_id` — BIGINT
* `underwriting_case_id` — BIGINT
* `decision_type` — VARCHAR(50)
* `recommended_amount` — DECIMAL(15,2)
* `recommended_term_months` — INT
* `decision_source` — VARCHAR(30)
* `decided_by_user_id` — BIGINT, nullable
* `decision_at` — TIMESTAMP

**Relationship**

* one-to-many if decision history is preserved
* one-to-one if only final decision is stored

**Recommendation**

* keep one-to-many to preserve re-evaluation and manual override history

---

### 4. `manual_review_notes`

**Primary Key**

* `review_note_id`

**Foreign Keys**

* `underwriting_case_id` → `underwriting_cases.underwriting_case_id`

**Sample fields**

* `review_note_id` — BIGINT
* `underwriting_case_id` — BIGINT
* `reviewer_user_id` — BIGINT
* `note_text` — VARCHAR(1000)
* `created_at` — TIMESTAMP

**Relationship**

* one-to-many from `underwriting_cases` to `manual_review_notes`

---

### 5. `processed_events`

Same purpose as in other event consumers.

**Sample fields**

* `processed_event_id` — BIGINT
* `event_id` — VARCHAR(100), unique
* `event_type` — VARCHAR(100)
* `processed_at` — TIMESTAMP

---

## Relationships

* `underwriting_cases` → `underwriting_checks`: one-to-many
* `underwriting_cases` → `underwriting_decisions`: one-to-many
* `underwriting_cases` → `manual_review_notes`: one-to-many

## How this supports the workflow

* a verified application creates an underwriting case
* individual checks are stored separately for transparency
* final or repeated decisions are stored historically
* manual fallback is supported when dependencies fail

## Important constraints

* case must exist before checks or decisions are inserted
* only valid decision states should be allowed
* recommended loan amount should not exceed requested amount without explicit override policy

---

# 5. Notification & Audit Service

## Purpose

Owns user notifications and flexible audit history.

## Primary Storage Choice

**MongoDB** is justified here because audit and notification payloads are append-heavy, event-shaped, and may vary over time.

## Collections Owned

### 1. `application_audit_events`

**Primary Key**

* `_id` (ObjectId)

**Key fields**

* `eventId` — String, unique
* `applicationId` — Long
* `eventType` — String
* `eventSource` — String
* `performedByUserId` — Long or String
* `summary` — String
* `details` — object
* `occurredAt` — Date
* `receivedAt` — Date

**Indexing considerations**

* unique index on `eventId`
* index on `applicationId`
* compound index on `(applicationId, occurredAt)`

**Why MongoDB fits**

* event payloads vary by source service
* avoids constant relational schema migrations for new audit shapes

---

### 2. `user_notifications`

**Primary Key**

* `_id` (ObjectId)

**Key fields**

* `notificationId` — String, unique
* `userId` — Long
* `applicationId` — Long, nullable
* `type` — String
* `title` — String
* `message` — String
* `deliveryChannel` — String
* `deliveryStatus` — String
* `read` — Boolean
* `createdAt` — Date
* `readAt` — Date, nullable

**Indexing considerations**

* unique index on `notificationId`
* index on `userId`
* compound index on `(userId, read, createdAt)`

---

### 3. `event_replay_log`

**Primary Key**

* `_id` (ObjectId)

**Key fields**

* `eventId` — String
* `replayStatus` — String
* `replayedAt` — Date
* `notes` — String

**Why included**

* supports operational replay and recovery of failed audit/notification event handling

---

## Relationships

MongoDB is document-based, so relationships are reference-based rather than FK-enforced:

* `application_audit_events.applicationId` refers to external application
* `user_notifications.userId` refers to identity user
* `user_notifications.applicationId` optionally refers to loan application

## How this supports the workflow

* every Kafka status event can be stored as an audit document
* notifications are generated asynchronously without affecting core loan transactions
* replay logs help explain recovery strategy in interviews

## Important constraints

* eventId must remain unique for idempotency
* audit writes should be append-only
* notification history should not be overwritten except for read state

---

# 6. Platform / Infrastructure Module

## Purpose

Mostly operational, but a small persistence layer may exist for shared technical controls.

## Tables Owned

### 1. `api_rate_limit_counters`

If persisted in Redis only, this may not exist in SQL.

**Recommended store**

* Redis, not MySQL

**Why**

* rate limiting requires high-speed atomic counters and TTL behavior

**Example logical fields**

* `rate_limit_key`
* `request_count`
* `window_start`
* `expires_at`

---

### 2. `service_request_audit`

Optional relational or log-stream sink for platform-level request tracking.

**Sample fields**

* `request_audit_id` — BIGINT
* `trace_id` — VARCHAR(100)
* `service_name` — VARCHAR(100)
* `endpoint` — VARCHAR(255)
* `http_method` — VARCHAR(20)
* `response_status` — INT
* `started_at` — TIMESTAMP
* `completed_at` — TIMESTAMP

**Recommendation**

* keep this out of core transactional databases if centralized logging stack already exists

---

## Redis Structures Used by Platform

### Rate Limiting Keys

* `login:ip:{ipAddress}`
* `upload:user:{userId}`
* `submit:user:{userId}`

### Cache Keys

* `application-summary:{applicationId}`
* `dashboard-status:{status}:page:{page}`
* `loan-product-rules:{loanType}`

## Why Redis instead of relational tables

* TTL and atomic increment operations are first-class
* best fit for short-lived counters and cached read models

---

# 7. Frontend Module

The React frontend does **not** own the system-of-record database. It consumes APIs only.

If interviewers ask about client-side persistence:

* access tokens may be stored in memory or secure client storage depending on implementation strategy
* UI preferences or drafts can use browser local storage, but this is not part of the backend schema

---

# Relationship Summary by Type

## One-to-One

* `loan_applications` → `application_employment_details` if only one current employment record is allowed
* `underwriting_cases` → current active decision, if modeled as a single current view in service logic

## One-to-Many

* `users` → `refresh_tokens`
* `loan_applications` → `application_addresses`
* `loan_applications` → `application_status_history`
* `application_documents` → `document_verification_audit`
* `underwriting_cases` → `underwriting_checks`
* `underwriting_cases` → `underwriting_decisions`
* `underwriting_cases` → `manual_review_notes`

## Many-to-Many

* `users` ↔ `roles` via `user_roles`

---

# How the Overall Schema Supports Main Business Workflows

## 1. Applicant submits application

* Identity service authenticates the user
* Loan Application service stores the application and applicant data
* Outbox event records pending Kafka publication

## 2. Applicant uploads documents

* Document service stores upload metadata and required document mapping
* officer or system verifies completeness

## 3. Document verification triggers underwriting

* document verified event reaches Underwriting service
* underwriting case and checks are recorded
* decisions are stored with history

## 4. Status updates and notifications

* Loan Application service updates master application state
* Notification & Audit service stores timeline and user notification records asynchronously

## 5. Operational resilience

* processed event tables and unique event IDs support idempotency
* Redis supports rate limiting and cached reads
* no service needs direct access to another service’s database

---

# Important Constraints and Indexing Considerations

## Constraints

* external IDs like `application_id` and `user_id` are stored as references, not cross-schema FKs
* enum-like fields such as status and role should be tightly validated at application level
* append-only history tables should not be updated in place except for operational corrections
* idempotency keys and event IDs must be unique

## Indexing

Focus indexes on:

* high-frequency lookups by status
* application timeline queries
* user notification retrieval
* event deduplication
* document verification filters
* loan officer dashboard searches

Avoid over-indexing:

* write-heavy audit and status history tables should keep only necessary indexes

---

# Short Summary

## Cross-service reference strategy

Each service stores external identifiers such as `user_id` and `application_id` as business references. Services communicate through REST APIs and Kafka events rather than foreign keys across databases.

## Normalization choices

Transactional services use normalized relational design:

* separate master and child tables
* join tables for many-to-many relationships
* history tables for state transitions
* outbox and processed-event tables for reliable event workflows

## Justified NoSQL usage

MongoDB is justified only in the **Notification & Audit Service** because audit events and notification payloads are flexible, append-heavy, and evolve faster than core transactional schemas. This avoids forcing variable event structures into rigid relational tables.
