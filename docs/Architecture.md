# Software Architecture

## Overview

**URL System** is a layered Spring Boot application designed to explore production-oriented backend engineering practices through the implementation of a URL shortening platform.

Although the application began as a simple URL shortener, its architecture is intentionally designed to support gradual evolution into a scalable backend system by introducing technologies such as Redis, Kafka, Docker, automated testing, monitoring, and CI/CD without requiring major architectural changes.

The project follows a clear separation of responsibilities across application layers to maximize maintainability, extensibility, and testability.

---

# Architectural Goals

The architecture has been designed around the following engineering principles.

- Separation of Concerns
- Layered Architecture
- Single Responsibility Principle
- Loose Coupling
- High Cohesion
- Production-Oriented Design
- Extensibility
- Observability
- Maintainability

Rather than optimizing for the smallest amount of code, the project prioritizes clean boundaries between application components and prepares the codebase for future production features.

---

# System Architecture

Current Architecture

```text
                    Client
                       │
                       ▼
               Spring Boot API
                       │
                       ▼
                DispatcherServlet
                       │
                       ▼
                 UrlController
                       │
                       ▼
                  UrlService
                ┌──────┴────────┐
                ▼               ▼
          Redis Cache      PostgreSQL
                │
                ▼
           Cache Refresh
```

---

# Architectural Layers

The application is divided into four logical layers.

```text
Presentation Layer

↓

Business Layer

↓

Persistence Layer

↓

Infrastructure Layer
```

Each layer owns a specific responsibility and communicates only with adjacent layers.

---

# Presentation Layer

**Package**

```
controller
```

### Responsibilities

- Expose REST endpoints
- Receive HTTP requests
- Validate request payloads
- Delegate business logic
- Return HTTP responses

The controller layer contains no business logic.

Current Components

```
UrlController
```

---

# Business Layer

**Package**

```
service
```

The Service Layer represents the core of the application.

Responsibilities include

- URL generation
- Duplicate URL detection
- Base62 short-code generation
- Redirect processing
- Click analytics
- Redis cache coordination
- Business rule enforcement

Current Components

```
UrlService
```

Unlike the Controller layer, this layer owns all application behaviour.

---

# Persistence Layer

**Package**

```
repository
entity
```

Responsibilities

- Database interaction
- Entity persistence
- Query execution
- Mapping database records to Java objects

Current Components

```
UrlRepository

Url Entity
```

The persistence layer remains unaware of HTTP requests or caching logic.

---

# Infrastructure Layer

Packages

```
config

exception
```

Responsibilities

- Redis configuration
- OpenAPI configuration
- Global exception handling
- Application infrastructure

Current Components

```
RedisConfig

OpenApiConfig

GlobalExceptionHandler
```

As the project evolves, additional infrastructure components such as Kafka, Docker configuration and Security will be introduced into this layer.

---

# Current Request Flow

## URL Creation

```text
POST /shorten

↓

DispatcherServlet

↓

UrlController

↓

UrlService

↓

Duplicate URL Check

↓

Generate Base62 Short Code

↓

Save to PostgreSQL

↓

Return Response DTO
```

---

## URL Redirection

The redirection endpoint is optimized using Redis.

```text
GET /{shortCode}

↓

UrlController

↓

UrlService

↓

Redis Lookup

↓

Cache Hit?
      │
 ┌────┴────┐
 │         │
Yes       No
 │         │
 │     PostgreSQL
 │         │
 │     Cache Result
 └────┬────┘
      ▼
Increment Click Count

↓

Persist Updated Entity

↓

Synchronize Redis Cache

↓

Redirect Client
```

This architecture minimizes unnecessary database reads while ensuring Redis remains synchronized after each redirect.

---

## Analytics

```text
GET /analytics/{shortCode}

↓

UrlController

↓

UrlService

↓

PostgreSQL

↓

AnalyticsResponse DTO
```

Currently analytics are retrieved synchronously from PostgreSQL.

Future versions will publish click events asynchronously using Kafka.

---

# Cache Architecture

The application currently implements a Cache-Aside pattern.

```text
Client

↓

Redis

↓

Cache Hit
      │
      ▼
Return Cached Object

OR

↓

Cache Miss

↓

PostgreSQL

↓

Populate Redis

↓

Return Object
```

Following every redirect,

1. Click count is incremented.
2. PostgreSQL is updated.
3. Redis cache is synchronized.

This guarantees that subsequent reads receive fresh data while reducing unnecessary database access.

---

# Package Structure

```text
config/
controller/
dto/
entity/
exception/
repository/
service/
```

Each package groups classes according to responsibility rather than feature, making navigation straightforward and reducing coupling between layers.

---

# Logging Strategy

Logging is implemented using **SLF4J** with **Logback**.

Important application events currently logged include

- Incoming API requests
- URL generation
- Duplicate URL detection
- Cache hits
- Cache misses
- Redis synchronization
- Redirect operations
- Analytics requests

This logging strategy provides visibility into request execution while assisting debugging and future monitoring.

---

# Design Patterns

Current architectural patterns include

| Pattern | Usage |
|----------|-------|
| Layered Architecture | Overall application structure |
| Repository Pattern | Database abstraction |
| DTO Pattern | Request / Response separation |
| Dependency Injection | Spring-managed components |
| Cache-Aside Pattern | Redis caching |
| Exception Handler Pattern | Centralized error handling |

Future patterns may include

- Event-Driven Architecture
- Publisher-Subscriber
- Strategy Pattern
- Factory Pattern

---

# Future Architecture

The application is intentionally designed for incremental evolution.

```text
                    Client
                       │
                       ▼
                 Spring Boot API
                       │
                       ▼
                  UrlController
                       │
                       ▼
                   UrlService
          ┌───────────┼────────────┐
          ▼           ▼            ▼
      Redis      PostgreSQL     Kafka
          │                        │
          ▼                        ▼
    Fast Reads            Analytics Events
                                   │
                                   ▼
                             Monitoring
                                   │
                                   ▼
                               Grafana
```

Each new component is introduced without violating existing layer boundaries, allowing the system to grow while remaining maintainable.

---

# Summary

The current architecture emphasizes clean separation of responsibilities while introducing production-oriented concerns such as caching, structured logging, centralized exception handling, and API documentation.

As the project evolves, additional infrastructure components will be integrated without fundamentally changing the existing application structure, demonstrating how backend systems mature incrementally through well-defined architectural boundaries.