# 🧠 Engineering Decisions

> _"Good software is not only defined by the code that exists, but also by the decisions that led to it."_

This document records the important engineering decisions made throughout the project and explains the reasoning behind each choice.

As the project evolves, this document evolves alongside it.

---

# Decision 001

## Why Spring Boot?

### Problem

The project required a framework capable of supporting rapid backend development while remaining suitable for production-scale applications.

---

### Decision

Spring Boot was selected as the backend framework.

---

### Why?

Spring Boot provides

- Dependency Injection
- REST API support
- Validation
- Configuration Management
- Database Integration
- Excellent ecosystem
- Production-ready tooling

It also makes it easier to introduce future technologies like Redis, Kafka, Docker, Spring Security and Actuator without redesigning the project.

---

### Trade-offs

- Larger learning curve
- Higher memory usage compared to lightweight frameworks

---

# Decision 002

## Why PostgreSQL?

### Problem

The application requires reliable persistent storage for URL mappings.

---

### Decision

PostgreSQL was chosen as the primary database.

---

### Why?

- ACID compliance
- Excellent indexing
- Mature ecosystem
- Strong SQL support
- Widely used in production

PostgreSQL acts as the **single source of truth** for all application data.

---

### Trade-offs

- Slower reads compared to in-memory storage
- Requires additional optimization under heavy read traffic

---

# Decision 003

## Why Redis?

### Problem

Redirect requests are read-heavy.

Reading every request directly from PostgreSQL creates unnecessary database load.

---

### Decision

Introduce Redis as an in-memory cache.

---

### Current Strategy

The application follows a **Cache-Aside Pattern**.

```text
Request

↓

Redis

↓

Cache Hit

↓

Return

OR

↓

Cache Miss

↓

PostgreSQL

↓

Store in Redis

↓

Return
```

---

### Why?

Redis significantly reduces database reads while providing sub-millisecond access for frequently requested URLs.

---

### Trade-offs

Redis introduces cache consistency challenges.

To address this,

every redirect

- updates PostgreSQL
- refreshes Redis

ensuring cached data remains synchronized.

---

# Decision 004

## Why DTOs?

### Problem

Entities should not be exposed directly through REST APIs.

---

### Decision

Separate Request and Response objects from persistence models.

---

### Why?

DTOs

- hide internal implementation
- reduce coupling
- improve API stability
- simplify validation

Current DTOs

- UrlRequest
- UrlResponse
- AnalyticsResponse
- ErrorResponse

---

# Decision 005

## Why Layered Architecture?

### Decision

The project follows

```text
Controller

↓

Service

↓

Repository

↓

Database
```

---

### Why?

Each layer owns a single responsibility.

Controllers

- receive requests

Services

- execute business logic

Repositories

- interact with the database

This separation improves

- maintainability
- testing
- scalability

---

# Decision 006

## Why Logging?

### Problem

Production systems require visibility into request execution.

---

### Decision

Use SLF4J with Logback.

---

### Current Logging

The application currently logs

- incoming requests
- URL creation
- duplicate detection
- cache hits
- cache misses
- redirects
- Redis synchronization
- analytics requests

---

### Future Improvements

- Structured JSON logging
- Correlation IDs
- Request tracing

---

# Future Decisions

The following decisions will be documented as the project evolves.

- Kafka
- Docker
- Spring Security
- Rate Limiting
- Authentication
- Monitoring
- Prometheus
- Grafana
- CI/CD
- Testing Strategy

---

# Guiding Principle

Every technology introduced into the project should solve a real engineering problem.

New components are added because they improve the architecture—not simply because they are popular technologies.