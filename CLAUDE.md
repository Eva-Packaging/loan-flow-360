# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**LoanFlow 360** is an event-driven microservices platform for loan application and underwriting workflows. It is designed as both a functional application and an interview portfolio project showcasing Java/Spring Boot microservices, Kafka, React, Redis, MySQL, MongoDB, Docker, and Kubernetes.

## Commands

### Backend Services (each service has its own Maven wrapper)

```bash
# Run a service locally
cd backend/<service-name>
./mvnw spring-boot:run

# Build JAR (skip tests)
./mvnw clean package -DskipTests

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=MyTestClass

# Run a single test method
./mvnw test -Dtest=MyTestClass#myTestMethod
```

### Frontend

```bash
cd frontend/loan-flow-360
npm install        # Install dependencies
npm run dev        # Dev server at http://localhost:5173
npm run build      # Production build
npm run lint       # ESLint
npm run preview    # Preview production build
```

### Infrastructure

```bash
# Start all infrastructure dependencies (Kafka, MySQL, Redis, MongoDB)
docker compose -f infra/docker-compose.yml up -d
docker compose -f infra/docker-compose.yml down
```

## Architecture

### Services and Ports

| Service | Port | Technology |
|---|---|---|
| API Gateway | 8080 | Spring Boot 3.5.13, Java 17 |
| Identity Service | 8081 | Spring Boot, MySQL |
| Loan Application Service | 8082 | Spring Boot, MySQL |
| Document Service | 8083 | Spring Boot, MySQL, S3 |
| Underwriting Service | 8084 | Spring Boot, MySQL |
| Notification/Audit Service | 8085 | Spring Boot, MongoDB |
| Frontend | 5173 (dev) | React 19, Vite, TypeScript |

Infrastructure ports: MySQL `3306`, Redis `6379`, Kafka `9092`, MongoDB `27017`.

### Request Flow (Synchronous)

All client traffic enters through the **API Gateway** (8080), which handles JWT validation and routes to downstream services. The frontend never calls services directly.

### Event Flow (Asynchronous via Kafka)

```
Loan Service  →  loan.application.submitted  →  Document Service
Document Service  →  loan.document.verified  →  Underwriting Service
Underwriting Service  →  loan.underwriting.completed  →  Notification/Audit Service
Loan Service  →  loan.status.updated  →  Notification/Audit Service
```

Services use the **outbox pattern** for reliable Kafka event publishing and track `processed_events` for idempotent consumption.

### Data Ownership

Each service owns its own schema — there are no cross-database foreign keys. Cross-service references use business IDs (`application_id`, `user_id`). MySQL is used for transactional services; MongoDB is used for the append-heavy audit/notification service.

### Key Patterns in Use

- **Outbox pattern** (Loan Service): reliable event publishing via `outbox_events` table
- **Idempotent consumers**: `processed_events` table per consuming service
- **Redis caching**: application summaries (5–10 min TTL), reference data (30–60 min TTL)
- **Redis rate limiting**: distributed counters for login, uploads, submissions
- **Circuit breaker**: Underwriting Service fallback for external dependency failures
- **RBAC**: Three roles — `APPLICANT`, `LOAN_OFFICER`, `ADMIN` — enforced via Spring Security and JWT

## Git & PR Conventions

- Branch naming: `SCRUM-###-short-description`
- **PR titles must match `SCRUM-### Short description`** — enforced by GitHub Actions (fails the build otherwise)
- Merge strategy: squash-and-merge; delete branch after merge
- Minimum 1 approval required before merging

## Spring Boot Profile Configuration

Each service has `application.yaml` and `application-dev.yaml`. Run with the dev profile for local development:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Key Documentation

- `docs/api-reference.md` — Full REST API spec for all services (request/response shapes, status codes)
- `docs/database-schema.md` — Normalized schema definitions, indexes, Redis key patterns
- `docs/roadmap.md` — 10-sprint implementation roadmap
- `.claude/backlog-generator.md` — Spec for generating Jira backlog JSON from this codebase

## Jira Backlog generation
At the start of every new session:
1. Read `.claude/backlog-generator.md` fully.
2. Summarize the current repository status before making changes.