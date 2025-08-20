# Shared Database Library - ServiceAuth

A common entity library for microservices that provides shared database entities, DAOs, and repositories for service authentication management.

## 📋 Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Database Schema](#database-schema)
- [How It Works](#how-it-works)
- [Usage Examples](#usage-examples)
- [Integration Guide](#integration-guide)
- [API Reference](#api-reference)
- [Building and Installation](#building-and-installation)

## 🔍 Overview

This library provides a complete implementation for service authentication management with the following structure:

### Key Components:
- **Entity**: `ServiceAuth` - JPA entity with Lombok annotations
- **Enums**: `Status` - Authentication status management
- **DAO Interface**: `ServiceAuthDao` - Data access operations
- **DAO Implementation**: `ServiceAuthDaoImpl` - Business logic implementation
- **Repository**: `ServiceAuthRepo` - Spring Data JPA repository
- **Exception Handling**: Custom exceptions and error messages

## 🏗️ Architecture

```
com.sdl.auth/
├── entity/
│   └── ServiceAuth.java          # JPA Entity with Lombok
├── enums/
│   └── Status.java               # Status enumeration
├── dao/
│   ├── ServiceAuthDao.java       # DAO Interface
│   └── ServiceAuthDaoImpl.java   # DAO Implementation
├── repo/
│   └── ServiceAuthRepo.java      # Spring Data JPA Repository
├── exception/
│   ├── SDLException.java         # Custom Exception
│   └── SDLMessages.java          # Error Messages
└── common/model/
    └── VariablesConstant.java    # Query Parameter Constants
```

## 🗄️ Database Schema

### Table: `service_auth_token`

| Column Name | Type | Constraints | Description |
|-------------|------|-------------|-------------|
| id | BIGINT | Primary Key, Auto Increment | Unique identifier |
| service_code | VARCHAR | NOT NULL | Service identification code |
| service_auth_key | VARCHAR | NOT NULL | Service authentication key/token |
| allowed_ips | VARCHAR | NULL | Comma-separated list of allowed IP addresses |
| status | VARCHAR | NOT NULL, Enum | Status: ACTIVE, INACTIVE, SUSPENDED, EXPIRED |
| created_at | TIMESTAMP | NOT NULL, Auto-set | Record creation timestamp |
| updated_at | TIMESTAMP | Auto-updated | Record update timestamp |
| expires_at | TIMESTAMP | NOT NULL | Token expiration timestamp |

## ⚙️ How It Works

### 1. **Entity Layer (`ServiceAuth`)**
```java
@Table(name = "service_auth")
@Entity
@Data
@NoArgsConstructor
public class ServiceAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    
    private String clientCode;
    private String token;
    private String clientIp;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
}
```

**Features:**
- **Lombok Annotations**: `@Data` generates getters, setters, toString, equals, hashCode
- **`@NoArgsConstructor`**: Default constructor for JPA
- **`@JsonIgnore`**: Hides ID from JSON serialization
- **`@Enumerated(EnumType.STRING)`**: Stores enum as string in database
- **Automatic Timestamps**: Managed through custom constructors and methods

### 2. **Custom Constructors and Methods**
```java
// Constructor with automatic status and timestamps
public ServiceAuth(String clientCode, String token, String clientIp, Long loggedInUserId) {
    this.clientCode = clientCode;
    this.token = token;
    this.clientIp = clientIp;
    this.status = Status.ACTIVE;           // Auto-set to ACTIVE
    this.createdBy = loggedInUserId;
    this.updatedBy = loggedInUserId;
    this.createdAt = new Date();           // Auto-set current time
    this.updatedAt = new Date();
}

// Method to update client details
public ServiceAuth setClientCodeAndToken(String clientCode, String token, Long loggedIn) {
    this.id = null;                        // Reset ID for new entity
    this.clientCode = clientCode;
    this.token = token;
    this.createdBy = loggedIn;
    this.updatedBy = loggedIn;
    this.createdAt = new Date();
    this.updatedAt = new Date();
    return this;
}

// Method to update status with audit trail
public ServiceAuth updateStatus(Status status, Long userId) {
    this.status = status;
    this.updatedAt = new Date();           // Auto-update timestamp
    this.updatedBy = userId;               // Track who updated
    return this;
}
```

### 3. **DAO Layer with Exception Handling**
```java
public interface ServiceAuthDao {
    // Default method for validation and exception handling
    default void isEmptyOrNull(Boolean bool) {
        if (Boolean.TRUE.equals(bool))
            throw new SDLException(SDLMessages.DATA_NOT_FOUND_CODE,
                    SDLMessages.DATA_NOT_FOUND_MESSAGE + " For Service Auth");
    }
    
    List<ServiceAuth> saveAll(List<ServiceAuth> serviceAuths);
    Integer countByClientCodeAndStatus(String clientCode, Status status);
    List<ServiceAuth> findByClientCode(String clientCode);
    ServiceAuth findByToken(String token);
}
```

### 4. **DAO Implementation with Validation**
```java
@Component
public class ServiceAuthDaoImpl implements ServiceAuthDao {
    @Autowired
    ServiceAuthRepo repo;

    @Override
    public List<ServiceAuth> findByClientCode(String clientCode) {
        List<ServiceAuth> result = repo.findByClientCode(clientCode);
        isEmptyOrNull(result.isEmpty());    // Throws exception if empty
        return result;
    }
}
```

### 5. **Repository with Native Queries**
```java
public interface ServiceAuthRepo extends JpaRepository<ServiceAuth, Long> {
    @Query(value = "select count(*) from service_auth sa where sa.client_code = :clientCode and sa.status = :status", 
           nativeQuery = true)
    Integer fetchCountByClientCodeAndStatus(@Param(VariablesConstant.CLIENT_CODE) String clientCode, 
                                          @Param(VariablesConstant.STATUS) String status);
}
```

## 🚀 Usage Examples

### Creating a New Service Auth
```java
// Create new service authentication
ServiceAuth auth = new ServiceAuth("USER_SERVICE", "auth-key-123456", "192.168.1.100,10.0.0.1");

// Save using DAO
ServiceAuth savedAuth = serviceAuthDao.save(auth);
```

### Updating Status
```java
// Update status with audit trail
ServiceAuth updatedAuth = existingAuth.updateStatus(Status.SUSPENDED);
serviceAuthDao.save(updatedAuth);
```

### Querying Data
```java
// Find by service code (throws exception if not found)
List<ServiceAuth> auths = serviceAuthDao.findByServiceCode("USER_SERVICE");

// Count active auths for a service
Integer count = serviceAuthDao.countByServiceCodeAndStatus("USER_SERVICE", Status.ACTIVE);

// Find by auth key
ServiceAuth auth = serviceAuthDao.findByServiceAuthKey("auth-key-123456");

// Find by service code, auth key and status (your requested method)
ServiceAuth specificAuth = serviceAuthDao.findByServiceCodeAndServiceAuthKeyAndStatus(
    "USER_SERVICE", "auth-key-123456", Status.ACTIVE);

// Find expired tokens
List<ServiceAuth> expiredTokens = serviceAuthDao.findExpiredTokens();
```

### Batch Operations
```java
// Save multiple auth records
List<ServiceAuth> authList = Arrays.asList(
    new ServiceAuth("USER_SERVICE", "key1", "192.168.1.1"),
    new ServiceAuth("ORDER_SERVICE", "key2", "192.168.1.2")
);
List<ServiceAuth> saved = serviceAuthDao.saveAll(authList);
```

## 🔧 Integration Guide

### Step 1: Add Dependency
Add to your Spring Boot project's `pom.xml`:
```xml
<dependency>
    <groupId>com.sdl</groupId>
    <artifactId>shared-database-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Step 2: Configure Spring Boot Application
```java
@SpringBootApplication
@EntityScan("com.sdl.auth.entity")                    // Scan for JPA entities
@EnableJpaRepositories("com.sdl.auth.repo")          // Enable JPA repositories
@ComponentScan(basePackages = {"com.yourapp", "com.sdl.auth"})  // Component scan
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```

### Step 3: Use in Your Service
```java
@Service
public class AuthService {
    @Autowired
    private ServiceAuthDao serviceAuthDao;
    
    public ServiceAuth createAuth(String clientCode, String token, String clientIp, Long userId) {
        ServiceAuth auth = new ServiceAuth(clientCode, token, clientIp, userId);
        return serviceAuthDao.save(auth);
    }
    
    public List<ServiceAuth> getActiveAuths(String clientCode) {
        return serviceAuthDao.findByClientCodeAndStatus(clientCode, Status.ACTIVE);
    }
}
```

## 📚 API Reference

### ServiceAuth Entity Methods
- `ServiceAuth(String serviceCode, String serviceAuthKey, String allowedIps)` - Constructor with auto-status and expiry
- `setServiceCodeAndKey(String serviceCode, String serviceAuthKey, String allowedIps)` - Update service details
- `updateStatus(Status status)` - Update status with audit
- `updateExpiry(Date expiresAt)` - Update expiration date

### DAO Methods
- `saveAll(List<ServiceAuth> serviceAuths)` - Batch save
- `save(ServiceAuth serviceAuth)` - Single save
- `findByServiceCode(String serviceCode)` - Find by service code (throws if empty)
- `findByServiceCodeAndStatus(String serviceCode, Status status)` - Find by service and status
- `findByStatus(Status status)` - Find by status
- `findByServiceAuthKey(String serviceAuthKey)` - Find by auth key (throws if null)
- `findByServiceCodeAndServiceAuthKeyAndStatus(String serviceCode, String serviceAuthKey, Status status)` - Find by service, key and status
- `findByAllowedIps(String allowedIps)` - Find by allowed IPs
- `findExpiredTokens()` - Find all expired tokens
- `countByServiceCodeAndStatus(String serviceCode, Status status)` - Count records

### Status Enum Values
- `ACTIVE` - Authentication is active and valid
- `INACTIVE` - Authentication is inactive
- `SUSPENDED` - Authentication is temporarily suspended
- `EXPIRED` - Authentication has expired

### Exception Handling
- **`SDLException`**: Custom runtime exception
- **Automatic Validation**: DAO methods throw exceptions for null/empty results
- **Error Codes**: Standardized error codes and messages

## 🛠️ Building and Installation

### Prerequisites
- Java 17+
- Maven 3.6.3+

### Build Commands
```bash
# Set Java 17 (required for Lombok compatibility)
export JAVA_HOME=/Users/bharatpalariya/Library/Java/JavaVirtualMachines/ms-17.0.16/Contents/Home

# Compile
mvn clean compile

# Package
mvn clean package

# Install to local repository
mvn clean install
```

### Dependencies
- **Jakarta Persistence API 3.1.0** - JPA support
- **Jakarta Validation API 3.0.2** - Validation annotations
- **Lombok 1.18.30** - Code generation
- **Jackson Annotations 2.15.2** - JSON processing
- **Spring Data JPA 3.1.2** - Repository support
- **Spring Context 6.0.11** - Dependency injection

## 🎯 Key Benefits

1. **🔄 Automatic Auditing**: Created/updated timestamps and user tracking
2. **⚡ Exception Handling**: Built-in validation and error management
3. **🔧 Lombok Integration**: Reduced boilerplate code
4. **🗃️ Spring Data JPA**: Powerful query capabilities
5. **📦 Enum Management**: Type-safe status handling
6. **🔍 Native Queries**: Optimized database operations
7. **🏗️ Modular Design**: Clean separation of concerns
8. **🔒 Spring Boot Compatible**: Ready for microservices architecture

## 📝 Migration Notes

This replaces previous User and UserData entities with a comprehensive ServiceAuth implementation designed specifically for service authentication and authorization management in microservices architecture.

---

**Version**: 1.0.0  
**Compatibility**: Spring Boot 3.5.4+, Java 17+  
**License**: Internal Use