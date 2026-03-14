# DivertiKids — Booking Service (Backend)

[![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-336791?logo=postgresql)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-Proyecto%20privado-lightgrey)](./README.md)

**Microservicio de reservas** para la plataforma DivertiKids. Expone un API REST para crear, consultar y cancelar reservas de eventos infantiles, con persistencia en PostgreSQL y documentación OpenAPI (Swagger UI).

---

## Solución de negocio

El backend centraliza la **gestión de reservas** de DivertiKids:

- **Crear reservas** — Cliente, email, fecha, horario y precio; validación de datos y estado inicial `PENDING`.
- **Consultar por cliente** — Listado de reservas por correo electrónico para seguimiento y soporte.
- **Cancelar reservas** — Cambio de estado a `CANCELLED` sin borrar el registro (trazabilidad).

Integrado con el frontend React para que las familias reserven desde la web sin depender solo de WhatsApp.

---

## Stack tecnológico

| Área                  | Tecnología                         |
| --------------------- | ---------------------------------- |
| **Lenguaje**          | Java 17                            |
| **Framework**         | Spring Boot 3.2                    |
| **Web**               | Spring Web (REST)                  |
| **Persistencia**      | Spring Data JPA + Hibernate        |
| **Validación**        | Bean Validation (Jakarta)          |
| **Base de datos**     | PostgreSQL                         |
| **Documentación API** | SpringDoc OpenAPI 2.x (Swagger UI) |
| **Utilidades**        | Lombok                             |

---

## Arquitectura y estructura

```
divertikids-backend/
├── src/main/java/com/divertikids/booking/
│   ├── BookingServiceApplication.java
│   ├── config/          # OpenApiConfig, WebConfig (CORS)
│   ├── controllers/     # BookingController
│   ├── dto/             # BookingRequestDto, BookingResponseDto
│   ├── entities/        # Booking, BookingStatus
│   ├── exceptions/      # GlobalExceptionHandler, ErrorResponse, BookingNotFoundException
│   ├── repositories/    # BookingRepository
│   └── services/        # BookingService, BookingServiceImpl
├── src/main/resources/
│   └── application.yml
└── pom.xml
```

- **Capa REST:** `BookingController` — `/api/v1/bookings`.
- **Capa de servicio:** reglas de negocio y mapeo entidad ↔ DTO.
- **Capa de datos:** JPA con `Booking` y enum `BookingStatus` (PENDING, PAID, CANCELLED).
- **Excepciones:** respuestas JSON unificadas y manejo de reserva no encontrada.

---

## API REST

| Método | Ruta                                      | Descripción                                                                                  |
| ------ | ----------------------------------------- | -------------------------------------------------------------------------------------------- |
| `POST` | `/api/v1/bookings`                        | Crear reserva (body: customerName, customerEmail, eventDate, startTime, endTime, totalPrice) |
| `GET`  | `/api/v1/bookings/customer?email={email}` | Listar reservas por email del cliente                                                        |
| `POST` | `/api/v1/bookings/{id}/cancel`            | Cancelar reserva por UUID                                                                    |

Documentación interactiva: **Swagger UI** → `http://localhost:8080/swagger-ui.html`

---

## Ejecución

```bash
# Desde la raíz del backend
cd divertikids-backend
mvn spring-boot:run
```

La API quedará disponible en `http://localhost:8080`. Para comprobar salud, Swagger UI o un `GET` a un recurso documentado.

---

## Modelo de datos (resumen)

- **Booking:** id (UUID), customerName, customerEmail, eventDate, startTime, endTime, totalPrice, status (PENDING | PAID | CANCELLED).
- Validaciones: nombre y email obligatorios, email válido, fecha hoy o futura, precio ≥ 0.

---

## Metodología

- API REST versionada (`/api/v1/`).
- DTOs de entrada/salida y validación en controlador.
- Manejo centralizado de excepciones con mensajes claros para el frontend.
- Documentación automática con OpenAPI para facilitar integración y pruebas.

---

## Pruebas de integración

Para validar de extremo a extremo:

1. Levantar PostgreSQL con la base `divertikids_db` y credenciales indicadas.
2. Ejecutar el backend con `mvn spring-boot:run`.
3. Desde el frontend (o con `curl`/Postman) crear una reserva, listar por email y cancelar por id.

---

## Autor y licencia

- **Proyecto:** DivertiKids — Booking Service
- Proyecto privado.
