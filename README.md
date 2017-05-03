in-vader-controller
===================

[![Build Status](https://travis-ci.org/in-vader/in-vader-controller.svg?branch=master)](https://travis-ci.org/in-vader/in-vader-controller)

## Running the controller

Start the application using the gradle spring boot plugin:

```
./gradlew bootRun
```

The above requires that you have a mongodb instance accessible at `localhost:27017`.

### Docker

```
./gradlew build
docker-compose up
```

The above will start both the mongodb and the app containers.