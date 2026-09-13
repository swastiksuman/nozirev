# Nozirev E-Commerce Platform

A modern microservices-based e-commerce application built with Spring Boot 4 and React 19, featuring product catalog management and shopping cart functionality with Caffeine caching.

## Architecture Overview

The application consists of three main components:

```
nozirev/
├── nozirev-soe/           # Store Operations Engine (Product Catalog Service)
├── nozirev-cxp-cart/      # Customer Experience Platform (Cart Service)
└── nozirev-ui/            # React Frontend Application
```

### Services

| Service | Technology | Port | Purpose |
|---------|-----------|------|---------|
| **nozirev-soe** | Spring Boot 4.0.1 + WebFlux | 8080 | Product catalog API providing smartphones, tablets, watches, and routers |
| **nozirev-cxp-cart** | Spring Boot 4.0.1 + WebFlux | 8081 | Shopping cart management with Caffeine in-memory caching |
| **nozirev-ui** | React 19.2.3 + Redux | 3000 | Customer-facing web application |

## Technology Stack

### Backend Services
- **Java**: 21 (LTS)
- **Spring Boot**: 4.0.1
- **Spring WebFlux**: Reactive programming model
- **Maven**: 3.9.12 (wrapper included)
- **Project Lombok**: Annotation-based boilerplate reduction
- **Caffeine Cache**: High-performance in-memory caching (cart service)

### Frontend
- **React**: 19.2.3
- **Redux**: 5.0.1 with Redux Thunk
- **React Router**: 7.13.0
- **Testing Library**: Jest + React Testing Library

## Getting Started

### Prerequisites

- **Java 21**: Required for running backend services
- **Node.js**: 16+ and npm for frontend development
- **Maven**: Included via wrapper (`mvnw`)

### Running the Backend Services

#### 1. Start the Product Catalog Service (nozirev-soe)

```bash
cd nozirev-soe
./mvnw spring-boot:run
```

The service will start on `http://localhost:8080`

#### 2. Start the Cart Service (nozirev-cxp-cart)

```bash
cd nozirev-cxp-cart
mvn spring-boot:run
```

The service will start on `http://localhost:8081`

> **Note**: nozirev-cxp-cart does not include Maven Wrapper. Use your system Maven installation or copy the wrapper from nozirev-soe.

#### 3. Start the Frontend Application (nozirev-ui)

```bash
cd nozirev-ui
npm install
npm start
```

The application will start on `http://localhost:3000`

## API Documentation

### Product Catalog Service (nozirev-soe)

#### Get Product List

```http
POST /api/getProductList
Content-Type: application/json

{
  "type": "smartphones" | "tablets" | "watches" | "routers"
}
```

**Response**: Array of products with id, name, image, price, and description

### Cart Service (nozirev-cxp-cart)

#### Get Cart

```http
GET /api/cart/{userId}
```

#### Create Cart

```http
POST /api/cart/create
Content-Type: application/json

{
  "userId": "string",
  "profileDetails": {},
  "shippingDetails": {}
}
```

#### Add Item to Cart

```http
POST /api/cart/addItem
Content-Type: application/json

{
  "userId": "string",
  "item": {},
  "profileDetails": {},
  "shippingDetails": {}
}
```

#### Update Item Quantity

```http
PUT /api/cart/updateItem
Content-Type: application/json

{
  "userId": "string",
  "productId": 0,
  "quantity": 0
}
```

#### Remove Item from Cart

```http
DELETE /api/cart/{userId}/item/{productId}
```

#### Clear Cart

```http
DELETE /api/cart/{userId}/clear
```

## Features

### Product Catalog Service
- ✅ RESTful API for product retrieval
- ✅ Support for multiple product categories (smartphones, tablets, watches, routers)
- ✅ Reactive programming with Spring WebFlux
- ✅ Comprehensive test coverage

### Cart Service
- ✅ Full CRUD operations for shopping cart
- ✅ High-performance Caffeine caching (30-minute TTL, 10K entries max)
- ✅ Validation with Jakarta Bean Validation
- ✅ User profile and shipping details management
- ✅ Cache eviction strategies
- ✅ Comprehensive test coverage with cache integration tests

### Frontend Application
- ✅ Modern React 19 with functional components and hooks
- ✅ Redux state management with Redux Thunk for async operations
- ✅ React Router for navigation
- ✅ Responsive design
- ✅ Product listing and filtering
- ✅ Shopping cart functionality
- ✅ Test coverage with React Testing Library

## Project Structure

### Backend Services

```
nozirev-soe/
├── src/
│   ├── main/
│   │   ├── java/com/nozirev_service/nozirev_soe/
│   │   │   ├── NozirevSoeApplication.java
│   │   │   ├── controller/
│   │   │   │   └── ProductController.java
│   │   │   └── config/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml

nozirev-cxp-cart/
├── src/
│   ├── main/
│   │   ├── java/com/nozirev_service/nozirev_cxp_cart/
│   │   │   ├── controller/CartController.java
│   │   │   ├── service/CartService.java
│   │   │   ├── model/
│   │   │   └── config/CacheConfig.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

### Frontend

```
nozirev-ui/
├── src/
│   ├── components/        # Reusable UI components
│   │   ├── Navigation.js
│   │   └── Footer.js
│   ├── pages/            # Route-level page components
│   │   ├── HomePage.js
│   │   ├── ProductListing.js
│   │   └── CartPage.js
│   ├── redux/            # State management
│   │   ├── store.js
│   │   ├── actions/
│   │   └── reducers/
│   ├── services/         # API integration
│   │   └── productService.js
│   └── styles/           # Component-specific CSS
└── package.json
```

## Testing

### Backend Tests

Run tests for all services:

```bash
# Product Catalog Service
cd nozirev-soe
./mvnw test

# Cart Service
cd nozirev-cxp-cart
mvn test
```

### Frontend Tests

```bash
cd nozirev-ui
npm test
```

## Caching Strategy

The cart service implements a high-performance caching layer using Caffeine:

- **Cache Type**: In-memory (Caffeine)
- **Max Size**: 10,000 cart entries
- **TTL**: 30 minutes per entry
- **Key Strategy**: `userId` as cache key
- **Operations**:
  - `@Cacheable` on `getCart()` — retrieve from cache if exists
  - `@CachePut` on `createCart()`, `addItem()`, `updateItem()`, `removeItem()`, `clearCart()` — update cache
  - `@CacheEvict` on `deleteCart()` — remove from cache

See [CAFFEINE_CACHE_IMPLEMENTATION.md](nozirev-cxp-cart/CAFFEINE_CACHE_IMPLEMENTATION.md) for detailed caching documentation.

## Build for Production

### Backend Services

```bash
# Build nozirev-soe
cd nozirev-soe
./mvnw clean package
java -jar target/nozirev-soe-0.0.1-SNAPSHOT.jar

# Build nozirev-cxp-cart
cd nozirev-cxp-cart
mvn clean package
java -jar target/nozirev-cxp-cart-0.0.1-SNAPSHOT.jar
```

### Frontend

```bash
cd nozirev-ui
npm run build
```

The optimized production build will be in the `build/` directory.

## Configuration

### Backend Configuration

Both services use `application.properties` for configuration:

**nozirev-soe** (`src/main/resources/application.properties`):
```properties
spring.application.name=nozirev-soe
# Runs on default port 8080
```

**nozirev-cxp-cart** (`src/main/resources/application.properties`):
```properties
spring.application.name=nozirev-cxp-cart
server.port=8081

# Caffeine Cache Configuration
spring.cache.type=caffeine
spring.cache.caffeine.spec=maximumSize=10000,expireAfterWrite=30m
```

### CORS Configuration

Both backend services are configured to allow cross-origin requests from the React frontend during development.

## Development Guidelines

### Code Style
- Backend: Follow standard Spring Boot conventions with Lombok annotations
- Frontend: Use functional components with hooks; organize files by feature

### Folder Conventions
- Backend: Package by layer (controller, service, model, config)
- Frontend: Organize by feature type (components, pages, services, redux)

### Testing Standards
- Backend: Unit tests with JUnit 5 and Mockito; integration tests with `@SpringBootTest`
- Frontend: Component tests with React Testing Library; Redux logic tests with Jest

## Troubleshooting

### Common Issues

**Port already in use**
```bash
# Find and kill process using port 8080/8081
lsof -i :8080
kill -9 <PID>
```

**Maven wrapper permission denied**
```bash
chmod +x mvnw
```

**Node modules issues**
```bash
cd nozirev-ui
rm -rf node_modules package-lock.json
npm install
```

## Contributing

1. Follow existing code structure and conventions
2. Write tests for new features
3. Update documentation as needed
4. Ensure all tests pass before committing

## Additional Documentation

- [Frontend Structure Guide](nozirev-ui/README_STRUCTURE.md)
- [Frontend Overview](nozirev-ui/README_UI.md)
- [Caffeine Cache Implementation](nozirev-cxp-cart/CAFFEINE_CACHE_IMPLEMENTATION.md)

## License

This project is for demonstration purposes.

---

**Built with ❤️ using Spring Boot 4, React 19, and modern microservices architecture**
