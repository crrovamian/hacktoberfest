# PisoMatch 🏠

Un "Tinder" para encontrar compañero/a de piso, hecho con **Spring Boot 3.4** y
**Java 21** (Maven). Cada usuario tiene su perfil de convivencia y sus preferencias
de búsqueda; la app muestra candidatos ordenados por compatibilidad y, cuando dos se
dan "like" mutuamente, se crea un **match** y pueden **chatear**.

Incluye una **web demo estilo Tinder**, **vistas Thymeleaf** y base de datos **H2 en
memoria** con datos de ejemplo (arranca sin instalar nada).

## Requisitos

- Java 21
- Maven 3.9+

## Arrancar

```bash
mvn spring-boot:run
```

- Web demo (swipe + chat): `http://localhost:8080/`
- Vistas Thymeleaf: `http://localhost:8080/ui/usuarios`
- API REST: `http://localhost:8080/api/v1`

> Al ser H2 en memoria, los datos se reinician en cada arranque y se cargan 12 usuarios de ejemplo (con perfiles, preferencias, 7 viviendas, likes pendientes y dos matches con chat precargado: Lucía–Carlos y Marta–Diego).

## Modelo de datos

| Entidad | Atributos | Relación |
|---------|-----------|----------|
| **Usuario** | id, nombre, edad, foto, profesión, descripción | — |
| **PerfilConvivencia** | usuario_id, presupuesto, ciudad, fechaEntrada, hábitos, preferencias | 1:1 con Usuario |
| **PreferenciasBusqueda** | usuario_id, presupuestoMínimo, presupuestoMáximo, zonaDeseada | 1:1 con Usuario |
| **Vivienda** | id, propietario_id, dirección, zona, precio, habitaciones, fotos | N:1 con Usuario |
| **Like** | id, usuario_id, usuario_objetivo_id, estado (`LIKE`/`PASS`/`MATCH`) | dirigido Usuario→Usuario |
| **Mensaje** | id, emisor_id, receptor_id, contenido, fecha | entre dos Usuarios con match |

**Compatibilidad (0-100%)**: se calcula combinando las preferencias de búsqueda de
quien explora con el perfil de convivencia del candidato (zona deseada 40, presupuesto
dentro de rango 40, misma ciudad 20).

**Match**: al darse like recíproco, ambas filas `Like` pasan a estado `MATCH`.
**Chat**: solo se permite enviar mensajes entre usuarios con match.

## API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/v1/usuarios` | Crea un usuario |
| `GET`  | `/api/v1/usuarios` · `/{id}` | Lista / obtiene usuarios |
| `PUT`  | `/api/v1/usuarios/{id}` | Actualiza un usuario |
| `GET/PUT` | `/api/v1/usuarios/{id}/perfil` | Perfil de convivencia |
| `GET/PUT` | `/api/v1/usuarios/{id}/preferencias` | Preferencias de búsqueda |
| `GET`  | `/api/v1/usuarios/{id}/discover` | Candidatos ordenados por compatibilidad |
| `POST` | `/api/v1/usuarios/{id}/swipes` | Like/pass; indica si hay match |
| `GET`  | `/api/v1/usuarios/{id}/matches` | Matches del usuario |
| `POST` | `/api/v1/usuarios/{id}/mensajes` | Envía un mensaje (requiere match) |
| `GET`  | `/api/v1/usuarios/{id}/mensajes/{otroId}` | Conversación entre dos usuarios |
| `POST/GET` | `/api/v1/viviendas` | Publica / lista viviendas (`?propietarioId=` para filtrar) |
| `GET`  | `/api/v1/viviendas/{id}` | Obtiene una vivienda |

### Ejemplos

```bash
# Crear usuario
curl -X POST http://localhost:8080/api/v1/usuarios -H 'Content-Type: application/json' \
  -d '{"nombre":"Alex","edad":29,"foto":"","profesion":"Dev","descripcion":"Ordenado"}'

# Añadir su perfil de convivencia y preferencias
curl -X PUT http://localhost:8080/api/v1/usuarios/1/perfil -H 'Content-Type: application/json' \
  -d '{"presupuesto":500,"ciudad":"Madrid","fechaEntrada":"2026-12-01","habitos":"No fumo","preferencias":"Tranquilo"}'
curl -X PUT http://localhost:8080/api/v1/usuarios/1/preferencias -H 'Content-Type: application/json' \
  -d '{"presupuestoMinimo":400,"presupuestoMaximo":650,"zonaDeseada":"Madrid"}'

# Descubrir, dar like y chatear
curl http://localhost:8080/api/v1/usuarios/1/discover
curl -X POST http://localhost:8080/api/v1/usuarios/1/swipes -H 'Content-Type: application/json' -d '{"objetivoId":2,"liked":true}'
curl -X POST http://localhost:8080/api/v1/usuarios/1/mensajes -H 'Content-Type: application/json' -d '{"receptorId":2,"contenido":"¡Hola!"}'
```

## Tests

```bash
mvn test
```

## Estructura

```
src/main/java/com/pisomatch/
├── config/DataSeeder.java         # Datos de ejemplo al arrancar
├── domain/                        # Usuario, PerfilConvivencia, PreferenciasBusqueda,
│                                  #   Vivienda, Like (+ EstadoLike), Mensaje
├── repository/                    # Un repositorio Spring Data por entidad
├── service/                       # UsuarioService, PerfilConvivenciaService,
│                                  #   PreferenciasBusquedaService, ViviendaService,
│                                  #   MatchingService, MensajeService, CompatibilityCalculator
└── web/
    ├── UsuarioController / ViviendaController / MatchingController / MensajeController
    ├── dto/                        # Requests y responses (records)
    ├── error/                      # ApiError + manejador global (400/404/500)
    └── ui/                         # Vistas Thymeleaf (UsuarioViewController + UsuarioForm)

src/main/resources/
├── static/index.html              # Web demo estilo Tinder (swipe + chat)
└── templates/                     # Plantillas Thymeleaf (usuarios/*, viviendas/*, fragments)
```

## Siguientes pasos sugeridos

- Autenticación (Spring Security + JWT) para que cada usuario sea "el suyo".
- Swipe también sobre viviendas, no solo sobre usuarios.
- Formularios Thymeleaf para perfil, preferencias y viviendas (ahora se crean por API).
- Persistencia real (PostgreSQL) y documentación OpenAPI con `springdoc-openapi`.
```
