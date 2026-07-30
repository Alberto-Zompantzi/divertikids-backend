<div align="center">

# 🎈 DivertiKids — Backend

### **Stateless Booking Reservation Microservice — Spring Boot 3 + Java 21 + Jakarta Bean Validation + JPA/Hibernate + PostgreSQL + OpenAPI Swagger UI + Alpine Docker**

[![API Health](https://img.shields.io/badge/🔌_Health_Check-Render%20Live-46E3B7?style=for-the-badge&logo=render&logoColor=white)](https://divertikids-backend.onrender.com/api/v1/booking/health)
&nbsp;&nbsp;
[![Swagger UI](https://img.shields.io/badge/📘_OpenAPI_Swagger-Auto--Generated-85EA2D?style=for-the-badge&logo=swagger&logoColor=white)](https://divertikids-backend.onrender.com/swagger-ui/index.html)
&nbsp;&nbsp;
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
&nbsp;&nbsp;
[![Java](https://img.shields.io/badge/Java-21%20LTS-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://adoptium.net/)

</div>

<p align="center">
<img width="90%" alt="DivertiKids Backend Architecture" src="https://divertikids.pages.dev/Divertikids.jpeg" style="max-width:700px;border-radius:1rem;" />
</p>

## Architecture Grade Summary

Production-hardened **versioned REST booking microservice**:
- Strict **N-Tier architecture**: Controller → Bean-Validated DTO → Service Interface/Impl → JPA Repository → Entity.
- **Jakarta Bean Validation** centralizes input contracts before persistence — validated date must be present or future, email pattern, non-negative currency, time range.
- **SpringDoc OpenAPI 2.x auto-docs** — Swagger UI browser with `Try it out` for bookings.
- **Twelve-Factor env-only secret injection**: `DATABASE_URL`, `DATABASE_USER`, `DATABASE_PASSWORD` are never committed — only env vars.
- **Gradle Kotlin DSL + toolchain** (reproducible Java 21 builds) → Alpine Docker image.
- **Local docker-compose Postgres 17-alpine** named volume persistence for integration testing.

---

## Backend Badges & Tech Stack

![Java](https://img.shields.io/badge/Java-21%20LTS-ED8B00?logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?logo=springboot&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-JPA%20ORM-59666C?logo=hibernate&logoColor=white)
![Jakarta Bean](https://img.shields.io/badge/Jakarta_EE-Bean_Validation-6DB33F?logo=eclipse)
![Swagger](https://img.shields.io/badge/OpenAPI-Swagger%20UI-85EA2D?logo=swagger)
![Gradle](https://img.shields.io/badge/Gradle-Kotlin%20DSL-02303A?logo=gradle&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17%20Managed-336791?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Alpine%20JDK-2496ED?logo=docker&logoColor=white)
![Docker Compose](https://img.shields.io/badge/Docker%20Compose-v2-2496ED?logo=docker)
![Lombok](https://img.shields.io/badge/Lombok-Boilerplate%20Reduction-59666C?logo=lombok)
![JUnit 5](https://img.shields.io/badge/JUnit-5%20Platform-25A162?logo=junit5&logoColor=white)
![Render](https://img.shields.io/badge/Deploy-Render%20Container-46E3B7?logo=render&logoColor=white)

---

## 🚀 Core Features Breakdown

| Layer | Feature | Technical Pattern |
|-------|---------|-------------------|
| **🗓️ Booking REST API** | Versioned Namespace | All endpoints under `/api/v1/**` — future `/api/v2` rollout can run alongside without breaking clients |
| | Validated DTO Boundary | DTO carries 6 Jakarta Bean constraints (`@NotBlank` name, `@Email`, `@FutureOrPresent` event date, `@JsonFormat` time range, `@PositiveOrZero` currency). Invalid payload returns 400 with field-level messages. |
| | Semantic HTTP Status Codes | `POST /booking` success returns **201 Created** (not ambiguous 200). Health check returns 200 OK plain text. |
| | Cross-Origin Policy | Controller-level global CORS wildcard allows SPA + mobile client integration without separate gateway. |
| | Auto-Generated OpenAPI Docs | SpringDoc dependency auto-generates OpenAPI 3 spec + Swagger UI at `/swagger-ui/index.html` with `Try it out`. |
| **🧠 DTO → Entity Service Layer** | Interface + Impl Separation | `BookingService` (interface: contract) → `BookingServiceImpl` (@Service concrete). Easy to swap out for Mock impl in test. |
| | Autoregistration Timestamp | `@PrePersist entity callback` stamps `registrationDate = now()` automatically at INSERT — never set manually. |
| | High-Precision Currency | `BigDecimal(precision=10, scale=2)` for budget fields — avoids float rounding drift typical of Double-based prices. |
| | Time Typesafe Fields | `LocalDate` (event date) + `LocalTime` (start/end) — no legacy `java.util.Date` timezone bugs. |
| **🗃️ Persistence** | JPA Hibernate DDL-Auto Update | Dev profile: `ddl-auto=update` evolves schema without SQL. Production-ready env can switch to `validate` + Flyway later. |
| | Twelve-Factor Env Secrets Only | `spring.datasource.url/username/password` read EXCLUSIVELY from `DATABASE_URL/USER/PASSWORD` env vars. Zero secrets in source code. |
| | Postgres 17-Alpine Docker Local | Compose `postgres:17-alpine` service + named `postgres_data` volume — identical to Neon/Supabase managed Postgres 17 providers. |
| | SQL Formatting Dev Toggle | `show-sql + format_sql` enabled for local dev. Production: off by default to reduce log noise. |
| **☁️ Container / Deploy** | Gradle Kotlin DSL Toolchain 21 | `java.toolchain.languageVersion = JavaLanguageVersion.of(21)` guarantees builds use exactly Java 21 across every workstation (no "works on my machine" JDK 17/20 drift). |
| | Alpine Slim Docker Image | `eclipse-temurin:21-jdk-alpine` base image — Gradle `bootJar` → single JAR. Small footprint. |
| | Render Container Runtime | PaaS Docker runtime deployed from backend subdirectory. Health check URL configured — auto-restart on cold-stuck. |
| | Gradle Wrapper Reproducibility | `gradle-wrapper.jar` committed → anyone builds with exact Gradle version without local install. |

---

## 📐 Architecture Diagram (ASCII)


                          ┌─────────────────────────────────────────────────────────────┐
                          │              DivertiKids Booking Request Flow              │
                          └──────────────────────────────┬──────────────────────────────┘
                                                         │
                          ┌──────────────────────────────┴─────────────────────────────┐
                          │   CLIENT (React SPA / Postman / cURL / Swagger UI)          │
                          │   (DivertiKids.pages.dev · localhost:5173 · mobile)         │
                          └──────────────────────────────┬──────────────────────────────┘
                                                         │
                                                         │ HTTPS POST JSON
                                                         │ body = { name, email, date,
                                                         │         start, end, budget }
                          ┌──────────────────────────────▼──────────────────────────────┐
                          │        RENDER ALPINE DOCKER CONTAINER (Free Tier)           │
                          │  ┌───────────────────────────────────────────────────────┐ │
                          │  │  SPRING BOOT 3.x · JAVA 21 LTS ALPINE                │ │
                          │  │  ┌─────────────────────────────────────────────────┐  │ │
                          │  │  │  @RestController /api/v1/booking (CORS: *)      │  │ │
                          │  │  │  ┌─────────────────────────────────────────────┐│  │ │
                          │  │  │  │  @PostMapping          @GetMapping /health ││  │ │
                          │  │  │  └─────────────────────────────────────────────┘│  │ │
                          │  │  └───────────────────────────┬─────────────────────────┘ │
                          │  │                              │ @Valid BookingDTO        │
                          │  │                              │ Jakarta Bean validation │
                          │  │  ┌───────────────────────────▼─────────────────────────┐ │
                          │  │  │  BookingService (Interface)                          │ │
                          │  │  │  BookingServiceImpl (@RequiredArgsConstructor inj) │ │
                          │  │  │  - createBooking(dto) → entity → repository.save() │ │
                          │  │  └───────────────────────────┬─────────────────────────┘ │
                          │  │                              │ JPA Entity Mapping       │
                          │  │  ┌───────────────────────────▼─────────────────────────┐ │
                          │  │  │  Booking JpaRepository< Booking, Long >             │ │
                          │  │  │  (Spring Data generated impl at runtime)            │ │
                          │  │  └───────────────────────────┬─────────────────────────┘ │
                          │  └──────────────────────────────┼───────────────────────────┘ │
                          │                                 │ HikariCP · Hibernate ORM    │
                          └─────────────────────────────────┼─────────────────────────────┘
                                                            │
                          ┌─────────────────────────────────▼─────────────────────────────┐
                          │         MANAGED POSTGRESQL (Neon/Supabase-style 17.x)          │
                          │    ┌──────────────────────────────────────────────────────┐   │
                          │    │                    TABLE: booking                     │   │
                          │    │  id PK · name · email · eventDate · start · end     │   │
                          │    │  budget NUMERIC(10,2) · registrationDate TIMESTAMP   │   │
                          │    └──────────────────────────────────────────────────────┘   │
                          └────────────────────────────────────────────────────────────────┘
---

## 🧩 Project-Specific Technical Skills

| Category | Exact Stack |
|----------|-------------|
| **☕ Backend Runtime** | Java 21 LTS (JDK toolchain pinned at Gradle level) · Spring Boot 3.x (Web MVC + Data JPA + Jakarta Bean Validation starter) · Spring Boot DevTools local live reload · Spring Dependency Management plugin 1.x |
| **🗃️ Persistence & ORM** | PostgreSQL (managed Neon-like provider · 17-alpine Docker local) · Hibernate ORM (Spring Data JPA default) · `GenerationType.IDENTITY` surrogates · `BigDecimal` currency precision fields · `LocalDate / LocalTime / LocalDateTime` JSR-310 safe time types |
| **✅ Validation & OpenAPI** | Jakarta Bean Validation annotations (`@NotBlank`, `@Email`, `@FutureOrPresent`, `@PositiveOrZero`) · Jackson `@JsonFormat` for `LocalTime` flexible parse · SpringDoc 2.x (`springdoc-openapi-starter-webmvc-ui`) → OpenAPI 3 spec + Swagger UI auto-generated |
| **🧱 Backend Architecture** | N-Tier contract: `@RestController → @Valid DTO → Service Interface + ServiceImpl (@Service) → JpaRepository → @Entity` · Lombok boilerplate reduction (`@Data`, `@RequiredArgsConstructor`) · ResponseEntity + Semantic HTTP Status (201 Created · 404 Not Found · 400 Bad Request for violations) · Versioned REST namespace `/api/v1/*` · @PrePersist entity lifecycle callback |
| **🏗️ Build Tooling** | Gradle latest · Kotlin DSL `.kts` build scripts · Java Toolchain 21 pinned · JUnit Platform test task · Gradle Wrapper `gradlew` + gradle-wrapper.jar (no local Gradle needed) |
| **☁️ DevOps & Deployment** | Alpine Temurin 21 Dockerfile (`bootJar` + single COPY to runtime) · Docker Compose v2: Postgres 17-alpine service + named volume persistence · PaaS container deploy + auto-documented health probe path · Environment-only datasource credentials (Twelve-Factor) |

---

## 🐳 Docker & Local Execution (Step-by-Step)

### Prerequisites
- Java 21 LTS (Eclipse Temurin / OpenJDK 21). Gradle Toolchain auto-downloads pinned JDK when missing.
- Docker Desktop 24+ (for Docker Postgres service / build / compose).
- (Optional) Postgres 17 client if using managed provider locally.
- No local Gradle install needed — wrapper included.

---

### Option 1 — Quick 1: Run Against Managed Postgres Neon/Supabase
1. Create Postgres DB on Neon/Supabase/Render Managed → copy `postgres://user:pass@host:5432/dbname`.
2. Expose three env vars (or create `.env` at backend root):
   ```
   SPRING_PROFILES_ACTIVE=dev
   DATABASE_URL=jdbc:postgresql://<managed-host>:5432/<dbname>?sslmode=require
   DATABASE_USER=<your-user>
   DATABASE_PASSWORD=<your-pass>
   ```
3. Boot:
   ```bash
   cd divertikids-backend
   ./gradlew bootRun              # Linux/macOS (Git Bash on Windows also OK)
   # .\gradlew.bat bootRun        # Windows CMD/PowerShell
   ```
4. Verify:
   ```
   GET http://localhost:8080/api/v1/booking/health       → 200 "Servidor despierto y listo"
   GET http://localhost:8080/swagger-ui/index.html        → Swagger UI OpenAPI browser
   ```

---

### Option 2 — Quick 2: Local Docker-Compose Postgres + Local App
No managed provider required. Runs Postgres 17-alpine in Docker:

```bash
cd divertikids-backend

# 1) Launch DB only (named volume persists data across restarts)
docker compose up -d db

# 2) Export env vars pointing to the local Compose DB (use your own generic user/pass, never hardcode committed)
export DATABASE_URL="jdbc:postgresql://localhost:5432/<dbname>"
export DATABASE_USER="<local-user>"
export DATABASE_PASSWORD="<local-pass>"

# 3) Boot Spring Boot app (uses ddl-auto=update to create booking table)
./gradlew bootRun

# 4) Smoke test — create a booking via curl:
curl -X POST http://localhost:8080/api/v1/booking \
  -H "Content-Type: application/json" \
  -d '{ "name":"Family Garcia","email":"test@example.com",
        "eventDate":"2099-12-01","startTime":"14:00","endTime":"19:00",
        "budget":4500.00 }' -i
# → HTTP/1.1 201 Created + persisted JSON entity with id & registrationDate
```

---

### Option 3 — Full Dockerized Build + Container Run
Same Alpine image deployed to PaaS, run locally against docker-compose DB:

```bash
cd divertikids-backend

# 1) DB up
docker compose up -d db

# 2) Build Docker image (Gradle bootJar runs inside Docker build using toolchain Java 21)
docker build -t divertikids-backend .

# 3) Run backend container on 8080 — link / network + env injection
docker run -d --name divertikids-be \
  -p 8080:8080 \
  --network="divertikids_default" \
  -e SPRING_PROFILES_ACTIVE=dev \
  -e DATABASE_URL="jdbc:postgresql://db:5432/<dbname>" \
  -e DATABASE_USER="<local-user>" \
  -e DATABASE_PASSWORD="<local-pass>" \
  divertikids-backend

# 4) Inspect
docker logs -f divertikids-be
curl -s http://localhost:8080/api/v1/booking/health
```

---

## 🔌 API Endpoints Reference

**Base URL:** `{host}/api/v1/booking`

| Method | Path | Payload / Params | Contract & Behavior |
|--------|------|------------------|---------------------|
| `POST` | `/` | **JSON Body** `{ name, email (@Email), eventDate (@FutureOrPresent), startTime, endTime, budget (BigDecimal ≥0) }` | **Booking Creation** — Jakarta Bean validates input. Valid → Service converts DTO → Entity → `@PrePersist` stamps `registrationDate`. Response → `201 Created` + full persisted Booking entity with id. Invalid → global handler returns `400 Bad Request` with field-level error messages. |
| `GET`  | `/health` | — | **Readiness / Wakeup probe**. Returns `200 OK` with plaintext "server awake and ready". Used by: PaaS auto-health poll, React SPA frontend `wakeupBackend()` fire-and-forget on first visit to bust free-tier cold start. |
| `GET`  | **Swagger UI** | `/swagger-ui/index.html` | Auto-generated OpenAPI 3 documentation browser. Click `Try it out` → send test POST requests directly from browser (never use production DB here). |
| `GET`  | **Raw OpenAPI JSON** | `/v3/api-docs` | OpenAPI 3 JSON spec import into Postman collections / Insomnia / API gateways. |

---

## ☁️ Deployment Guide (Production)

### Backend → Render Docker Container Service
1. Push latest main to GitHub.
2. Render → New → Web Service → Repo.
3. **Root Directory:** `divertikids-backend`
4. Runtime: **Docker** (not Java — use committed Dockerfile).
5. **Health Check Path:** `/api/v1/booking/health` (prevents cold-stuck free-tier container).
6. **Environment Variables:**
   | Key | Value |
   |-----|-------|
   | `SPRING_PROFILES_ACTIVE` | dev or custom prod profile name |
   | `DATABASE_URL` | Neon/Render/Supabase managed JDBC URL `jdbc:postgresql://host:5432/db?sslmode=require` |
   | `DATABASE_USER` | Managed DB username |
   | `DATABASE_PASSWORD` | Managed DB password (sync-protected) |
   | `JAVA_TOOL_OPTIONS` | `-Xmx<heap-ceiling>m` (match free-tier container memory) |
7. Deploy. First Swagger UI call triggers Hibernate schema update → booking table created → health transitions healthy.

### Production Hardening Roadmap
- Replace `ddl-auto=update` with **Flyway/Liquibase versioned migrations** + `ddl-auto=validate`.
- Add **Spring Security** stateless JWT RBAC (future admin cancellation endpoints scope).
- Add `@Transactional` rollback on service failures.
- Hikari explicit pool tuning (`spring.datasource.hikari.*`) for managed Postgres, matching free-tier managed connection cap.
- Centralized `@ControllerAdvice @ExceptionHandler(MethodArgumentNotValidException.class)` to return RFC-7807 Problem Detail JSON (Spring Boot 3 default formatter).

---

## 📊 Production Quality Gates (Measurable Pattern-Based)

| Signal | Current Approach / Value | Rationale |
|--------|---------------------------|-----------|
| **Input Contract Strictness** | 6 Jakarta Bean constraints on every `POST` | 0 invalid entity ever reaches service layer. All errors caught BEFORE business logic. |
| **Persistence Precision** | `BigDecimal(10,2)` budget currency | No float rounding drift — client quote always exactly matches DB stored value in MXN. |
| **Time Type Safety** | JSR-310 `LocalDate/LocalTime/LocalDateTime` · `@JsonFormat HH:mm[:ss]` | Zero timezone bugs vs legacy `java.util.Date`. Flexible time parse accepts both 14:00 and 14:00:00 input. |
| **Reproducible Builds** | Gradle Toolchain = Java 21 + wrapper | `./gradlew build` always builds with Java 21, regardless of developer's local JAVA_HOME. |
| **Secret Safety** | Datasource 100% env-only (DATABASE_* vars) | Zero passwords committed. `.gitignore` + `.gitattributes` prevent accidental YAML leaks. |
| **Cold Start UX** | Frontend fire-and-forget `/health` call at bootstrap | Free-tier 45-60s cold start runs in background — user interacts with landing hero + activities during warmup; by checkout form time, backend is fully ready. |
| **Swagger Discoverability** | SpringDoc auto-docs → out-of-date zero-cost | API contract always matches current codebase. Zero manual docs drift. |

---

## 🧭 Future Roadmap

- [ ] **Admin Cancel + Lookup Endpoints**: Protected POST `/cancel/{id}` + GET `/customer?email=` behind JWT RBAC Spring Security.
- [ ] **Flyway Migrations**: `V1__create_booking.sql` + `ddl-auto=validate` enforced.
- [ ] **Booking Confirmation Email**: Spring Mail async (`@Async`) on POST 201 — send MXN quote + event details to customer.
- [ ] **Rate Limiting**: Bucket4j + bucket-per-IP to prevent booking form spam.
- [ ] **Integration Tests**: Testcontainers Postgres 17 module → real end-to-end `POST` booking → `repository.count()` assertions.
- [ ] **Time Range Validation**: Service-level rule `endTime.isAfter(startTime)` with custom Jakarta validator + message.
- [ ] **i18n Error Messages**: Spanish bean validation messages bundle.

---

## 🤝 Contributing

1. Fork + clone repo.
2. Feature branch off `main`.
3. Conventional commit messages: `feat:`, `fix:`, `docs:`, `refactor:`, `test:`.
4. Run all three local execution options (Managed DB / Compose DB / Dockerized) to make sure no environment regressions.
5. Always test the Swagger UI `Try it out` happy path + 2 failure paths (missing email · past event date) to confirm 400-level validation still works after changes.
6. Open PR + attach screenshots of the response JSON + registrationDate stamp.

---

## ⚖️ License

- Application code: Portfolio / professional review only. Commercial use requires written author authorization.
- Documentation & diagrams: May be referenced with attribution linking back to `github.com/Alberto-Zompantzi` profile.
- DivertiKids brand name and logo assets: property of DivertiKids entertainment — used with permission for portfolio.

---

<div align="center">

## 📫 Let's Connect

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Alberto%20Zompantzi-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/alberto-zompantzi/)
&nbsp;&nbsp;
[![Portfolio](https://img.shields.io/badge/🌐_Portfolio-albertozompantzi--portfolio.pages.dev-F38020?style=for-the-badge&logo=cloudflare&logoColor=white)](https://albertozompantzi-portfolio.pages.dev/)
&nbsp;&nbsp;
[![GitHub](https://img.shields.io/badge/GitHub-Alberto--Zompantzi-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Alberto-Zompantzi)
&nbsp;&nbsp;
[![Email](https://img.shields.io/badge/📧_Email-alberto--zompantzi%40outlook.com-0078D4?style=for-the-badge&logo=microsoftoutlook&logoColor=white)](mailto:alberto-zompantzi@outlook.com)

---

**💼 Open to backend, platform, and architecture roles.**

</div>
