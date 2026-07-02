# 🚀 Request Flow

> _"Every request has a journey. This document follows that journey from the browser to the database and back."_

---

# 🌎 Meet Our Traveler

Every request entering the system starts here.

```text
🌐 Browser
     │
     ▼
🚪 Spring Boot
     │
     ▼
🚦 DispatcherServlet
     │
     ▼
🎮 UrlController
     │
     ▼
🧠 UrlService
     │
     ▼
💾 PostgreSQL
```

The controller is the receptionist.

The service is the decision maker.

The database is the source of truth.

---

# 🟢 Mission 1 — Create a Short URL

### Endpoint

```
POST /shorten
```

Imagine Alice wants to shorten

```
https://openai.com
```

---

## Journey

```text
👩 Client
   │
   ▼
📨 POST /shorten
   │
   ▼
🎮 UrlController
   │
   ▼
🧠 UrlService
   │
   ▼
🔍 Duplicate URL?
   │
 ┌─┴─────────────┐
 │               │
😊 Found      ❌ Not Found
 │               │
 │          🎲 Generate Base62 Code
 │               │
 └──────┬────────┘
        ▼
💾 Save into PostgreSQL
        │
        ▼
📦 UrlResponse DTO
        │
        ▼
👩 Client receives
https://localhost:8080/aB92Xz
```

---

## Behind the Scenes

🧠 UrlService

✔ Checks duplicate URLs

✔ Generates Base62 short code

✔ Saves mapping

✔ Returns DTO

No Redis is involved here because this is a write operation.

---

# 🔵 Mission 2 — Redirect

### Endpoint

```
GET /aB92Xz
```

This is where things become interesting.

Instead of immediately asking PostgreSQL...

The application first asks Redis.

---

## Adventure Begins

```text
👩 Client
   │
   ▼
🌐 GET /aB92Xz
   │
   ▼
🎮 UrlController
   │
   ▼
🧠 UrlService
   │
   ▼
⚡ Redis
```

---

## 🟢 Path A — Cache Hit

Redis smiles 😊

```
"I already know this URL."
```

```text
⚡ Redis
   │
   ▼
📦 Url Object
   │
   ▼
📈 Click++
   │
   ▼
💾 PostgreSQL Update
   │
   ▼
⚡ Refresh Redis
   │
   ▼
🌍 Redirect Browser
```

Fast.

One database write.

Zero database reads.

---

## 🔴 Path B — Cache Miss

Redis shrugs 🤷

```
"I don't have it."
```

```text
⚡ Redis
   │
❌ Cache Miss
   │
   ▼
💾 PostgreSQL
   │
   ▼
📦 Url Object
   │
   ▼
⚡ Save into Redis
   │
   ▼
📈 Click++
   │
   ▼
💾 PostgreSQL Update
   │
   ▼
⚡ Refresh Redis
   │
   ▼
🌍 Redirect Browser
```

The next visitor benefits from the cached result.

---

# 🟣 Mission 3 — Analytics

```
GET /analytics/aB92Xz
```

Current journey

```text
👨 User
   │
   ▼
🎮 UrlController
   │
   ▼
🧠 UrlService
   │
   ▼
💾 PostgreSQL
   │
   ▼
📊 AnalyticsResponse
```

Future versions will publish click events through Kafka.

---

# ⚡ Redis Strategy

The application follows the **Cache-Aside Pattern**.

```text
                Request
                   │
                   ▼
            ⚡ Check Redis
                   │
          ┌────────┴────────┐
          │                 │
      😊 HIT            😢 MISS
          │                 │
          ▼                 ▼
     Return URL       PostgreSQL
          │                 │
          │          Cache Result
          └────────┬────────┘
                   ▼
             Continue Request
```

---

# 🔄 Cache Synchronization

Every redirect updates Redis.

```text
📈 Click++

↓

💾 PostgreSQL

↓

⚡ Redis Refresh

↓

✅ Next request is fresh
```

Redis is **never** treated as the source of truth.

PostgreSQL always owns the data.

---

# 🎭 Who Does What?

| Character | Responsibility |
|-----------|----------------|
| 🌐 Browser | Sends Requests |
| 🚦 DispatcherServlet | Routes Requests |
| 🎮 UrlController | Receives HTTP Requests |
| 🧠 UrlService | Business Logic |
| ⚡ Redis | Fast Cache |
| 💾 PostgreSQL | Persistent Storage |

---

# 🗺️ Entire Journey

```text
                👩 Client
                    │
                    ▼
             🌐 HTTP Request
                    │
                    ▼
         🚦 DispatcherServlet
                    │
                    ▼
            🎮 UrlController
                    │
                    ▼
             🧠 UrlService
          ┌────────┼─────────┐
          ▼        ▼         ▼
      ⚡ Redis   💾 PostgreSQL
          │
          ▼
   📦 Response / Redirect
          │
          ▼
        👩 Client
```

---

# 🚀 Future Journey

The story will continue.

```text
Client

↓

Controller

↓

Service

↓

Redis

↓

PostgreSQL

↓

Kafka 📬

↓

Analytics Service 📊

↓

Prometheus 📈

↓

Grafana 📉
```

The architecture grows...

The journey remains the same.