# Postman Collections — LoanFlow 360

## Files

| File | Description |
|---|---|
| `loanflow-360.postman_collection.json` | All requests routed through the **API Gateway** |
| `loanflow-360.direct-services.postman_collection.json` | Each folder targets its **own service** directly, bypassing the gateway |
| `loanflow-360.local.postman_environment.json` | Local gateway environment (`localhost:8080`) |
| `loanflow-360.dev.postman_environment.json` | Dev/staging gateway environment |
| `loanflow-360.local-direct.postman_environment.json` | Local direct-service environment (ports 8081–8085) |
| `loanflow-360.dev-direct.postman_environment.json` | Dev/staging direct-service environment |

## Quickstart

1. Import the collection JSON into Postman (`File → Import`)
2. Import and select the matching environment
3. Run **Login** in folder `1. Identity & Access` — the test script captures `accessToken` automatically
4. All subsequent requests inherit the token via collection-level bearer auth

## Environment Variables

### Gateway collections

| Variable | Set by | Description |
|---|---|---|
| `baseUrl` | Environment file | API Gateway base URL |
| `loginUsername` | Environment file | Login credential |
| `loginPassword` | Environment file (secret) | Login credential |
| `accessToken` | Login test script | JWT — auto-captured |
| `refreshToken` | Login test script | Refresh token — auto-captured |
| `applicationId` | Create Application test script | Auto-captured on 201 |
| `documentId` | Upload Document test script | Auto-captured on 201 |
| `notificationId` | Set manually | From Get Notification History response |

### Direct-service collections

Same as above, except `baseUrl` is replaced by one variable per service:

| Variable | Default (local) | Service |
|---|---|---|
| `identityServiceUrl` | `http://localhost:8081` | Identity Service |
| `loanServiceUrl` | `http://localhost:8082` | Loan Application Service |
| `documentServiceUrl` | `http://localhost:8083` | Document Service |
| `underwritingServiceUrl` | `http://localhost:8084` | Underwriting Service |
| `auditServiceUrl` | `http://localhost:8085` | Notification/Audit Service |

## Collection Structure

Each folder maps 1:1 to a service. Folder names and request names mirror the headings in `docs/api-reference.md`.

```
1. Identity & Access          → identityServiceUrl  (gateway: /api/v1/auth/*)
2. Loan Applications          → loanServiceUrl      (gateway: /api/v1/applications/*)
3. Document Management        → documentServiceUrl  (gateway: /api/v1/applications/{id}/documents/*)
4. Underwriting & Decisions   → underwritingServiceUrl (gateway: /api/v1/underwriting/*)
5. Notifications & Audit      → auditServiceUrl     (gateway: /api/v1/audit/*, /api/v1/notifications/*)
6. Platform / Ops             → (gateway collection only — actuator endpoints)
```

Each folder in the direct-service collection also contains a **Health Check** request (`GET /actuator/health`) so individual services can be verified in isolation.

## Keeping Collections in Sync

`docs/api-reference.md` is the authoritative API spec. The Postman collections must stay in sync with it.

**Rule: any PR that adds or changes an endpoint must update both `api-reference.md` and the Postman collections in the same commit.** This is enforced by the Author Checklist in `.github/PULL_REQUEST_TEMPLATE.md`.

### Adding a new endpoint

1. Add the endpoint spec to `docs/api-reference.md` under the correct service section
2. Open the matching collection JSON
3. Locate the correct folder and add a new request object — copy the nearest sibling as a starting point
4. Update `name`, `method`, `url.raw`, `url.path`, `body.raw`, and the `response` examples
5. If the response creates a resource that other requests depend on, add a test script to capture the ID into a collection variable (see Login or Create Application for the pattern)
6. Update **both** collection files (gateway and direct-service) unless the endpoint is only relevant to one

### Editing an existing endpoint

Update the `url`, `body`, and `response` fields in both collection JSONs to match the revised spec. If a path param or variable name changes, update the collection-level `variable` block too.

### Preferred workflow for complex changes

For changes involving many requests, editing the JSON directly is error-prone. Instead:

1. Import the current collection into Postman
2. Make edits in the Postman UI
3. Export (`File → Export → Collection v2.1`) and overwrite the file in `docs/postman/`
4. Commit the result

### Request object reference

Minimum fields required for a new request:

```json
{
  "name": "Human-readable name matching api-reference.md",
  "request": {
    "method": "GET | POST | PATCH | PUT | DELETE",
    "header": [],
    "url": {
      "raw": "{{serviceUrlVar}}/api/v1/path/{{variableName}}",
      "host": ["{{serviceUrlVar}}"],
      "path": ["api", "v1", "path", "{{variableName}}"]
    }
  },
  "response": []
}
```

For requests with a body, add:

```json
"body": {
  "mode": "raw",
  "raw": "{ ... }"
},
"header": [{ "key": "Content-Type", "value": "application/json" }]
```

For public endpoints that must skip the collection-level bearer auth:

```json
"auth": { "type": "noauth" }
```
