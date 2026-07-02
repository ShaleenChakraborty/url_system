# URL System

> A production-oriented URL management platform built with Spring Boot to explore real-world backend engineering concepts including caching, asynchronous processing, observability, scalability, and clean software architecture.

---

## Overview

**URL System** started as a simple URL shortener but is being intentionally evolved into a production-style backend system.

The objective of this project is not only to implement features, but to understand how scalable backend systems are designed, built, tested, deployed, and maintained using modern software engineering practices.

Instead of treating this as another CRUD application, every major feature is implemented with production concerns in mind, including:

- Clean Architecture
- Layered Design
- Caching
- Asynchronous Processing
- Logging & Observability
- API Documentation
- Testing
- CI/CD
- Containerization
- Performance Optimization

---

## Current Features

- URL shortening
- URL redirection
- Click analytics
- PostgreSQL persistence
- OpenAPI / Swagger documentation
- Global exception handling
- Structured logging
- Layered Spring Boot architecture

---

## Planned Features

- Redis caching
- Cache invalidation strategies
- Kafka event-driven analytics
- Docker & Docker Compose
- GitHub Actions CI
- Unit & Integration Testing
- Rate Limiting
- Authentication & Authorization
- Prometheus Metrics
- Grafana Dashboards
- Health Monitoring
- Distributed Tracing
- Performance Benchmarking

---

# High-Level Architecture

```
                Client
                   │
                   ▼
        Spring Boot REST API
                   │
                   ▼
            UrlController
                   │
                   ▼
             UrlService
                   │
          ┌────────┴─────────┐
          ▼                  ▼
     PostgreSQL         Redis Cache
                              │
                              ▼
                     Kafka Analytics
```

> Redis and Kafka are part of the planned architecture and will be introduced incrementally.

---

# Technology Stack

| Category | Technology |
|----------|------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Build Tool | Maven |
| Database | PostgreSQL |
| Documentation | OpenAPI / Swagger |
| Logging | SLF4J + Logback |
| Version Control | Git + GitHub |
| IDE | IntelliJ IDEA |

### Planned

- Redis
- Apache Kafka
- Docker
- Docker Compose
- JUnit 5
- Mockito
- Testcontainers
- GitHub Actions
- Prometheus
- Grafana

---

# Project Structure

```
url_system
│
├── src
│   └── main
│       ├── java
│       │   └── com.urlshortener.shaleen
│       │       ├── config
│       │       ├── controller
│       │       ├── dto
│       │       ├── entity
│       │       ├── exception
│       │       ├── repository
│       │       └── service
│       │
│       └── resources
│
├── docs
│   ├── Architecture.md
│   ├── RequestFlow.md
│   ├── Database.md
│   └── Decisions.md
│
├── docker
│
├── .github
│
└── README.md
```

---

# Request Lifecycle

```
Client
   │
   ▼
DispatcherServlet
   │
   ▼
UrlController
   │
   ▼
UrlService
   │
   ▼
UrlRepository
   │
   ▼
PostgreSQL
   │
   ▼
Response
```

Future versions will extend this flow using:

- Redis Cache
- Kafka
- Metrics
- Monitoring

---

# Engineering Principles

This project is being developed with the following principles:

- Clean Code
- Separation of Concerns
- SOLID Principles
- Layered Architecture
- Maintainability
- Scalability
- Extensibility
- Observability
- Production-first Thinking

---

# Documentation

Detailed project documentation is available in the `docs/` directory.

- Architecture
- Request Flow
- Database Design
- Engineering Decisions

These documents evolve alongside the codebase.

---

# Roadmap

## Phase 1 — Foundation

- [x] Spring Boot Setup
- [x] PostgreSQL Integration
- [x] REST APIs
- [x] Swagger Documentation
- [x] Global Exception Handling
- [x] Logging

---

## Phase 2 — Performance

- [ ] Redis Integration
- [ ] Cache Invalidation
- [ ] Performance Benchmarks

---

## Phase 3 — Event Driven Architecture

- [ ] Kafka Integration
- [ ] Click Analytics Events
- [ ] Asynchronous Processing

---

## Phase 4 — DevOps

- [ ] Docker
- [ ] Docker Compose
- [ ] GitHub Actions
- [ ] Automated Testing

---

## Phase 5 — Production Readiness

- [ ] Authentication
- [ ] Rate Limiting
- [ ] Monitoring
- [ ] Metrics
- [ ] Dashboards
- [ ] Health Checks

---

# Getting Started

## Clone the repository

```bash
git clone https://github.com/ShaleenChakraborty/url_system.git
```

## Navigate into the project

```bash
cd url_system
```

## Run the application

```bash
./mvnw spring-boot:run
```

Windows

```bash
mvnw.cmd spring-boot:run
```

---

# API Documentation

After starting the application:

```
http://localhost:8080/swagger-ui/index.html
```

---

# Why this Project?

Most URL shortener projects stop after implementing CRUD functionality.

This project intentionally goes beyond that by exploring how production backend systems evolve over time through incremental improvements in architecture, scalability, performance, testing, deployment, and observability.

The focus is not only on building features, but on understanding the engineering decisions behind them.

---

# Contributing

Contributions, suggestions, and discussions are always welcome.

Please create a feature branch before opening a Pull Request.

---

# License

This project is licensed under the MIT License.

---

## Author

**Shaleen Chakraborty**

Building this project as a long-term exploration of backend engineering and production-grade software development.