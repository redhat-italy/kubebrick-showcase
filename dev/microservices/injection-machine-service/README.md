# injection-device-service Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Dependencies

You need a AMQ Broker running before starting this servi

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/injection-device-service-1.0.0-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

# Testing the service

## Get temperature

curl -X GET -H "Content-Type: application/json" localhost:8181/camel/temperature

## Run the integration with SSL connection to external AMQ Broker

When running with quarkus:dev you have to set trustStore and passaword

```mvn quarkus:dev -Djavax.net.ssl.trustStore=PATH_TO/client.ts -Djavax.net.ssl.trustStorePassword=PASSWORD```

## start a batch

curl -X POST -H "Content-Type: application/json" localhost:8181/camel/startbatch -d "@samples/batch.json"

curl -X POST -H "Content-Type: application/json" localhost:8181/camel/startbatch -d "@samples/batch_blue.json"


# Push image to quay.io

Build and export to Quay

    docker build . -t quay.io/<repository>/injection-machine:1.0

Prepare quay repository and an account to push/pull to quay

Push to quay

    docker push quay.io/<repository>/injection-machine:1.0
