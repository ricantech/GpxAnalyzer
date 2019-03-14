#GpxAnalyzer
Application loads the file, store information and calculate total distance, start waypoint - end waypoint distance, average speed and get close places through HereAPI (see bellow for more information).

##Defaults
port: 8081

###How to run locally
Use gradle wrapper ```./gradlew bootRun```

IDE spring boot run configuration - ```GpxAnalyzerApplication.class```

##Swagger documentation
Accessible here once up-and-running: http://localhost:8081/swagger-ui.html

##Configuration
**hereapi.url** - URL of HereAPI REST service.

**hereapi.id** - ID of HereAPI user.

**hereapi.code** - Code of HereAPI user.

##External APIs
To obtain information about close places HereAPI is used. 
Documentation can be found here: https://developer.here.com/documentation/places/topics/explore-nearby-places.html