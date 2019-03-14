# GpxAnalyzer
Application loads the file, store information and calculate (jenetics library) total distance, start waypoint - end waypoint distance, average speed and get close places (HereAPI)

### Defaults
**port: 8081**

### How to run locally
Use gradle wrapper: ```./gradlew bootRun```

IDE spring boot run configuration: ```GpxAnalyzerApplication.class```

### Swagger documentation
Accessible here once up-and-running: ```{url.of.running.service}/swagger-ui.html``` by default http://localhost:8081/swagger-ui.html

### Configuration
**hereapi.url**: URL of HereAPI REST service.

**hereapi.id**: ID of HereAPI user.

**hereapi.code**: Code of HereAPI user.

### Calculations
All geodetic calculations are done through **jenetics** library. Library can be found here: https://github.com/jenetics/jpx

### External APIs
To obtain information about close places HereAPI is used. 
Documentation can be found here: https://developer.here.com/documentation/places/topics/explore-nearby-places.html
