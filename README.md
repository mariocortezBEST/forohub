# ForoHub API 🚀

Una API REST robusta y escalable desarrollada con Spring Boot para gestionar un sistema de foro, permitiendo a los usuarios crear, consultar, actualizar y eliminar tópicos de manera segura.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías](#️-tecnologías)
- [Arquitectura](#-arquitectura)
- [Instalación](#-instalación)
- [Configuración](#️-configuración)
- [Endpoints](#-endpoints)
- [Autenticación](#-autenticación)
- [Base de Datos](#️-base-de-datos)
- [Documentación API](#-documentación-api)
- [Testing](#-testing)
- [Contribución](#-contribución)

## ✨ Características

- **CRUD Completo de Tópicos**: Crear, leer, actualizar y eliminar tópicos
- **Sistema de Autenticación JWT**: Seguridad basada en tokens JSON Web Token
- **Control de Acceso**: Solo el autor puede modificar/eliminar sus propios tópicos
- **Validación de Datos**: Validaciones robustas con Bean Validation
- **Prevención de Duplicados**: No permite tópicos con mismo título y mensaje
- **Paginación y Filtrado**: Listado paginado con filtros por curso y año
- **Manejo de Excepciones**: Sistema centralizado de manejo de errores
- **Documentación Automática**: Integración con Swagger/OpenAPI
- **Migración de BD**: Control de versiones de base de datos con Flyway

## 🛠️ Tecnologías

### Backend
- **Java 17+** - Lenguaje de programación
- **Spring Boot 3.3.0** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **Spring Validation** - Validación de datos
- **Maven** - Gestión de dependencias

### Base de Datos
- **MySQL 8+** - Base de datos relacional
- **Flyway** - Migración y versionado de BD

### Seguridad
- **JWT (JSON Web Token)** - Autenticación stateless
- **BCrypt** - Encriptación de contraseñas

### Documentación
- **SpringDoc OpenAPI** - Documentación automática de API

### Utilidades
- **Lombok** - Reducción de código boilerplate
- **Spring Boot DevTools** - Desarrollo hot-reload

## 🏗 Arquitectura

```
src/
├── main/
│   ├── java/com/alura/forohub/
│   │   ├── config/           # Configuraciones de seguridad y aplicación
│   │   ├── controller/       # Controladores REST
│   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── request/     # DTOs para requests
│   │   │   ├── response/    # DTOs para responses
│   │   │   └── error/       # DTOs para manejo de errores
│   │   ├── entity/          # Entidades JPA
│   │   ├── enums/           # Enumeraciones
│   │   ├── exception/       # Excepciones personalizadas
│   │   ├── infra/           # Infraestructura (JWT, Security)
│   │   ├── repository/      # Repositorios JPA
│   │   └── service/         # Lógica de negocio
│   └── resources/
│       ├── db/migration/    # Scripts de migración Flyway
│       └── application.properties
└── test/                    # Tests unitarios y de integración
```

## 🚀 Instalación

### Prerrequisitos

- Java JDK 17 o superior
- Maven 4+
- MySQL 8+
- IDE (IntelliJ IDEA recomendado)

### Pasos de Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/forohub.git
cd forohub
```

2. **Configurar la base de datos**
```sql
CREATE DATABASE forohub_db;
```

3. **Configurar variables de entorno**
```bash
export DB_URL=jdbc:mysql://localhost:3306/forohub_db
export DB_USERNAME=tu_usuario
export DB_PASSWORD=tu_contraseña
export JWT_SECRET=tu_secret_muy_seguro
export JWT_EXPIRATION=3600
```

4. **Compilar y ejecutar**
```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## ⚙️ Configuración

### application.properties

```properties
# Base de datos
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# Flyway
spring.flyway.enabled=true

# JWT
api.security.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
```

## 📡 Endpoints

### Autenticación

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| POST   | `/auth/login` | Login de usuario | No |

### Tópicos

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| POST   | `/topicos` | Crear nuevo tópico | Sí |
| GET    | `/topicos` | Listar todos los tópicos (paginado) | Sí |
| GET    | `/topicos/{id}` | Obtener tópico específico | Sí |
| PUT    | `/topicos/{id}` | Actualizar tópico | Sí (solo autor) |
| DELETE | `/topicos/{id}` | Eliminar tópico | Sí (solo autor) |

### Ejemplos de Uso

#### 1. Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "usuario@email.com",
    "password": "password123"
  }'
```

#### 2. Crear Tópico
```bash
curl -X POST http://localhost:8080/topicos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {tu_token}" \
  -d '{
    "titulo": "¿Cómo usar Spring Security?",
    "mensaje": "Necesito ayuda con la configuración de Spring Security",
    "cursoId": 1
  }'
```

#### 3. Listar Tópicos con Filtros
```bash
curl -X GET "http://localhost:8080/topicos?curso=Java&año=2024&page=0&size=10" \
  -H "Authorization: Bearer {tu_token}"
```

## 🔐 Autenticación

ForoHub utiliza JWT (JSON Web Token) para la autenticación:

1. **Login**: El usuario envía credenciales a `/auth/login`
2. **Token**: El servidor retorna un JWT válido por 1 hora
3. **Autorización**: Incluir el token en el header: `Authorization: Bearer {token}`
4. **Validación**: El servidor valida el token en cada request protegido

### Estructura del Token JWT

```json
{
  "iss": "ForoHub API",
  "sub": "usuario@email.com",
  "id": 1,
  "exp": 1640995200
}
```

## 🗄️ Base de Datos

### Modelo de Datos

```sql
-- Usuarios
usuarios (id, email, nombre, password, rol, fecha_creacion)

-- Cursos  
cursos (id, nombre, descripcion, categoria)

-- Tópicos
topicos (id, titulo, mensaje, fecha_creacion, status, autor_id, curso_id)
```

### Relaciones
- Un **Usuario** puede crear múltiples **Tópicos** (1:N)
- Un **Curso** puede tener múltiples **Tópicos** (1:N)
- Los **Tópicos** tienen restricción única por título + mensaje

### Estados de Tópicos
```java
public enum StatusTopico {
    ABIERTO,      // Nuevo tópico, esperando respuestas
    SOLUCIONADO,  // Tópico resuelto
    CERRADO       // Tópico cerrado sin solución
}
```

## 📚 Documentación API

La documentación interactiva está disponible mediante Swagger UI:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

La documentación incluye:
- Descripción de todos los endpoints
- Esquemas de request/response
- Códigos de estado HTTP
- Ejemplos de uso
- Autenticación requerida

## 🧪 Testing

### Ejecutar Tests
```bash
# Tests unitarios
mvn test

# Tests con cobertura
mvn jacoco:prepare-agent test jacoco:report
```

### Herramientas de Testing Recomendadas
- **Postman**: Colección disponible para importar
- **Insomnia**: Alternativa ligera para testing de API

## 📊 Características Técnicas

### Seguridad
- ✅ Autenticación JWT stateless
- ✅ Encriptación de contraseñas con BCrypt
- ✅ Control de acceso basado en roles
- ✅ Validación de autoría para modificaciones
- ✅ Prevención de inyección SQL con JPA

### Performance
- ✅ Paginación para grandes volúmenes de datos
- ✅ Lazy loading en relaciones JPA
- ✅ Índices optimizados en base de datos
- ✅ Connection pooling automático

### Mantenibilidad
- ✅ Arquitectura en capas bien definida
- ✅ Principios SOLID aplicados
- ✅ Manejo centralizado de excepciones
- ✅ Logging estructurado
- ✅ Migraciones versionadas de BD

## 🚨 Códigos de Estado HTTP

| Código | Descripción | Cuándo se usa |
|--------|-------------|---------------|
| 200    | OK | GET, PUT exitosos |
| 201    | Created | POST exitoso |
| 204    | No Content | DELETE exitoso |
| 400    | Bad Request | Datos de entrada inválidos |
| 401    | Unauthorized | Token inválido/ausente |
| 403    | Forbidden | Sin permisos para la acción |
| 404    | Not Found | Recurso no encontrado |
| 409    | Conflict | Tópico duplicado |
| 500    | Internal Server Error | Error interno del servidor |

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

### Convenciones de Código
- Seguir estándares de Java (Google Java Style Guide)
- Documentar métodos públicos
- Incluir tests para nuevas funcionalidades
- Mantener cobertura de tests > 80%

## 📄 Licencia

ver el archivo [LICENSE]
## 👨‍💻 Autor

**Mario Isaac Alberto Cortez**
- GitHub: [@tu-usuario](https://github.com/mariocortezBEST)
- LinkedIn: [tu-perfil](https://linkedin.com/in/https://www.linkedin.com/in/maritocortez/?lipi=urn%3Ali%3Apage%3Ad_flagship3_profile_verification_details%3B74ECv5%2FkRf2KEwTljhohcQ%3D%3D)
- Email: cortezmario665@gmail.com

---

⭐️ **¿Te gusta el proyecto? ¡Dale una estrella!**

*Desarrollado con ❤️ usando Spring Boot*
