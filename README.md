# Shared Database Library

A common entity library for microservices that provides shared database entities, DTOs, and DAOs.

## ServiceAuth Entity

This library provides a complete implementation for service authentication management with the following structure:

### Database Table: `service_auth`

| Column Name | Type | Constraints | Description |
|-------------|------|-------------|-------------|
| id | BIGINT | Primary Key, Auto Increment | Unique identifier |
| client_code | VARCHAR(50) | NOT NULL, Size: 3-50 chars | Client identification code |
| token | VARCHAR(500) | NOT NULL, Size: 10-500 chars | Authentication token |
| client_ip | VARCHAR(45) | NOT NULL, Valid IPv4/IPv6 | Client IP address |
| status | VARCHAR(20) | NOT NULL, Enum values | Status: ACTIVE, INACTIVE, SUSPENDED, EXPIRED |
| created_at | TIMESTAMP | NOT NULL, Auto-set on creation | Record creation timestamp |
| updated_at | TIMESTAMP | Auto-updated on modification | Record update timestamp |

### Package Structure

```
com.sdl.auth/
├── entity/
│   └── ServiceAuth.java          # JPA Entity with validation
├── dto/
│   ├── ServiceAuthDto.java       # Data Transfer Object
│   ├── ServiceAuthCreateDto.java # DTO for creation operations
│   └── ServiceAuthUpdateDto.java # DTO for update operations
├── dao/
│   └── ServiceAuthDao.java       # Data Access Object interface
└── mapper/
│   └── ServiceAuthMapper.java    # Entity-DTO mapping utilities
```

### Features

1. **JPA Entity (`ServiceAuth`)**
   - Full JPA annotations for database mapping
   - Bean validation annotations for data integrity
   - Automatic timestamp management with `@PrePersist` and `@PreUpdate`
   - Custom validation for IP addresses and status values

2. **Data Transfer Objects (DTOs)**
   - `ServiceAuthDto`: Complete DTO with all fields
   - `ServiceAuthCreateDto`: DTO for creation (excludes id, timestamps)
   - `ServiceAuthUpdateDto`: DTO for updates (optional fields, requires id)

3. **Data Access Object (DAO)**
   - Complete interface with CRUD operations
   - Finder methods by various criteria (clientCode, token, status, clientIp)
   - Existence checks and counting methods

4. **Mapper Utilities**
   - Static methods for converting between Entity and DTOs
   - Null-safe operations
   - List conversion utilities

### Validation Rules

- **Client Code**: Required, 3-50 characters
- **Token**: Required, 10-500 characters  
- **Client IP**: Required, must be valid IPv4 or IPv6 address
- **Status**: Required, must be one of: ACTIVE, INACTIVE, SUSPENDED, EXPIRED
- **Timestamps**: Automatically managed by JPA lifecycle hooks

### Usage Example

```java
// Create a new service auth
ServiceAuthCreateDto createDto = new ServiceAuthCreateDto(
    "CLIENT001", 
    "auth-token-123456", 
    "192.168.1.100", 
    "ACTIVE"
);

ServiceAuth entity = ServiceAuthMapper.toEntity(createDto);
// Save using your preferred persistence mechanism

// Convert to DTO for API response
ServiceAuthDto responseDto = ServiceAuthMapper.toDto(entity);
```

### Maven Dependencies

The library includes the following key dependencies:
- Jakarta Persistence API 3.1.0
- Jakarta Validation API 3.0.2
- Java 17 compatibility

### Building

```bash
mvn clean compile  # Compile the library
mvn package       # Create JAR file
```

## Migration Notes

This replaces the previous `User` and `UserData` entities with a new `ServiceAuth` entity designed specifically for service authentication and authorization management.
