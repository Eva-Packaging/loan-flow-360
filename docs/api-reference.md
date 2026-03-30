# REST API Reference — LoanFlow 360

This API reference follows the previously defined **LoanFlow 360** microservices architecture and focuses on the **main business workflows only**.

**Base path convention**
All public APIs are exposed through the API Gateway under:

`/api/v1/...`

**Auth model**

* **Public**: no token required
* **Authenticated**: valid JWT required
* **Role-restricted**: JWT required with specific role such as `APPLICANT`, `LOAN_OFFICER`, or `ADMIN`

**Standard error payload shape**

```json
{
  "timestamp": "2026-03-24T14:20:11Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/applications"
}
```

---

# 1. Identity & Access Service

## 1.1 Login

**POST** `/api/v1/auth/login`

**Purpose**
Authenticates a user and returns a JWT access token.

**Auth requirement**
Public

**Input parameters**

* **Body**

    * `username`
    * `password`

**Example request JSON**

```json
{
  "username": "loan.officer@loanflow.com",
  "password": "Password123!"
}
```

**Example response JSON**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 102,
    "username": "loan.officer@loanflow.com",
    "role": "LOAN_OFFICER"
  }
}
```

**Error handling**

* `400 Bad Request` — missing credentials
* `401 Unauthorized` — invalid username or password
* `429 Too Many Requests` — login rate limit exceeded using Redis-based distributed rate limiting

**Sample error payload**

```json
{
  "timestamp": "2026-03-24T14:20:11Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid username or password",
  "path": "/api/v1/auth/login"
}
```

---

## 1.2 Refresh Access Token

**POST** `/api/v1/auth/refresh`

**Purpose**
Issues a new access token using a valid refresh token or refresh session flow.

**Auth requirement**
Public with valid refresh token payload

**Input parameters**

* **Body**

    * `refreshToken`

**Example request JSON**

```json
{
  "refreshToken": "rt_78sd7f8sd7f98sdf..."
}
```

**Example response JSON**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.newtoken...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

**Error handling**

* `400 Bad Request` — missing refresh token
* `401 Unauthorized` — invalid or expired refresh token
* `429 Too Many Requests` — refresh attempts throttled

**Sample error payload**

```json
{
  "timestamp": "2026-03-24T14:21:03Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Refresh token is invalid or expired",
  "path": "/api/v1/auth/refresh"
}
```

---

## 1.3 Get Current User Profile

**GET** `/api/v1/auth/me`

**Purpose**
Returns the authenticated user’s profile and role.

**Auth requirement**
Authenticated

**Input parameters**
None

**Example response JSON**

```json
{
  "id": 102,
  "username": "loan.officer@loanflow.com",
  "fullName": "Jordan Smith",
  "role": "LOAN_OFFICER",
  "permissions": [
    "APPLICATION_READ",
    "APPLICATION_REVIEW",
    "UNDERWRITING_DECIDE"
  ]
}
```

**Error handling**

* `401 Unauthorized` — missing or invalid JWT
* `403 Forbidden` — token valid but access blocked by policy

---

# 2. Loan Application Service

## 2.1 Create Loan Application

**POST** `/api/v1/applications`

**Purpose**
Creates a new loan application for an applicant.

**Auth requirement**
Authenticated, role: `APPLICANT`

**Input parameters**

* **Body**

    * `loanType`
    * `amount`
    * `termMonths`
    * `annualIncome`
    * `employmentStatus`
    * `applicantDetails`

**Example request JSON**

```json
{
  "loanType": "PERSONAL",
  "amount": 25000,
  "termMonths": 48,
  "annualIncome": 92000,
  "employmentStatus": "FULL_TIME",
  "applicantDetails": {
    "firstName": "Devin",
    "lastName": "Catuns",
    "email": "devin@example.com",
    "phone": "555-0147"
  }
}
```

**Example response JSON**

```json
{
  "applicationId": 50021,
  "status": "SUBMITTED",
  "submittedAt": "2026-03-24T14:30:22Z",
  "loanType": "PERSONAL",
  "amount": 25000,
  "nextStep": "UPLOAD_REQUIRED_DOCUMENTS"
}
```

**Kafka-based processing**
Yes. Successful creation triggers publication of a `loan.application.submitted` event for downstream processing.

**Error handling**

* `400 Bad Request` — validation failure
* `401 Unauthorized` — missing or invalid JWT
* `403 Forbidden` — role not allowed
* `409 Conflict` — duplicate active application detected
* `429 Too Many Requests` — public-facing submission rate limit triggered
* `500 Internal Server Error` — persistence or publish failure

**Sample error payload**

```json
{
  "timestamp": "2026-03-24T14:30:29Z",
  "status": 409,
  "error": "Conflict",
  "message": "An active application already exists for this applicant and loan type",
  "path": "/api/v1/applications"
}
```

---

## 2.2 Get Application Details

**GET** `/api/v1/applications/{applicationId}`

**Purpose**
Returns full details for a specific loan application.

**Auth requirement**
Authenticated

* `APPLICANT` can access own application
* `LOAN_OFFICER` and `ADMIN` can access assigned or all permitted applications

**Input parameters**

* **Path**

    * `applicationId`

**Example response JSON**

```json
{
  "applicationId": 50021,
  "status": "UNDERWRITING_IN_PROGRESS",
  "loanType": "PERSONAL",
  "amount": 25000,
  "termMonths": 48,
  "annualIncome": 92000,
  "submittedAt": "2026-03-24T14:30:22Z",
  "applicantDetails": {
    "firstName": "Devin",
    "lastName": "Catuns",
    "email": "devin@example.com"
  },
  "documentSummary": {
    "required": 4,
    "uploaded": 4,
    "verified": 4
  }
}
```

**Cached**
Yes. Frequently accessed application summaries are cached in Redis with short TTL for read optimization.

**Error handling**

* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found` — application does not exist

---

## 2.3 Search Applications for Review

**GET** `/api/v1/applications`

**Purpose**
Returns a paginated list of applications for review dashboards and status filtering.

**Auth requirement**
Authenticated, roles: `LOAN_OFFICER`, `ADMIN`

**Input parameters**

* **Query**

    * `status`
    * `loanType`
    * `page`
    * `size`
    * `sort`

**Example request**
`GET /api/v1/applications?status=PENDING_REVIEW&page=0&size=20&sort=submittedAt,desc`

**Example response JSON**

```json
{
  "content": [
    {
      "applicationId": 50021,
      "applicantName": "Devin Catuns",
      "loanType": "PERSONAL",
      "amount": 25000,
      "status": "PENDING_REVIEW",
      "submittedAt": "2026-03-24T14:30:22Z"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 143,
  "totalPages": 8
}
```

**Cached**
Partially. Common dashboard filters may be cached briefly in Redis to reduce repeated database reads.

**Error handling**

* `401 Unauthorized`
* `403 Forbidden`
* `400 Bad Request` — invalid query params

---

## 2.4 Update Application Status

**PATCH** `/api/v1/applications/{applicationId}/status`

**Purpose**
Updates the master application status after review or downstream workflow completion.

**Auth requirement**
Authenticated, roles: `LOAN_OFFICER`, `ADMIN`

**Input parameters**

* **Path**

    * `applicationId`
* **Body**

    * `status`
    * `reason`

**Example request JSON**

```json
{
  "status": "PENDING_DOCUMENTS",
  "reason": "Income verification document missing"
}
```

**Example response JSON**

```json
{
  "applicationId": 50021,
  "previousStatus": "SUBMITTED",
  "currentStatus": "PENDING_DOCUMENTS",
  "updatedAt": "2026-03-24T15:02:11Z",
  "reason": "Income verification document missing"
}
```

**Kafka-based processing**
Yes. Status changes publish `loan.status.updated` for notification and audit processing.

**Error handling**

* `400 Bad Request`
* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`
* `409 Conflict` — invalid state transition

**Sample error payload**

```json
{
  "timestamp": "2026-03-24T15:03:44Z",
  "status": 409,
  "error": "Conflict",
  "message": "Cannot transition application from APPROVED to PENDING_DOCUMENTS",
  "path": "/api/v1/applications/50021/status"
}
```

---

# 3. Document Management Service

## 3.1 Upload Document Metadata

**POST** `/api/v1/applications/{applicationId}/documents`

**Purpose**
Registers an uploaded document against a loan application. In implementation, the binary may be uploaded directly to S3 through a pre-signed URL flow, while this endpoint stores document metadata.

**Auth requirement**
Authenticated

* `APPLICANT` for own application
* `LOAN_OFFICER`, `ADMIN` for internal uploads if allowed

**Input parameters**

* **Path**

    * `applicationId`
* **Body**

    * `documentType`
    * `fileName`
    * `storageKey`
    * `contentType`

**Example request JSON**

```json
{
  "documentType": "PAY_STUB",
  "fileName": "paystub-mar-2026.pdf",
  "storageKey": "loan-apps/50021/paystub-mar-2026.pdf",
  "contentType": "application/pdf"
}
```

**Example response JSON**

```json
{
  "documentId": 88041,
  "applicationId": 50021,
  "documentType": "PAY_STUB",
  "status": "UPLOADED",
  "uploadedAt": "2026-03-24T15:11:40Z"
}
```

**Rate-limited**
Yes. Upload-related endpoints are rate-limited to protect downstream storage and validation services.

**Error handling**

* `400 Bad Request`
* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`
* `413 Payload Too Large` — if metadata or upload policy is violated
* `429 Too Many Requests`

---

## 3.2 List Documents for an Application

**GET** `/api/v1/applications/{applicationId}/documents`

**Purpose**
Returns all submitted documents and their verification state.

**Auth requirement**
Authenticated with ownership or staff role

**Input parameters**

* **Path**

    * `applicationId`

**Example response JSON**

```json
{
  "applicationId": 50021,
  "documents": [
    {
      "documentId": 88041,
      "documentType": "PAY_STUB",
      "fileName": "paystub-mar-2026.pdf",
      "status": "VERIFIED",
      "uploadedAt": "2026-03-24T15:11:40Z",
      "verifiedAt": "2026-03-24T15:20:00Z"
    }
  ]
}
```

**Error handling**

* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`

---

## 3.3 Verify Documents

**POST** `/api/v1/applications/{applicationId}/documents/verify`

**Purpose**
Validates document completeness and marks the application ready for underwriting when requirements are met.

**Auth requirement**
Authenticated, roles: `LOAN_OFFICER`, `ADMIN`

**Input parameters**

* **Path**

    * `applicationId`
* **Body**

    * `verifiedBy`
    * `notes`

**Example request JSON**

```json
{
  "verifiedBy": "loan.officer@loanflow.com",
  "notes": "All required documents reviewed and accepted"
}
```

**Example response JSON**

```json
{
  "applicationId": 50021,
  "documentVerificationStatus": "VERIFIED",
  "verifiedAt": "2026-03-24T15:20:00Z",
  "nextStep": "UNDERWRITING_REQUESTED"
}
```

**Kafka-based processing**
Yes. Successful verification publishes `loan.document.verified`, which triggers underwriting.

**Error handling**

* `400 Bad Request` — required documents missing
* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`
* `409 Conflict` — documents already verified

**Sample error payload**

```json
{
  "timestamp": "2026-03-24T15:20:15Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Required documents missing: BANK_STATEMENT",
  "path": "/api/v1/applications/50021/documents/verify"
}
```

---

# 4. Underwriting & Decision Service

## 4.1 Get Underwriting Decision

**GET** `/api/v1/underwriting/{applicationId}`

**Purpose**
Returns underwriting outcome and review summary for an application.

**Auth requirement**
Authenticated

* `LOAN_OFFICER`, `ADMIN`
* `APPLICANT` may be allowed to view only final decision summary, depending on policy

**Input parameters**

* **Path**

    * `applicationId`

**Example response JSON**

```json
{
  "applicationId": 50021,
  "decisionStatus": "APPROVED",
  "decisionAt": "2026-03-24T16:02:11Z",
  "decisionReason": "Income and debt ratio within policy thresholds",
  "recommendedAmount": 25000,
  "recommendedTermMonths": 48,
  "manualReviewRequired": false
}
```

**Error handling**

* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`

---

## 4.2 Submit Manual Review Decision

**POST** `/api/v1/underwriting/{applicationId}/decision`

**Purpose**
Allows a loan officer to finalize a manual underwriting outcome after exception handling or fallback review.

**Auth requirement**
Authenticated, roles: `LOAN_OFFICER`, `ADMIN`

**Input parameters**

* **Path**

    * `applicationId`
* **Body**

    * `decision`
    * `reason`
    * `recommendedAmount`
    * `recommendedTermMonths`

**Example request JSON**

```json
{
  "decision": "MANUAL_APPROVAL",
  "reason": "External credit dependency unavailable; officer completed manual review",
  "recommendedAmount": 20000,
  "recommendedTermMonths": 36
}
```

**Example response JSON**

```json
{
  "applicationId": 50021,
  "decisionStatus": "APPROVED",
  "decisionSource": "MANUAL_REVIEW",
  "decisionAt": "2026-03-24T16:15:00Z"
}
```

**Kafka-based processing**
Yes. Decision completion publishes `loan.underwriting.completed` and typically results in downstream `loan.status.updated`.

**Error handling**

* `400 Bad Request`
* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`
* `409 Conflict` — decision already finalized

---

## 4.3 Re-run Underwriting

**POST** `/api/v1/underwriting/{applicationId}/re-evaluate`

**Purpose**
Triggers a re-evaluation when application data changes or review is reopened.

**Auth requirement**
Authenticated, roles: `LOAN_OFFICER`, `ADMIN`

**Input parameters**

* **Path**

    * `applicationId`
* **Body**

    * `reason`

**Example request JSON**

```json
{
  "reason": "Updated income documents submitted"
}
```

**Example response JSON**

```json
{
  "applicationId": 50021,
  "status": "RE_EVALUATION_REQUESTED",
  "requestedAt": "2026-03-24T16:20:10Z"
}
```

**Kafka-based processing**
Yes. This triggers asynchronous underwriting workflow through the same event-driven path.

**Error handling**

* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`
* `409 Conflict` — application not eligible for re-evaluation

---

# 5. Notification & Audit Service

## 5.1 Get Application Activity Timeline

**GET** `/api/v1/audit/applications/{applicationId}/timeline`

**Purpose**
Returns a chronological audit trail of major application events.

**Auth requirement**
Authenticated

* `APPLICANT` for own application
* `LOAN_OFFICER`, `ADMIN` for staff access

**Input parameters**

* **Path**

    * `applicationId`

**Example response JSON**

```json
{
  "applicationId": 50021,
  "events": [
    {
      "eventType": "APPLICATION_SUBMITTED",
      "occurredAt": "2026-03-24T14:30:22Z",
      "performedBy": "devin@example.com",
      "summary": "Loan application submitted"
    },
    {
      "eventType": "DOCUMENTS_VERIFIED",
      "occurredAt": "2026-03-24T15:20:00Z",
      "performedBy": "loan.officer@loanflow.com",
      "summary": "Required documents verified"
    },
    {
      "eventType": "UNDERWRITING_COMPLETED",
      "occurredAt": "2026-03-24T16:02:11Z",
      "performedBy": "system",
      "summary": "Application approved"
    }
  ]
}
```

**Notes**
This data is populated asynchronously from Kafka events and may be eventually consistent by a few seconds.

**Error handling**

* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`

---

## 5.2 Get Notification History

**GET** `/api/v1/notifications`

**Purpose**
Returns notifications sent to the current authenticated user.

**Auth requirement**
Authenticated

**Input parameters**

* **Query**

    * `page`
    * `size`
    * `read`

**Example request**
`GET /api/v1/notifications?page=0&size=10&read=false`

**Example response JSON**

```json
{
  "content": [
    {
      "notificationId": "ntf-4001",
      "type": "APPLICATION_STATUS",
      "title": "Application Approved",
      "message": "Your loan application #50021 has been approved.",
      "read": false,
      "createdAt": "2026-03-24T16:03:15Z"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 7,
  "totalPages": 1
}
```

**Error handling**

* `401 Unauthorized`
* `400 Bad Request`

---

## 5.3 Mark Notification as Read

**PATCH** `/api/v1/notifications/{notificationId}/read`

**Purpose**
Marks a notification as read.

**Auth requirement**
Authenticated

**Input parameters**

* **Path**

    * `notificationId`

**Example response JSON**

```json
{
  "notificationId": "ntf-4001",
  "read": true,
  "updatedAt": "2026-03-24T16:10:01Z"
}
```

**Error handling**

* `401 Unauthorized`
* `403 Forbidden`
* `404 Not Found`

---

# 6. Platform / Gateway Operational Endpoints

These are not core business features, but they are part of the realistic platform layer.

## 6.1 Service Health Check

**GET** `/actuator/health`

**Purpose**
Reports service health for Kubernetes probes and operational monitoring.

**Auth requirement**
Internal or restricted admin access depending on deployment policy

**Input parameters**
None

**Example response JSON**

```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    },
    "redis": {
      "status": "UP"
    },
    "kafka": {
      "status": "UP"
    }
  }
}
```

**Error handling**

* `503 Service Unavailable` — one or more critical dependencies unavailable

---

## 6.2 Metrics Endpoint

**GET** `/actuator/metrics`

**Purpose**
Exposes service metrics for Prometheus scraping and monitoring dashboards.

**Auth requirement**
Internal or admin-restricted

**Input parameters**
None

**Example response JSON**

```json
{
  "names": [
    "http.server.requests",
    "jvm.memory.used",
    "process.cpu.usage",
    "kafka.consumer.records.lag"
  ]
}
```

**Error handling**

* `401 Unauthorized`
* `403 Forbidden`

---

# 7. Main Workflow Mapping

## Workflow A — Applicant submits a new loan request

1. `POST /api/v1/auth/login`
2. `POST /api/v1/applications`
3. `POST /api/v1/applications/{applicationId}/documents`
4. `GET /api/v1/applications/{applicationId}`
5. `GET /api/v1/audit/applications/{applicationId}/timeline`

## Workflow B — Loan officer verifies documents and triggers underwriting

1. `POST /api/v1/auth/login`
2. `GET /api/v1/applications?status=PENDING_DOCUMENTS`
3. `GET /api/v1/applications/{applicationId}/documents`
4. `POST /api/v1/applications/{applicationId}/documents/verify`
5. `GET /api/v1/underwriting/{applicationId}`

## Workflow C — Manual exception handling

1. `POST /api/v1/underwriting/{applicationId}/decision`
2. `PATCH /api/v1/applications/{applicationId}/status`
3. `GET /api/v1/audit/applications/{applicationId}/timeline`
4. `GET /api/v1/notifications`

---

# 8. Common Status Codes Used Across Services

* `200 OK` — successful read or update
* `201 Created` — resource created
* `202 Accepted` — async processing accepted
* `400 Bad Request` — validation or malformed input
* `401 Unauthorized` — invalid or missing token
* `403 Forbidden` — authenticated but not allowed
* `404 Not Found` — resource missing
* `409 Conflict` — invalid business state or duplicate action
* `413 Payload Too Large` — upload policy violation
* `429 Too Many Requests` — Redis-backed distributed rate limit exceeded
* `500 Internal Server Error` — unexpected server-side issue
* `503 Service Unavailable` — dependency outage or circuit breaker open