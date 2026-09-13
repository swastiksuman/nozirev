# Nozirev Workspace Guidelines

## Backend Data & Controller Conventions (Spring Boot)
- **Externalize Mock & Seed Data**: Do not hardcode datasets, item catalogs, or mock data directly inside Java controller or service classes.
- **Resource JSON Files**: Store static catalog, configuration, and mock datasets in `src/main/resources/*.json` (e.g., `products.json`).
- **Dynamic Loading via ObjectMapper**: Use Jackson `ObjectMapper` with `ClassPathResource` inside an `@PostConstruct` method (or dedicated loader service) to initialize thread-safe in-memory collections (such as `ConcurrentHashMap`).
- **DTO Structure**: Keep data models/DTOs clean with default no-argument constructors and standard getters/setters for seamless Jackson serialization and deserialization.
- **Endpoint Structure**: Expose REST endpoints returning `ResponseEntity<T>` with appropriate HTTP status codes (`200 OK`, `404 Not Found`, etc.).
