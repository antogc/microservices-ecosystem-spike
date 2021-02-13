# microservices-ecosystem-spike
This repo holds several projects that compose a spring cloud based micro-service ecosystem. Config server, api-gateway, business services,load balance, distributed tracing and circuit breaker.

Repo structure:

1. department-service
  Deparment service business logic. 
  Features: 
    * Externalized config
    * Discovery client (eureka)
    * Data-jpa + H2 in-memory database
    * Zipkin and Sleuth for distribute tracing
    
2. user-service
  User service business logic. 
  Features: 
    * Externalized config
    * Discovery client (eureka)
    * Data-jpa + H2 in-memory database
    * VO pattern to join user department
    * Zipkin and Sleuth for distribute tracing
    
3. user-service
  Api-gateway service to provide common access point to user and department services 
  Features: 
    * Externalized config
    * Discovery client (eureka)
    * Load balancing
    * Circuit breaker (with fallback endpoints)
    * Provides an hystrix stream to be consumed by the hystrix server
    
4. service-registry
  Servicy registry server for service auto-discovery. 
  Features:
    * Externalized config
    * Discovery server (eureka)
    
5. cloud-config-server
  Config server to provide externalized configuration
  Features:
    * Spring cloud config server
    * Discovery client (eureka)
    
6. hystrix-dashboard
  Hystrix server to trace hystrix stream provided by api-gateway
  Features:
    * Externalized config
    * Discovery client (eureka)
    * Hystrix dashboard
