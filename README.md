# two-listening-ports
Small Spring Boot demo project that exposes two TCP/IP listening ports and routes the /internal and /external endpoints

## Configuration
Define the ports in the `application.properties` file:
```yaml
server:
  # External port
  port: 8080
  # Internal port
  internal-port: 8079
```

All the endpoints `/internal/*` can be accessed using the internal port, 
while all the endpoints `/external/*` can be accessed by the external port.

## Open questions
- What about the other enpoints like /actuators, /health, etc.? 
  - Should they be accessible from both ports?
